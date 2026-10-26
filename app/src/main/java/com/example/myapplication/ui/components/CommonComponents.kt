package com.example.myapplication.ui.components

import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.myapplication.model.Product
import com.example.myapplication.model.UserRole
import com.example.myapplication.ui.theme.DealerGold
import com.example.myapplication.ui.theme.PrimaryWater
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String = "Califnco HydroPure RO",
    userRole: UserRole? = UserRole.CUSTOMER,
    cartItemCount: Int = 0,
    notificationCount: Int = 0,
    isDarkMode: Boolean = false,
    onCartClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onDarkModeToggle: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = null,
                        tint = PrimaryWater,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                userRole?.let { role ->
                    Surface(
                        color = when (role) {
                            UserRole.ADMIN -> MaterialTheme.colorScheme.errorContainer
                            UserRole.DEALER -> DealerGold.copy(alpha = 0.2f)
                            UserRole.CUSTOMER -> MaterialTheme.colorScheme.primaryContainer
                        },
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = when (role) {
                                UserRole.ADMIN -> "ADMIN PANEL"
                                UserRole.DEALER -> "DEALER (WHOLESALE)"
                                UserRole.CUSTOMER -> "CUSTOMER RETAIL"
                            },
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (role) {
                                UserRole.ADMIN -> MaterialTheme.colorScheme.error
                                UserRole.DEALER -> DealerGold
                                UserRole.CUSTOMER -> MaterialTheme.colorScheme.primary
                            },
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = onDarkModeToggle) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Toggle Dark Mode"
                )
            }

            IconButton(onClick = onNotificationClick) {
                BadgedBox(
                    badge = {
                        if (notificationCount > 0) {
                            Badge { Text(notificationCount.toString()) }
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "Notifications")
                }
            }

            IconButton(onClick = onCartClick) {
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge { Text(cartItemCount.toString()) }
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = "Shopping Cart")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun ProductCard(
    product: Product,
    userRole: UserRole = UserRole.CUSTOMER,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    onImageZoomClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )

                // Category Tag
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = product.category,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Image Lightbox/Zoom Button
                IconButton(
                    onClick = { onImageZoomClick(product) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(32.dp)
                        .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ZoomIn,
                        contentDescription = "Zoom Image",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                if (userRole == UserRole.DEALER) {
                    Surface(
                        color = DealerGold,
                        shape = RoundedCornerShape(bottomStart = 8.dp),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Text(
                            text = "Wholesale Available",
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB800),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${product.rating}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " (${product.reviewCount})",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price display logic based on role
                val showWholesale = (userRole == UserRole.DEALER)
                val displayPrice = if (showWholesale) product.dealerWholesalePrice else product.retailPrice

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "₹${displayPrice.toInt()}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (showWholesale) DealerGold else MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "₹${product.mrp.toInt()}",
                                fontSize = 11.sp,
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            )
                        }

                        if (showWholesale) {
                            Text(
                                text = "Wholesale (Min Qty: ${product.minDealerOrderQty})",
                                fontSize = 10.sp,
                                color = DealerGold,
                                fontWeight = FontWeight.Medium
                            )
                        } else {
                            val savings = ((product.mrp - product.retailPrice) / product.mrp * 100).toInt()
                            Text(
                                text = "Save $savings%",
                                fontSize = 10.sp,
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Button(
                        onClick = { onAddToCartClick(product) },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showWholesale) DealerGold else MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = "Add",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Add", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ImageLightboxDialog(
    imageUrl: String,
    productName: String,
    onDismiss: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.92f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = productName,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = (scale * zoom).coerceIn(1f, 4f)
                                if (scale > 1f) {
                                    offsetX += pan.x
                                    offsetY += pan.y
                                } else {
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = productName,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offsetX,
                                translationY = offsetY
                            ),
                        contentScale = ContentScale.Fit
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Pinch to Zoom | Double Tap / Drag to Move",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OSMMapPickerModal(
    initialLat: Double = 19.0760, // Default Mumbai
    initialLng: Double = 72.8777,
    onAddressSelected: (String, Double, Double) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedPoint by remember { mutableStateOf(GeoPoint(initialLat, initialLng)) }
    var selectedAddressText by remember { mutableStateOf("Fetching location address...") }

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        selectedAddressText = "Selected Location: (${String.format("%.4f", selectedPoint.latitude)}, ${String.format("%.4f", selectedPoint.longitude)})"
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = PrimaryWater
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Select Delivery Location (OpenStreetMap)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    AndroidView(
                        factory = { ctx ->
                            MapView(ctx).apply {
                                setTileSource(TileSourceFactory.MAPNIK)
                                setMultiTouchControls(true)
                                controller.setZoom(14.0)
                                controller.setCenter(selectedPoint)

                                val marker = Marker(this).apply {
                                    position = selectedPoint
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    title = "Delivery Location"
                                }
                                overlays.add(marker)

                                setOnTouchListener { _, _ ->
                                    val center = mapCenter as GeoPoint
                                    selectedPoint = center
                                    marker.position = center
                                    selectedAddressText = "Location: (${String.format("%.4f", center.latitude)}, ${String.format("%.4f", center.longitude)}) - Tap Confirm"
                                    invalidate()
                                    false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    // Target Pin Center Indicator
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "Pin",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = selectedAddressText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val fullAddr = "Address Pin (${String.format("%.4f", selectedPoint.latitude)}, ${String.format("%.4f", selectedPoint.longitude)})"
                        onAddressSelected(fullAddr, selectedPoint.latitude, selectedPoint.longitude)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Confirm This Location", fontSize = 14.sp)
                }
            }
        }
    }
}
