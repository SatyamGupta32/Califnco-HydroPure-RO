package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.AppRepository
import com.example.myapplication.model.Product
import com.example.myapplication.model.UserRole
import com.example.myapplication.ui.components.ImageLightboxDialog
import com.example.myapplication.ui.theme.DealerGold
import com.example.myapplication.ui.theme.PrimaryWater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit,
    onAddToCartClick: (Product, Int) -> Unit,
    onBuyNowClick: (Product, Int) -> Unit
) {
    val currentUser by AppRepository.currentUser.collectAsState()
    var quantity by remember { mutableIntStateOf(1) }
    var showLightbox by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val isDealer = currentUser?.role == UserRole.DEALER
    val effectivePrice = if (isDealer) product.dealerWholesalePrice else product.retailPrice

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Back Top Bar
        TopAppBar(
            title = { Text(text = "Product Details", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Product Image Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .clickable { showLightbox = true }
                )

                IconButton(
                    onClick = { showLightbox = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.ZoomIn, contentDescription = "Zoom Image", tint = Color.White)
                }

                Surface(
                    color = PrimaryWater,
                    shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = product.category,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title & Rating
            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color(0xFFFFF8E1),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB800), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${product.rating} / 5.0", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5D4037))
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${product.reviewCount} Verified Customer Reviews",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pricing Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDealer) DealerGold.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (isDealer) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Store, contentDescription = null, tint = DealerGold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "OFFICIAL WHOLESALE DEALER PRICE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DealerGold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹${effectivePrice.toInt()}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDealer) DealerGold else PrimaryWater
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "MRP ₹${product.mrp.toInt()}",
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Retail ₹${product.retailPrice.toInt()}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    if (isDealer) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Min Wholesale Order Quantity: ${product.minDealerOrderQty} units",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Description
            Text(text = "Description", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = product.description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))

            Spacer(modifier = Modifier.height(20.dp))

            // Specifications Table
            if (product.specifications.isNotEmpty()) {
                Text(text = "Technical Specifications", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        product.specifications.entries.forEachIndexed { index, entry ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = entry.key, fontSize = 13.sp, color = Color.Gray, modifier = Modifier.weight(1f))
                                Text(
                                    text = entry.value,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (index < product.specifications.size - 1) {
                                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Bottom Action Bar
        Surface(
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Quantity Counter
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                        .padding(horizontal = 4.dp)
                ) {
                    IconButton(
                        onClick = { if (quantity > 1) quantity-- },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
                    }
                    Text(
                        text = "$quantity",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(
                        onClick = { quantity++ },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase")
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { onAddToCartClick(product, quantity) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDealer) DealerGold else PrimaryWater
                    )
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add to Cart",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDealer) Color.Black else Color.White
                    )
                }
            }
        }
    }

    if (showLightbox) {
        ImageLightboxDialog(
            imageUrl = product.imageUrl,
            productName = product.name,
            onDismiss = { showLightbox = false }
        )
    }
}
