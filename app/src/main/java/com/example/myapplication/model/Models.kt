package com.example.myapplication.model

enum class UserRole {
    CUSTOMER,
    DEALER,
    ADMIN
}

enum class DealerStatus {
    NONE,
    PENDING,
    APPROVED,
    REJECTED
}

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: UserRole = UserRole.CUSTOMER,
    val dealerStatus: DealerStatus = DealerStatus.NONE,
    val companyName: String? = null,
    val gstNumber: String? = null,
    val referralCode: String = "",
    val referredBy: String? = null,
    val rewardPoints: Int = 100
)

data class Product(
    val id: String,
    val name: String,
    val category: String, // Purifiers, Dispensers, Pumps, Filters, Spares
    val description: String,
    val imageUrl: String,
    val mrp: Double,
    val retailPrice: Double,
    val dealerWholesalePrice: Double,
    val minDealerOrderQty: Int = 5,
    val rating: Float = 4.5f,
    val reviewCount: Int = 120,
    val specifications: Map<String, String> = emptyMap(),
    val stockQuantity: Int = 50,
    val isPopular: Boolean = false
)

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class Order(
    val orderId: String,
    val userId: String,
    val userName: String,
    val userPhone: String,
    val userRole: UserRole,
    val items: List<CartItem>,
    val totalAmount: Double,
    val discountAmount: Double = 0.0,
    val isDealerOrder: Boolean = false,
    val paymentMethod: String = "Razorpay Test",
    val paymentId: String = "pay_test_" + System.currentTimeMillis(),
    val address: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val status: String = "ORDER_PLACED", // ORDER_PLACED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    val createdAt: Long = System.currentTimeMillis()
)

data class ServiceBooking(
    val bookingId: String,
    val userId: String,
    val userName: String,
    val userPhone: String,
    val purifierModel: String,
    val serviceType: String, // Installation, Regular Servicing, Filter Replacement, RO Repair, Water Quality Check
    val issueDescription: String,
    val preferredDate: String,
    val preferredTimeSlot: String,
    val address: String,
    val status: String = "PENDING", // PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
    val assignedTechnician: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val type: String = "GENERAL" // ORDER, SERVICE, DEALER, PROMO
)
