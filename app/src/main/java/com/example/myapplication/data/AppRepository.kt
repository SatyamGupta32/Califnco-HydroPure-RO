package com.example.myapplication.data

import com.example.myapplication.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object AppRepository {

    // Default Demo Users
    val DEMO_CUSTOMER = User(
        uid = "usr_cust_01",
        name = "Rahul Sharma",
        email = "rahul@example.com",
        phone = "+91 9876543210",
        role = UserRole.CUSTOMER,
        referralCode = "RAHUL2026"
    )

    val DEMO_DEALER_APPROVED = User(
        uid = "usr_deal_01",
        name = "Apex Aqua Enterprises",
        email = "dealer@waterwholesale.com",
        phone = "+91 9812345678",
        role = UserRole.DEALER,
        dealerStatus = DealerStatus.APPROVED,
        companyName = "Apex Aqua Enterprises Pvt Ltd",
        gstNumber = "27AAACA123411Z5",
        referralCode = "APEXDEALER"
    )

    val DEMO_DEALER_PENDING = User(
        uid = "usr_deal_02",
        name = "Metro Water Solutions",
        email = "metro@watersolutions.com",
        phone = "+91 9765432109",
        role = UserRole.CUSTOMER,
        dealerStatus = DealerStatus.PENDING,
        companyName = "Metro Water Solutions",
        gstNumber = "07BBAAC567811Z9",
        referralCode = "METRODEAL"
    )

    val DEMO_ADMIN = User(
        uid = "usr_admin_01",
        name = "Califnco Admin Desk",
        email = "admin@califnco.com",
        phone = "+91 9999999999",
        role = UserRole.ADMIN,
        referralCode = "CALIFADMIN"
    )

    // Current User
    private val _currentUser = MutableStateFlow<User?>(DEMO_CUSTOMER)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Products
    private val _products = MutableStateFlow<List<Product>>(ProductCatalog.products)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    // Cart
    private val _cartItems = MutableStateFlow<List<CartItem>>(
        listOf(
            CartItem(ProductCatalog.products[0], 1),
            CartItem(ProductCatalog.products[3], 2)
        )
    )
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Orders
    private val _orders = MutableStateFlow<List<Order>>(
        listOf(
            Order(
                orderId = "ORD_88201",
                userId = "usr_cust_01",
                userName = "Rahul Sharma",
                userPhone = "+91 9876543210",
                userRole = UserRole.CUSTOMER,
                items = listOf(CartItem(ProductCatalog.products[0], 1)),
                totalAmount = 12999.0,
                address = "Flat 402, Green Park Apartments, Sector 15, New Delhi",
                status = "PROCESSING",
                createdAt = System.currentTimeMillis() - 86400000L
            ),
            Order(
                orderId = "ORD_88190",
                userId = "usr_deal_01",
                userName = "Apex Aqua Enterprises",
                userPhone = "+91 9812345678",
                userRole = UserRole.DEALER,
                items = listOf(CartItem(ProductCatalog.products[12], 10)), // Pumps bulk order
                totalAmount = 11500.0,
                isDealerOrder = true,
                address = "Warehouse 12, Industrial Area Phase 2, Mumbai",
                status = "SHIPPED",
                createdAt = System.currentTimeMillis() - 172800000L
            )
        )
    )
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    // Service Bookings
    private val _serviceBookings = MutableStateFlow<List<ServiceBooking>>(
        listOf(
            ServiceBooking(
                bookingId = "SRV_1092",
                userId = "usr_cust_01",
                userName = "Rahul Sharma",
                userPhone = "+91 9876543210",
                purifierModel = "HydroPure Grand RO+UV",
                serviceType = "Regular Servicing & Filter Change",
                issueDescription = "Annual filter change required and TDS check.",
                preferredDate = "2026-10-28",
                preferredTimeSlot = "10:00 AM - 01:00 PM",
                address = "Flat 402, Green Park Apartments, Sector 15, New Delhi",
                status = "ASSIGNED",
                assignedTechnician = "Amit Kumar (Tech ID: TK-402)"
            )
        )
    )
    val serviceBookings: StateFlow<List<ServiceBooking>> = _serviceBookings.asStateFlow()

    // Dealer Applications
    private val _allUsers = MutableStateFlow<List<User>>(
        listOf(DEMO_CUSTOMER, DEMO_DEALER_APPROVED, DEMO_DEALER_PENDING, DEMO_ADMIN)
    )
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    // Notifications
    private val _notifications = MutableStateFlow<List<AppNotification>>(
        listOf(
            AppNotification(
                id = "notif_1",
                title = "Welcome to Califnco HydroPure!",
                message = "Get 20% OFF on your first RO Water Purifier order. Use code FIRST20.",
                type = "PROMO"
            ),
            AppNotification(
                id = "notif_2",
                title = "Order Processing Update",
                message = "Your order #ORD_88201 is currently being packed for delivery.",
                type = "ORDER"
            )
        )
    )
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    // Dark Mode preference
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    // AUTH METHODS
    fun setCurrentUser(user: User?) {
        _currentUser.value = user
    }

    fun loginAsDemoCustomer() {
        _currentUser.value = DEMO_CUSTOMER
    }

    fun loginAsDemoDealer() {
        _currentUser.value = DEMO_DEALER_APPROVED
    }

    fun loginAsPendingDealer() {
        _currentUser.value = DEMO_DEALER_PENDING
    }

    fun loginAsAdmin() {
        _currentUser.value = DEMO_ADMIN
    }

    fun applyForDealerStatus(companyName: String, gstNumber: String) {
        val user = _currentUser.value ?: return
        val updated = user.copy(
            dealerStatus = DealerStatus.PENDING,
            companyName = companyName,
            gstNumber = gstNumber
        )
        _currentUser.value = updated
        _allUsers.value = _allUsers.value.map { if (it.uid == user.uid) updated else it }
        
        addNotification(
            title = "Dealer Application Submitted",
            message = "Your request for Dealer account ($companyName) is under Admin review.",
            type = "DEALER"
        )
    }

    fun approveDealer(userId: String) {
        _allUsers.value = _allUsers.value.map { u ->
            if (u.uid == userId) {
                u.copy(role = UserRole.DEALER, dealerStatus = DealerStatus.APPROVED)
            } else u
        }
        if (_currentUser.value?.uid == userId) {
            _currentUser.value = _currentUser.value?.copy(role = UserRole.DEALER, dealerStatus = DealerStatus.APPROVED)
        }
        addNotification(
            title = "Dealer Approved!",
            message = "User ID $userId has been approved as an official Dealer with Wholesale pricing.",
            type = "DEALER"
        )
    }

    fun rejectDealer(userId: String) {
        _allUsers.value = _allUsers.value.map { u ->
            if (u.uid == userId) {
                u.copy(dealerStatus = DealerStatus.REJECTED)
            } else u
        }
        if (_currentUser.value?.uid == userId) {
            _currentUser.value = _currentUser.value?.copy(dealerStatus = DealerStatus.REJECTED)
        }
    }

    // CART METHODS
    fun addToCart(product: Product, quantity: Int = 1) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.product.id == product.id }
        if (index >= 0) {
            val existing = current[index]
            current[index] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            current.add(CartItem(product, quantity))
        }
        _cartItems.value = current
    }

    fun updateCartQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }
        _cartItems.value = _cartItems.value.map { item ->
            if (item.product.id == productId) item.copy(quantity = quantity) else item
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    // CHECKOUT & ORDERS
    fun placeOrder(
        address: String,
        latitude: Double? = null,
        longitude: Double? = null,
        paymentMethod: String = "Razorpay Test Mode",
        appliedDiscount: Double = 0.0
    ): Order? {
        val user = _currentUser.value ?: return null
        val items = _cartItems.value
        if (items.isEmpty()) return null

        val isDealer = user.role == UserRole.DEALER
        val subtotal = items.sumOf { item ->
            val price = if (isDealer) item.product.dealerWholesalePrice else item.product.retailPrice
            price * item.quantity
        }
        val total = (subtotal - appliedDiscount).coerceAtLeast(0.0)

        val newOrder = Order(
            orderId = "ORD_" + (10000..99999).random(),
            userId = user.uid,
            userName = user.name,
            userPhone = user.phone,
            userRole = user.role,
            items = items,
            totalAmount = total,
            discountAmount = appliedDiscount,
            isDealerOrder = isDealer,
            paymentMethod = paymentMethod,
            paymentId = "pay_rzp_" + UUID.randomUUID().toString().take(10),
            address = address,
            latitude = latitude,
            longitude = longitude,
            status = "ORDER_PLACED",
            createdAt = System.currentTimeMillis()
        )

        _orders.value = listOf(newOrder) + _orders.value
        clearCart()

        addNotification(
            title = "Order Placed Successfully! 🎉",
            message = "Order #${newOrder.orderId} of ₹${total.toInt()} placed. Tracking enabled.",
            type = "ORDER"
        )

        return newOrder
    }

    fun updateOrderStatus(orderId: String, newStatus: String) {
        _orders.value = _orders.value.map { order ->
            if (order.orderId == orderId) order.copy(status = newStatus) else order
        }
        addNotification(
            title = "Order Status Updated",
            message = "Order #$orderId is now $newStatus.",
            type = "ORDER"
        )
    }

    // SERVICE BOOKINGS
    fun createServiceBooking(
        purifierModel: String,
        serviceType: String,
        issueDescription: String,
        preferredDate: String,
        preferredTimeSlot: String,
        address: String
    ): ServiceBooking? {
        val user = _currentUser.value ?: return null
        val booking = ServiceBooking(
            bookingId = "SRV_" + (1000..9999).random(),
            userId = user.uid,
            userName = user.name,
            userPhone = user.phone,
            purifierModel = purifierModel,
            serviceType = serviceType,
            issueDescription = issueDescription,
            preferredDate = preferredDate,
            preferredTimeSlot = preferredTimeSlot,
            address = address,
            status = "PENDING"
        )

        _serviceBookings.value = listOf(booking) + _serviceBookings.value

        addNotification(
            title = "Service Booking Request Received",
            message = "Service #${booking.bookingId} for $purifierModel booked for $preferredDate.",
            type = "SERVICE"
        )

        return booking
    }

    fun updateServiceStatus(bookingId: String, status: String, technician: String? = null) {
        _serviceBookings.value = _serviceBookings.value.map { b ->
            if (b.bookingId == bookingId) {
                b.copy(status = status, assignedTechnician = technician ?: b.assignedTechnician)
            } else b
        }
        addNotification(
            title = "Service Request Updated",
            message = "Service #$bookingId status updated to $status.",
            type = "SERVICE"
        )
    }

    // NOTIFICATIONS
    fun addNotification(title: String, message: String, type: String = "GENERAL") {
        val notif = AppNotification(
            id = "notif_" + System.currentTimeMillis(),
            title = title,
            message = message,
            timestamp = System.currentTimeMillis(),
            type = type
        )
        _notifications.value = listOf(notif) + _notifications.value
    }

    fun markNotificationsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }
}
