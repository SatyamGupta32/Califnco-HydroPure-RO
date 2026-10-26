package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.AppRepository
import com.example.myapplication.model.DealerStatus
import com.example.myapplication.model.Product
import com.example.myapplication.ui.theme.DealerGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealerDashboardScreen(
    onGoToCatalogClick: () -> Unit
) {
    val currentUser by AppRepository.currentUser.collectAsState()
    val allProducts by AppRepository.products.collectAsState()
    val allOrders by AppRepository.orders.collectAsState()

    val isApproved = currentUser?.dealerStatus == DealerStatus.APPROVED
    val isPending = currentUser?.dealerStatus == DealerStatus.PENDING

    val dealerOrders = allOrders.filter { it.userId == currentUser?.uid }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Dealer Status Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isApproved) DealerGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null,
                            tint = DealerGold,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = currentUser?.companyName ?: "Wholesale Dealer Hub",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "GST: ${currentUser?.gstNumber ?: "Pending GST"}",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Surface(
                        color = if (isApproved) DealerGold else Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (isApproved) "APPROVED DEALER" else if (isPending) "UNDER REVIEW" else "STANDARD",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (isApproved) {
                    Text(
                        text = "✨ You have full access to Wholesale Pricing across 50+ Water Purifiers, Pumps & Spares.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                } else if (isPending) {
                    Text(
                        text = "⏳ Your Dealer application is under Admin verification. You can also log in using the Quick Admin role to approve it instantly!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bulk Ordering Catalog Highlights
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Wholesale Bulk Rates (50+ Items)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = onGoToCatalogClick) {
                Text("View All in Catalog", fontSize = 12.sp, color = DealerGold, fontWeight = FontWeight.Bold)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(allProducts.take(10)) { product ->
                DealerProductRow(product = product, onAddToCart = { AppRepository.addToCart(product, product.minDealerOrderQty) })
            }
        }
    }
}

@Composable
fun DealerProductRow(
    product: Product,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Wholesale ₹${product.dealerWholesalePrice.toInt()}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DealerGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Retail ₹${product.retailPrice.toInt()}", fontSize = 11.sp, color = Color.Gray)
                }
                Text(text = "Min Order Qty: ${product.minDealerOrderQty} pcs", fontSize = 10.sp, color = Color.Gray)
            }

            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(containerColor = DealerGold),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(text = "Order Bulk", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}
