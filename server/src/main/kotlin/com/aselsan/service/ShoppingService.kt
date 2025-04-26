package com.aselsan.com.aselsan.service

import com.aselsan.com.aselsan.domain.campaign.PercentageDiscountCampaign
import com.aselsan.com.aselsan.repository.ShoppingRepository
import com.aselsan.domain.model.CampaignRequest
import com.aselsan.domain.product.ProductCategoryType

class ShoppingService(private val shoppingRepository: ShoppingRepository) {

    fun applyDiscount(request: CampaignRequest): String {

        val shoppingCart = shoppingRepository.getShoppingCart(request)
        val campaign = PercentageDiscountCampaign(
            eligibleCategories = mapOf(
                ProductCategoryType.ELECTRONICS to 0.1,
                ProductCategoryType.CLOTHING to 0.15
            ),
            minCartValue = 2500.0
        )


        return if (campaign.isApplicable(shoppingCart)) {

            val discountAmount = campaign.calculateDiscount(shoppingCart)
            "Discount applied: $discountAmount"
        } else {
            "Campaign not applicable"
        }

    }

}