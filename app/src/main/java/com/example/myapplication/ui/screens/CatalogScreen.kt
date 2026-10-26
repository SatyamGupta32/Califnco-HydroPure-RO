package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.AppRepository
import com.example.myapplication.data.ProductCatalog
import com.example.myapplication.model.Product
import com.example.myapplication.model.UserRole
import com.example.myapplication.ui.components.ImageLightboxDialog
import com.example.myapplication.ui.components.ProductCard
import com.example.myapplication.ui.theme.DealerGold
import com.example.myapplication.ui.theme.PrimaryWater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit
) {
    val currentUser by AppRepository.currentUser.collectAsState()
    val allProducts by AppRepository.products.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedSortOption by remember { mutableStateOf("Popularity") }

    var selectedImageZoomProduct by remember { mutableStateOf<Product?>(null) }

    // Filter & Sort Logic
    val filteredProducts = remember(allProducts, searchQuery, selectedCategory, selectedSortOption) {
        allProducts.filter { product ->
            val matchesCategory = (selectedCategory == "All" || product.category.equals(selectedCategory, ignoreCase = true))
            val matchesSearch = searchQuery.isBlank() ||
                    product.name.contains(searchQuery, ignoreCase = true) ||
                    product.description.contains(searchQuery, ignoreCase = true) ||
                    product.category.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }.let { list ->
            when (selectedSortOption) {
                "Price: Low to High" -> list.sortedBy { if (currentUser?.role == UserRole.DEALER) it.dealerWholesalePrice else it.retailPrice }
                "Price: High to Low" -> list.sortedByDescending { if (currentUser?.role == UserRole.DEALER) it.dealerWholesalePrice else it.retailPrice }
                "Rating" -> list.sortedByDescending { it.rating }
                else -> list.sortedByDescending { it.isPopular }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search & Filter Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Search Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search 50+ Purifiers, Filters, Pumps, Spares...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category Filter Pills
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(ProductCatalog.CATEGORIES) { cat ->
                        val isSelected = (cat == selectedCategory)
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryWater,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Dealer Wholesale Notice Banner
        if (currentUser?.role == UserRole.DEALER) {
            Surface(
                color = DealerGold.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Storefront, contentDescription = null, tint = DealerGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Wholesale Pricing active for ${currentUser?.companyName ?: "Dealer"}. Special bulk discounts applied!",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Sort & Results Count Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${filteredProducts.size} Products Found",
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )

            // Sort Dropdown Selector
            var showSortMenu by remember { mutableStateOf(false) }
            Box {
                Row(
                    modifier = Modifier
                        .clickable { showSortMenu = true }
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Sort, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Sort: $selectedSortOption", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = { showSortMenu = false }
                ) {
                    listOf("Popularity", "Price: Low to High", "Price: High to Low", "Rating").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontSize = 12.sp) },
                            onClick = {
                                selectedSortOption = option
                                showSortMenu = false
                            }
                        )
                    }
                }
            }
        }

        // Product Catalog Grid
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "No products match your search or filter.", color = Color.Gray)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        userRole = currentUser?.role ?: UserRole.CUSTOMER,
                        onProductClick = onProductClick,
                        onAddToCartClick = onAddToCartClick,
                        onImageZoomClick = { selectedImageZoomProduct = it }
                    )
                }
            }
        }
    }

    // Image Zoom / Lightbox Dialog
    selectedImageZoomProduct?.let { product ->
        ImageLightboxDialog(
            imageUrl = product.imageUrl,
            productName = product.name,
            onDismiss = { selectedImageZoomProduct = null }
        )
    }
}
