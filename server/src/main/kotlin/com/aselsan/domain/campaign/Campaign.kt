package com.aselsan.com.aselsan.domain.campaign

import com.aselsan.com.aselsan.domain.shoppingCart.ShoppingCart

interface Campaign {
    fun isApplicable(shoppingCart: ShoppingCart): Boolean
    fun calculateDiscount(shoppingCart: ShoppingCart): Double
    val name: String
}
