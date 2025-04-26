package com.aselsan.com.aselsan.service
import com.aselsan.com.aselsan.domain.campaign.BundleMultiBuyCampaign
import com.aselsan.com.aselsan.domain.campaign.Campaign
import com.aselsan.com.aselsan.domain.campaign.DateBasedDiscountCampaign
import com.aselsan.com.aselsan.domain.campaign.FirstPurchaseOfferCampaign
import com.aselsan.com.aselsan.domain.campaign.FixedAmountDiscountCampaign
import com.aselsan.com.aselsan.domain.campaign.PercentageDiscountCampaign
import com.aselsan.com.aselsan.repository.ShoppingRepository
import com.aselsan.domain.customer.CustomerType
import com.aselsan.domain.model.CampaignDetails
import com.aselsan.domain.model.CampaignRequest
import com.aselsan.domain.product.ProductCategoryType
import com.aselsan.domain.model.CampaignResponse
import java.time.DayOfWeek

class ShoppingService(private val shoppingRepository: ShoppingRepository) {

    private val campaigns: List<Campaign> = listOf(
        PercentageDiscountCampaign(
            eligibleCategories = mapOf(
                ProductCategoryType.ELECTRONICS to 0.1,
                ProductCategoryType.CLOTHING to 0.15
            )
        ),
        FixedAmountDiscountCampaign(
            eligibleCategories = setOf(ProductCategoryType.ELECTRONICS, ProductCategoryType.CLOTHING),
            minCartValue = 500.0,
            discountAmount = 100.0
        ),
        DateBasedDiscountCampaign(
            promotions = mapOf(
                DayOfWeek.MONDAY to DateBasedDiscountCampaign.Promotion(ProductCategoryType.BOOKS, 0.1),
                DayOfWeek.TUESDAY to DateBasedDiscountCampaign.Promotion(ProductCategoryType.ELECTRONICS, 0.05),
                DayOfWeek.WEDNESDAY to DateBasedDiscountCampaign.Promotion(ProductCategoryType.TOYS, 0.1),
                DayOfWeek.THURSDAY to DateBasedDiscountCampaign.Promotion(ProductCategoryType.CLOTHING, 0.05),
            ),
            sampleDay = DayOfWeek.MONDAY
        ),
        FirstPurchaseOfferCampaign(),
        BundleMultiBuyCampaign()
    )


    fun applyDiscount(request: CampaignRequest): CampaignResponse {
        val shoppingCart = shoppingRepository.getShoppingCart(request)

        val campaignResults = campaigns.map { campaign ->
            if (campaign.isApplicable(shoppingCart)) {
                val discount = campaign.calculateDiscount(shoppingCart)
                CampaignDetails(campaign.name, discount)
            } else {
                CampaignDetails(campaign.name, 0.0)
            }
        }


        val sortedCampaigns = campaignResults.sortedByDescending { it.discount }

        val isFree : Boolean = ((shoppingCart.total - sortedCampaigns[0].discount) >= 1000.0) ||( shoppingCart.customer.type == CustomerType.PREMIUM)

        val shippingFee = if (isFree) 0.0 else 25.0

        return CampaignResponse(campaignDetails = sortedCampaigns, shippingFee = shippingFee)

    }

}