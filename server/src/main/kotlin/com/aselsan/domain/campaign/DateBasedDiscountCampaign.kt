package com.aselsan.com.aselsan.domain.campaign

import com.aselsan.com.aselsan.domain.shoppingCart.ShoppingCart
import com.aselsan.domain.product.ProductCategoryType
import java.time.DayOfWeek
import java.time.LocalDate

class DateBasedDiscountCampaign(
    private val promotions: Map<DayOfWeek, Promotion>,
    private val sampleDay: DayOfWeek? = null
) : Campaign {

    data class Promotion(val category: ProductCategoryType, val discountRate: Double)

    override val name = "Date-Based Discounts"

    private fun todayPromotion(): Promotion? {
        val today = sampleDay ?: LocalDate.now().dayOfWeek
        return promotions[today]
    }

    override fun isApplicable(shoppingCart: ShoppingCart): Boolean {
        return todayPromotion() != null
    }

    override fun calculateDiscount(shoppingCart: ShoppingCart): Double {
        val promotion = todayPromotion() ?: return 0.0

        return shoppingCart.items
            .filter { it.product.category == promotion.category }
            .sumOf { it.product.price * promotion.discountRate * it.quantity }
    }
}
