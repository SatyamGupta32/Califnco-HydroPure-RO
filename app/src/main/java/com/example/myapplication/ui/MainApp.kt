package com.example.myapplication.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.AppRepository
import com.example.myapplication.model.Product
import com.example.myapplication.model.UserRole
import com.example.myapplication.ui.components.MainTopBar
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme

enum class Screen {
    AUTH,
    CATALOG,
    PRODUCT_DETAIL,
    CART,
    SERVICE,
    DEALER_HUB,
    ADMIN_DESK,
    PROFILE,
    ORDER_HISTORY
}

data class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
)

@Composable
fun MainApp() {
    val currentUser by AppRepository.currentUser.collectAsState()
    val cartItems by AppRepository.cartItems.collectAsState()
    val notifications by AppRepository.notifications.collectAsState()
    val isDarkMode by AppRepository.isDarkMode.collectAsState()

    var currentScreen by remember { mutableStateOf(Screen.CATALOG) }
    var selectedProductForDetail by remember { mutableStateOf<Product?>(null) }

    val unreadNotifCount = notifications.count { !it.isRead }
    val cartCount = cartItems.sumOf { it.quantity }

    MyApplicationTheme(darkTheme = isDarkMode) {
        if (currentUser == null) {
            AuthScreen(onLoginSuccess = { currentScreen = Screen.CATALOG })
        } else {
            Scaffold(
                topBar = {
                    MainTopBar(
                        title = "Califnco HydroPure RO",
                        userRole = currentUser?.role,
                        cartItemCount = cartCount,
                        notificationCount = unreadNotifCount,
                        isDarkMode = isDarkMode,
                        onCartClick = { currentScreen = Screen.CART },
                        onNotificationClick = {
                            AppRepository.markNotificationsRead()
                            currentScreen = Screen.PROFILE
                        },
                        onDarkModeToggle = { AppRepository.toggleDarkMode() }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        val bottomNavItems = mutableListOf(
                            BottomNavItem(Screen.CATALOG, "Catalog", Icons.Default.WaterDrop),
                            BottomNavItem(Screen.SERVICE, "Service", Icons.Default.Build),
                            BottomNavItem(Screen.CART, "Cart", Icons.Default.ShoppingCart)
                        )

                        if (currentUser?.role == UserRole.DEALER) {
                            bottomNavItems.add(BottomNavItem(Screen.DEALER_HUB, "Dealer Hub", Icons.Default.Storefront))
                        }

                        if (currentUser?.role == UserRole.ADMIN) {
                            bottomNavItems.add(BottomNavItem(Screen.ADMIN_DESK, "Admin Panel", Icons.Default.AdminPanelSettings))
                        }

                        bottomNavItems.add(BottomNavItem(Screen.PROFILE, "Profile", Icons.Default.Person))

                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentScreen == item.screen,
                                onClick = { currentScreen = item.screen },
                                icon = { Icon(item.icon, contentDescription = item.title) },
                                label = { Text(item.title) }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentScreen) {
                        Screen.AUTH -> AuthScreen(onLoginSuccess = { currentScreen = Screen.CATALOG })
                        Screen.CATALOG -> CatalogScreen(
                            onProductClick = { product ->
                                selectedProductForDetail = product
                                currentScreen = Screen.PRODUCT_DETAIL
                            },
                            onAddToCartClick = { product -> AppRepository.addToCart(product, 1) }
                        )
                        Screen.PRODUCT_DETAIL -> {
                            selectedProductForDetail?.let { product ->
                                ProductDetailScreen(
                                    product = product,
                                    onBackClick = { currentScreen = Screen.CATALOG },
                                    onAddToCartClick = { p, qty ->
                                        AppRepository.addToCart(p, qty)
                                        currentScreen = Screen.CART
                                    },
                                    onBuyNowClick = { p, qty ->
                                        AppRepository.addToCart(p, qty)
                                        currentScreen = Screen.CART
                                    }
                                )
                            }
                        }
                        Screen.CART -> CartScreen(
                            onCheckoutSuccess = { currentScreen = Screen.ORDER_HISTORY }
                        )
                        Screen.SERVICE -> ServiceBookingScreen()
                        Screen.DEALER_HUB -> DealerDashboardScreen(
                            onGoToCatalogClick = { currentScreen = Screen.CATALOG }
                        )
                        Screen.ADMIN_DESK -> AdminDashboardScreen()
                        Screen.PROFILE -> ProfileScreen(
                            onNavigateToOrderHistory = { currentScreen = Screen.ORDER_HISTORY },
                            onNavigateToServiceDesk = { currentScreen = Screen.SERVICE },
                            onLogout = {
                                AppRepository.setCurrentUser(null)
                                currentScreen = Screen.AUTH
                            }
                        )
                        Screen.ORDER_HISTORY -> OrderHistoryScreen()
                    }
                }
            }
        }
    }
}
