package com.aselsan.com.aselsan.domain.campaign

import com.aselsan.com.aselsan.domain.shoppingCart.ShoppingCart

class BundleMultiBuyCampaign : Campaign {
    override val name = "Bundle-Multi Buy Campaign"

    override fun isApplicable(shoppingCart: ShoppingCart): Boolean {
        return shoppingCart.items.sumOf { it.quantity } >= 2

    }

    override fun calculateDiscount(shoppingCart: ShoppingCart): Double {
        if (!isApplicable(shoppingCart)) return 0.0

        val totalQuantity = shoppingCart.items.sumOf { it.quantity }

        return when {
            totalQuantity == 2 -> {

                shoppingCart.items
                    .flatMap { cartItem -> List(cartItem.quantity) { cartItem.product.price } }
                    .minOrNull() ?: 0.0
            }
            totalQuantity >= 3 -> {
                shoppingCart.total * 0.2
            }
            else -> 0.0
        }
    }



}