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
import com.example.myapplication.model.Order
import com.example.myapplication.model.ServiceBooking
import com.example.myapplication.model.User
import com.example.myapplication.ui.theme.DealerGold
import com.example.myapplication.ui.theme.PrimaryWater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen() {
    var adminTab by remember { mutableIntStateOf(0) } // 0: Orders, 1: Dealers Desk, 2: Service Desk

    val allOrders by AppRepository.orders.collectAsState()
    val allUsers by AppRepository.allUsers.collectAsState()
    val allServiceBookings by AppRepository.serviceBookings.collectAsState()

    val pendingDealers = allUsers.filter { it.dealerStatus == DealerStatus.PENDING }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Admin Header
        Surface(
            color = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = "Califnco Admin Control Desk", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                    Text(text = "Manage Customer & Dealer Orders, Approvals & Technicians", fontSize = 11.sp, color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f))
                }
            }
        }

        // Navigation Tabs
        TabRow(selectedTabIndex = adminTab) {
            Tab(
                selected = adminTab == 0,
                onClick = { adminTab = 0 },
                text = { Text("Orders (${allOrders.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = adminTab == 1,
                onClick = { adminTab = 1 },
                text = { Text("Dealers (${pendingDealers.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Storefront, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = adminTab == 2,
                onClick = { adminTab = 2 },
                text = { Text("Service Desk (${allServiceBookings.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Engineering, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        when (adminTab) {
            0 -> AdminOrdersTab(orders = allOrders)
            1 -> AdminDealersTab(pendingDealers = pendingDealers)
            2 -> AdminServiceDeskTab(serviceBookings = allServiceBookings)
        }
    }
}

@Composable
fun AdminOrdersTab(orders: List<Order>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(orders) { order ->
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
                        Text(text = "₹${order.totalAmount.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Customer: ${order.userName} (${order.userPhone})", fontSize = 12.sp)
                    Text(text = "Address: ${order.address}", fontSize = 11.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Status: ${order.status}", fontSize = 12.sp, fontWeight = FontWeight.Bold)

                        Row {
                            Button(
                                onClick = { AppRepository.updateOrderStatus(order.orderId, "PROCESSING") },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text("Process", fontSize = 10.sp)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Button(
                                onClick = { AppRepository.updateOrderStatus(order.orderId, "SHIPPED") },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text("Ship", fontSize = 10.sp)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Button(
                                onClick = { AppRepository.updateOrderStatus(order.orderId, "DELIVERED") },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                            ) {
                                Text("Deliver", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminDealersTab(pendingDealers: List<User>) {
    if (pendingDealers.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("No pending dealer approval applications.", color = Color.Gray)
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pendingDealers) { dealer ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = dealer.companyName ?: "Business Account", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Applicant: ${dealer.name} (${dealer.email})", fontSize = 13.sp)
                        Text(text = "GST Number: ${dealer.gstNumber ?: "N/A"}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DealerGold)

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            OutlinedButton(
                                onClick = { AppRepository.rejectDealer(dealer.uid) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Reject")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { AppRepository.approveDealer(dealer.uid) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                            ) {
                                Text("Approve Dealer")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminServiceDeskTab(serviceBookings: List<ServiceBooking>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(serviceBookings) { booking ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Service #${booking.bookingId} - ${booking.purifierModel}", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Customer: ${booking.userName} (${booking.userPhone})", fontSize = 12.sp)
                    Text(text = "Issue: ${booking.issueDescription}", fontSize = 12.sp, color = Color.Gray)
                    Text(text = "Date: ${booking.preferredDate} (${booking.preferredTimeSlot})", fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Status: ${booking.status}", fontSize = 12.sp, fontWeight = FontWeight.Bold)

                        Row {
                            Button(
                                onClick = { AppRepository.updateServiceStatus(booking.bookingId, "ASSIGNED", "Vijay Tech (ID: TK-901)") },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text("Assign Tech", fontSize = 10.sp)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Button(
                                onClick = { AppRepository.updateServiceStatus(booking.bookingId, "COMPLETED") },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                            ) {
                                Text("Complete", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
