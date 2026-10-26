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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.AppRepository
import com.example.myapplication.model.CartItem
import com.example.myapplication.model.UserRole
import com.example.myapplication.ui.components.OSMMapPickerModal
import com.example.myapplication.ui.theme.DealerGold
import com.example.myapplication.ui.theme.PrimaryWater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onCheckoutSuccess: (String) -> Unit
) {
    val currentUser by AppRepository.currentUser.collectAsState()
    val cartItems by AppRepository.cartItems.collectAsState()

    var address by remember { mutableStateOf("Flat 402, Green Park Apartments, Sector 15, New Delhi") }
    var selectedLat by remember { mutableStateOf<Double?>(28.6139) }
    var selectedLng by remember { mutableStateOf<Double?>(77.2090) }

    var couponCode by remember { mutableStateOf("") }
    var appliedDiscount by remember { mutableDoubleStateOf(0.0) }
    var couponMessage by remember { mutableStateOf("") }

    var selectedPaymentMethod by remember { mutableStateOf("Razorpay Test Mode") }

    var showOSMMapModal by remember { mutableStateOf(false) }
    var showPaymentSuccessDialog by remember { mutableStateOf(false) }
    var lastCreatedOrderId by remember { mutableStateOf("") }

    val isDealer = currentUser?.role == UserRole.DEALER

    val subtotal = cartItems.sumOf { item ->
        val price = if (isDealer) item.product.dealerWholesalePrice else item.product.retailPrice
        price * item.quantity
    }
    val finalTotal = (subtotal - appliedDiscount).coerceAtLeast(0.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.RemoveShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Your Cart is Empty", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Add RO Purifiers, Filters or Spares from catalog", fontSize = 12.sp, color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Cart Items List
                item {
                    Text(text = "Shopping Cart (${cartItems.size} items)", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                }

                items(cartItems) { item ->
                    CartItemRow(
                        cartItem = item,
                        isDealer = isDealer,
                        onQuantityChange = { newQty -> AppRepository.updateCartQuantity(item.product.id, newQty) },
                        onRemove = { AppRepository.removeFromCart(item.product.id) }
                    )
                }

                // Delivery Address Section with OSM Map Picker
                item {
                    Spacer(modifier = Modifier.height(8.dp))
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryWater)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = "Delivery Address", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }

                                TextButton(onClick = { showOSMMapModal = true }) {
                                    Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Pick on OSM Map", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                maxLines = 2
                            )
                        }
                    }
                }

                // Coupon / Referral Code Section
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Promo Code / Referral Discount", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = couponCode,
                                    onValueChange = { couponCode = it.uppercase() },
                                    placeholder = { Text("Enter CALIF2026 or FIRST20") },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        when (couponCode.trim()) {
                                            "CALIF2026", "FIRST20" -> {
                                                appliedDiscount = (subtotal * 0.15).coerceAtMost(500.0)
                                                couponMessage = "🎉 Code Applied! Saved ₹${appliedDiscount.toInt()}"
                                            }
                                            "RAHUL2026", "APEXDEALER" -> {
                                                appliedDiscount = 200.0
                                                couponMessage = "🎉 Referral Code Applied! ₹200 Discount"
                                            }
                                            else -> {
                                                appliedDiscount = 0.0
                                                couponMessage = "❌ Invalid Promo Code"
                                            }
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Apply")
                                }
                            }

                            if (couponMessage.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = couponMessage,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (appliedDiscount > 0) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }

                // Payment Method Selector
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Payment Gateway", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedPaymentMethod == "Razorpay Test Mode",
                                    onClick = { selectedPaymentMethod = "Razorpay Test Mode" }
                                )
                                Column {
                                    Text(text = "Razorpay Test Mode (UPI, Cards, NetBanking)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Key: rzp_test_ (Zero KYC Test Mode)", fontSize = 10.sp, color = Color.Gray)
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedPaymentMethod == "Cash on Delivery",
                                    onClick = { selectedPaymentMethod = "Cash on Delivery" }
                                )
                                Text(text = "Cash on Delivery (COD)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Order Price Calculation Summary
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Price Details", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Subtotal", fontSize = 13.sp)
                                Text(text = "₹${subtotal.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }

                            if (appliedDiscount > 0) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Promo Discount", fontSize = 13.sp, color = Color(0xFF2E7D32))
                                    Text(text = "- ₹${appliedDiscount.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Delivery Charges", fontSize = 13.sp)
                                Text(text = "FREE", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Total Payable Amount", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text(text = "₹${finalTotal.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryWater)
                            }
                        }
                    }
                }
            }

            // Checkout Button Bar
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
                    Column {
                        Text(text = "Total Amount", fontSize = 11.sp, color = Color.Gray)
                        Text(text = "₹${finalTotal.toInt()}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryWater)
                    }

                    Button(
                        onClick = {
                            val createdOrder = AppRepository.placeOrder(
                                address = address,
                                latitude = selectedLat,
                                longitude = selectedLng,
                                paymentMethod = selectedPaymentMethod,
                                appliedDiscount = appliedDiscount
                            )
                            if (createdOrder != null) {
                                lastCreatedOrderId = createdOrder.orderId
                                showPaymentSuccessDialog = true
                            }
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .padding(start = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDealer) DealerGold else PrimaryWater
                        )
                    ) {
                        Icon(Icons.Default.Payment, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (selectedPaymentMethod.contains("Razorpay")) "Razorpay Checkout" else "Place Order",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDealer) Color.Black else Color.White
                        )
                    }
                }
            }
        }
    }

    // OSM Map Picker Dialog
    if (showOSMMapModal) {
        OSMMapPickerModal(
            initialLat = selectedLat ?: 28.6139,
            initialLng = selectedLng ?: 77.2090,
            onAddressSelected = { newAddr, lat, lng ->
                address = newAddr
                selectedLat = lat
                selectedLng = lng
            },
            onDismiss = { showOSMMapModal = false }
        )
    }

    // Razorpay Test Payment Success Confirmation Dialog
    if (showPaymentSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text("Razorpay Test Payment Successful!")
            },
            text = {
                Column {
                    Text("Payment ID: pay_rzp_test_${System.currentTimeMillis().toString().takeLast(8)}")
                    Text("Order ID: #$lastCreatedOrderId")
                    Text("Amount Paid: ₹${finalTotal.toInt()}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Order details & notification sent.", fontSize = 12.sp, color = Color.Gray)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPaymentSuccessDialog = false
                        onCheckoutSuccess(lastCreatedOrderId)
                    }
                ) {
                    Text("View Order History")
                }
            }
        )
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    isDealer: Boolean,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    val price = if (isDealer) cartItem.product.dealerWholesalePrice else cartItem.product.retailPrice

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = cartItem.product.imageUrl,
                contentDescription = cartItem.product.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.product.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "₹${price.toInt()}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDealer) DealerGold else PrimaryWater
                    )
                    if (isDealer) {
                        Text(
                            text = " (Wholesale)",
                            fontSize = 10.sp,
                            color = DealerGold,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onRemove, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(18.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(6.dp))
                ) {
                    IconButton(
                        onClick = { onQuantityChange(cartItem.quantity - 1) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "-", modifier = Modifier.size(14.dp))
                    }

                    Text(
                        text = "${cartItem.quantity}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    IconButton(
                        onClick = { onQuantityChange(cartItem.quantity + 1) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "+", modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}
