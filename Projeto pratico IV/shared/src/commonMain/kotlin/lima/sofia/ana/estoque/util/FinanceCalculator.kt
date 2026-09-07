package com.sofia.lima.estoque.util

import com.sofia.lima.estoque.model.Product

object FinanceCalculator {

    data class ProfitDivision(
        val totalSellingPrice: Double,
        val totalProfit: Double,
        val ownerShare: Double,
        val sellerShare: Double
    )

    // Calcula o comissionamento de 50/50 baseado no lucro líquido da venda
    fun calculateSaleProfit(product: Product, quantitySold: Int): ProfitDivision {
        val totalCost = product.costPrice * quantitySold
        val totalRevenue = product.sellingPrice * quantitySold
        val totalProfit = totalRevenue - totalCost

        // Divisão de 50% para cada
        val halfProfit = totalProfit / 2.0

        return ProfitDivision(
            totalSellingPrice = totalRevenue,
            totalProfit = totalProfit,
            ownerShare = halfProfit,
            sellerShare = halfProfit
        )
    }
}