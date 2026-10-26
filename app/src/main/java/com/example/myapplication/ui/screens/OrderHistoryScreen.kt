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
import com.example.myapplication.model.Order
import com.example.myapplication.ui.theme.PrimaryWater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen() {
    val currentUser by AppRepository.currentUser.collectAsState()
    val allOrders by AppRepository.orders.collectAsState()

    val myOrders = allOrders.filter { it.userId == currentUser?.uid }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(text = "My Order History (${myOrders.size})", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        if (myOrders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No orders placed yet.", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(myOrders) { order ->
                    OrderCard(order = order)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "#${order.orderId}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PrimaryWater)

                Surface(
                    color = when (order.status) {
                        "DELIVERED" -> Color(0xFFE8F5E9)
                        "SHIPPED" -> Color(0xFFE3F2FD)
                        else -> Color(0xFFFFF3E0)
                    },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = order.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (order.status) {
                            "DELIVERED" -> Color(0xFF2E7D32)
                            "SHIPPED" -> Color(0xFF1565C0)
                            else -> Color(0xFFE65100)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            order.items.forEach { cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${cartItem.quantity}x ${cartItem.product.name}", fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Text(text = "₹${(cartItem.product.retailPrice * cartItem.quantity).toInt()}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Payment: ${order.paymentMethod}", fontSize = 11.sp, color = Color.Gray)
                    Text(text = "Address: ${order.address}", fontSize = 10.sp, color = Color.Gray, maxLines = 1)
                }

                Text(text = "Total ₹${order.totalAmount.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryWater)
            }
        }
    }
}
