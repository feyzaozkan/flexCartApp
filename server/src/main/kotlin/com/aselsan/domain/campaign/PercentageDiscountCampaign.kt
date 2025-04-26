package com.aselsan.com.aselsan.domain.campaign

import com.aselsan.com.aselsan.domain.shoppingCart.ShoppingCart
import com.aselsan.domain.customer.CustomerType
import com.aselsan.domain.product.ProductCategoryType

class PercentageDiscountCampaign(
    private val eligibleCategories: Map<ProductCategoryType, Double>, // Category -> Discount Rate
    private val minCartValue: Double = 2500.0,
) : Campaign {

    override val name = "Percentage Discount"

    override fun isApplicable(shoppingCart: ShoppingCart): Boolean {
        return shoppingCart.total >= minCartValue && (shoppingCart.customer.type == CustomerType.PREMIUM)
    }

    override fun calculateDiscount(shoppingCart: ShoppingCart): Double {
        if (!isApplicable(shoppingCart)) return 0.0

        var discount = 0.0
        shoppingCart.items.forEach { item ->
            val discountRate = eligibleCategories[item.product.category] ?: 0.0
            discount += item.product.price * discountRate * item.quantity
        }
        return discount
    }
}