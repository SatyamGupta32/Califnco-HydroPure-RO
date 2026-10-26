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
import com.example.myapplication.model.ServiceBooking
import com.example.myapplication.ui.theme.PrimaryWater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceBookingScreen() {
    val currentUser by AppRepository.currentUser.collectAsState()
    val allServiceBookings by AppRepository.serviceBookings.collectAsState()

    var activeTab by remember { mutableIntStateOf(0) } // 0: Book Service, 1: My Service Requests

    var purifierModel by remember { mutableStateOf("HydroPure Grand RO+UV") }
    var selectedServiceType by remember { mutableStateOf("Regular Maintenance & Filter Change") }
    var issueDescription by remember { mutableStateOf("TDS level reading high, filter replacement needed.") }
    var preferredDate by remember { mutableStateOf("2026-10-28") }
    var preferredTimeSlot by remember { mutableStateOf("10:00 AM - 01:00 PM") }
    var serviceAddress by remember { mutableStateOf("Flat 402, Green Park Apartments, Sector 15, New Delhi") }

    var showSuccessSnackbar by remember { mutableStateOf(false) }

    val serviceTypes = listOf(
        "Regular Maintenance & Filter Change",
        "RO Purifier New Installation",
        "Leakage Repair & Pump Check",
        "Water Quality & TDS Inspection",
        "Membrane Replacement"
    )

    val myBookings = allServiceBookings.filter { it.userId == currentUser?.uid }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Header
        TabRow(selectedTabIndex = activeTab) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = { Text("Book RO Service", fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Build, contentDescription = null) }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = { Text("Service Desk (${myBookings.size})", fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Engineering, contentDescription = null) }
            )
        }

        if (activeTab == 0) {
            // Book Service Form
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(text = "Schedule Water Purifier Servicing", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Certified technicians at your doorstep within 24 hours.", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                }

                item {
                    OutlinedTextField(
                        value = purifierModel,
                        onValueChange = { purifierModel = it },
                        label = { Text("Purifier Model / Brand") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                item {
                    Text(text = "Select Service Type", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Column {
                        serviceTypes.forEach { type ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                RadioButton(
                                    selected = selectedServiceType == type,
                                    onClick = { selectedServiceType = type }
                                )
                                Text(text = type, fontSize = 13.sp)
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = issueDescription,
                        onValueChange = { issueDescription = it },
                        label = { Text("Issue Description / Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        minLines = 2
                    )
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = preferredDate,
                            onValueChange = { preferredDate = it },
                            label = { Text("Preferred Date") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedTextField(
                            value = preferredTimeSlot,
                            onValueChange = { preferredTimeSlot = it },
                            label = { Text("Time Slot") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                item {
                    OutlinedTextField(
                        value = serviceAddress,
                        onValueChange = { serviceAddress = it },
                        label = { Text("Service Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        minLines = 2
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            AppRepository.createServiceBooking(
                                purifierModel = purifierModel,
                                serviceType = selectedServiceType,
                                issueDescription = issueDescription,
                                preferredDate = preferredDate,
                                preferredTimeSlot = preferredTimeSlot,
                                address = serviceAddress
                            )
                            showSuccessSnackbar = true
                            activeTab = 1
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryWater)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Confirm Service Booking", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // Service History List
            if (myBookings.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No service bookings found.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(myBookings) { booking ->
                        ServiceBookingCard(booking = booking)
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceBookingCard(booking: ServiceBooking) {
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
                Text(text = "#${booking.bookingId}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryWater)

                Surface(
                    color = when (booking.status) {
                        "COMPLETED" -> Color(0xFFE8F5E9)
                        "ASSIGNED" -> Color(0xFFE3F2FD)
                        else -> Color(0xFFFFF3E0)
                    },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = booking.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (booking.status) {
                            "COMPLETED" -> Color(0xFF2E7D32)
                            "ASSIGNED" -> Color(0xFF1565C0)
                            else -> Color(0xFFE65100)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = booking.purifierModel, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(text = "Service: ${booking.serviceType}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Event, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${booking.preferredDate} (${booking.preferredTimeSlot})", fontSize = 12.sp, color = Color.Gray)
            }

            if (!booking.assignedTechnician.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(14.dp), tint = PrimaryWater)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Technician: ${booking.assignedTechnician}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryWater)
                }
            }
        }
    }
}
