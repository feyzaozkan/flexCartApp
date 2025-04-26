package com.aselsan.com.aselsan.domain.campaign

import com.aselsan.com.aselsan.domain.shoppingCart.ShoppingCart
import com.aselsan.domain.customer.Customer

class FirstPurchaseOfferCampaign : Campaign {
    override val name = "First Purchase Campaign"

    override fun isApplicable(shoppingCart: ShoppingCart): Boolean {
        return shoppingCart.customer.totalSpent == 0.0
    }

    override fun calculateDiscount(shoppingCart: ShoppingCart): Double {
        return shoppingCart.total * 0.25
    }

}
