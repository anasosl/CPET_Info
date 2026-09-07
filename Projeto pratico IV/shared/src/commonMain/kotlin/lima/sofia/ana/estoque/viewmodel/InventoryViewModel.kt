package com.sofia.lima.estoque.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.sofia.lima.estoque.model.*

class InventoryViewModel {
    val products = mutableStateListOf<Product>()
    val buyers = mutableStateListOf<Buyer>()
    val purchaseHistory = mutableStateListOf<PurchaseEntry>()
    val sellHistory = mutableStateListOf<SellEntry>()

    // Métricas Financeiras Consolidadas
    var totalSalesValue = mutableStateOf(0.0)
    var totalInvestedValue = mutableStateOf(0.0)
    var netProfit = mutableStateOf(0.0)
    var ownerProfitShare = mutableStateOf(0.0)
    var sellerCommissionShare = mutableStateOf(0.0)

    init {
        // Dados de Demonstração Iniciais (Mocks de marcas)
        buyers.add(Buyer("1", "Ana Sofia", "sofia@lima.com", "81 99999-9999"))
        buyers.add(Buyer("2", "Consultor Lucas", "lucas@vendas.com", "81 88888-8888"))

        addProductMock("Kaiak Desodorante", Brand.NATURA, 80.0, 15)
        addProductMock("Batom Power Stay", Brand.AVON, 35.0, 30)
        addProductMock("Malbec Perfume", Brand.OBOTICARIO, 140.0, 8)
    }

    private fun addProductMock(name: String, brand: Brand, costPrice: Double, quantity: Int) {
        val prod = Product((products.size + 1).toString(), name, brand, costPrice, quantity)
        products.add(prod)
        totalInvestedValue.value += costPrice * quantity
    }

    // FEATURE: Entrada de Compra (Purchase Entry) -> Abastece o Estoque
    fun registerPurchaseEntry(name: String, brand: Brand, costPrice: Double, quantity: Int, date: String) {
        val existingProduct = products.find { it.name.equals(name, ignoreCase = true) && it.brand == brand }

        val product = if (existingProduct != null) {
            val index = products.indexOf(existingProduct)
            val updated = existingProduct.copy(stockQuantity = existingProduct.stockQuantity + quantity, costPrice = costPrice)
            products[index] = updated
            updated
        } else {
            val newProd = Product((products.size + 1).toString(), name, brand, costPrice, quantity)
            products.add(newProd)
            newProd
        }

        purchaseHistory.add(PurchaseEntry((purchaseHistory.size + 1).toString(), product, quantity, costPrice, date))
        totalInvestedValue.value += costPrice * quantity
    }

    // FEATURE: Saída de Venda (Sell Entry) -> Deduz Estoque & Divide Lucros
    fun registerSellEntry(product: Product, buyer: Buyer?, quantity: Int, date: String) {
        if (product.stockQuantity >= quantity) {
            val index = products.indexOf(product)
            if (index != -1) {
                products[index] = product.copy(stockQuantity = product.stockQuantity - quantity)
            }

            val revenue = product.sellingPrice * quantity
            val costOfItems = product.costPrice * quantity
            val profitOfSale = revenue - costOfItems

            sellHistory.add(SellEntry((sellHistory.size + 1).toString(), product, buyer, quantity, revenue, date))

            // Atualização do Fluxo Financeiro das Regras de Vendas
            totalSalesValue.value += revenue
            netProfit.value += profitOfSale

            if (buyer != null) {
                // Divisão de comissão 50/50 se houver intermediário
                ownerProfitShare.value += profitOfSale / 2.0
                sellerCommissionShare.value += profitOfSale / 2.0
            } else {
                // Venda direta da proprietária
                ownerProfitShare.value += profitOfSale
            }
        }
    }

    // FEATURE: Cadastro de Novos Consultores
    fun registerBuyer(name: String, email: String, phone: String) {
        buyers.add(Buyer((buyers.size + 1).toString(), name, email, phone))
    }
}