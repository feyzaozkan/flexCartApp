package com.aselsan.com.aselsan.domain.campaign

import com.aselsan.com.aselsan.domain.shoppingCart.ShoppingCart
import com.aselsan.domain.product.ProductCategoryType

class FixedAmountDiscountCampaign(
    private val eligibleCategories: Set<ProductCategoryType>,
    private val minCartValue: Double,
    private val discountAmount: Double
) : Campaign {
    override val name = "Fixed Amount Discount"

    override fun isApplicable(shoppingCart: ShoppingCart): Boolean {
        val eligibleValue = shoppingCart.items
            .filter { it.product.category in eligibleCategories }
            .sumOf { it.product.price * it.quantity }
        return eligibleValue >= minCartValue
    }

    override fun calculateDiscount(shoppingCart: ShoppingCart): Double {
        return if (isApplicable(shoppingCart)) discountAmount else 0.0
    }
}
