package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.myapplication.model.UserRole
import com.example.myapplication.ui.theme.DealerGold
import com.example.myapplication.ui.theme.PrimaryWater

@Composable
fun ProfileScreen(
    onNavigateToOrderHistory: () -> Unit,
    onNavigateToServiceDesk: () -> Unit,
    onLogout: () -> Unit
) {
    val currentUser by AppRepository.currentUser.collectAsState()
    val isDarkMode by AppRepository.isDarkMode.collectAsState()

    var showReferralToast by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // User Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(PrimaryWater, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (currentUser?.name?.take(1) ?: "U").uppercase(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = currentUser?.name ?: "User Profile",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = currentUser?.email ?: "",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = when (currentUser?.role) {
                        UserRole.ADMIN -> MaterialTheme.colorScheme.errorContainer
                        UserRole.DEALER -> DealerGold.copy(alpha = 0.2f)
                        else -> MaterialTheme.colorScheme.primaryContainer
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = when (currentUser?.role) {
                            UserRole.ADMIN -> "ADMINISTRATOR CONTROL"
                            UserRole.DEALER -> "APPROVED WHOLESALE DEALER"
                            else -> "CUSTOMER ACCOUNT"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (currentUser?.role) {
                            UserRole.ADMIN -> MaterialTheme.colorScheme.error
                            UserRole.DEALER -> DealerGold
                            else -> PrimaryWater
                        },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Referral Card (Bonus Requirement)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryWater.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "🎁 Your Referral Code", fontSize = 12.sp, color = Color.Gray)
                    Text(text = currentUser?.referralCode ?: "CALIF2026", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryWater)
                    Text(text = "Share with friends & earn ₹200 discount!", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                }

                Button(
                    onClick = { showReferralToast = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryWater)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Share", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Menu Options
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column {
                ListItem(
                    headlineContent = { Text("My Orders History", fontWeight = FontWeight.Bold) },
                    leadingContent = { Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = PrimaryWater) },
                    trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))

                ListItem(
                    headlineContent = { Text("Service Desk & RO Maintenance", fontWeight = FontWeight.Bold) },
                    leadingContent = { Icon(Icons.Default.Build, contentDescription = null, tint = PrimaryWater) },
                    trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))

                ListItem(
                    headlineContent = { Text("Dark Theme", fontWeight = FontWeight.Bold) },
                    leadingContent = { Icon(Icons.Default.DarkMode, contentDescription = null, tint = PrimaryWater) },
                    trailingContent = {
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { AppRepository.toggleDarkMode() }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout / Switch User Button
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Switch User / Logout", fontWeight = FontWeight.Bold)
        }
    }

    if (showReferralToast) {
        AlertDialog(
            onDismissRequest = { showReferralToast = false },
            title = { Text("Referral Link Shared!") },
            text = { Text("Referral code ${currentUser?.referralCode} copied to clipboard! Give your friends ₹200 OFF on their first RO purifier order.") },
            confirmButton = {
                Button(onClick = { showReferralToast = false }) {
                    Text("OK")
                }
            }
        )
    }
}
