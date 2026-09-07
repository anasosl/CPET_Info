package com.sofia.lima.estoque.model

enum class Brand {
    NATURA, AVON, OBOTICARIO
}

data class Product(
    val id: String,
    val name: String,
    val brand: Brand,
    val costPrice: Double,
    val stockQuantity: Int
) {
    val sellingPrice: Double
        get() = costPrice * 1.30 // Margem automática de 30%
}

data class Buyer(
    val id: String,
    val name: String,
    val email: String,
    val phone: String
)

data class PurchaseEntry(
    val id: String,
    val product: Product,
    val quantity: Int,
    val costPriceAtPurchase: Double,
    val date: String
)

data class SellEntry(
    val id: String,
    val product: Product,
    val buyer: Buyer?, // Consultor/Vendedor intermediário (se houver)
    val quantity: Int,
    val finalPrice: Double,
    val date: String
)