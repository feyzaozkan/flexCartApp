package com.aselsan.com.aselsan.repository

import com.aselsan.com.aselsan.domain.cartItem.CartItem
import com.aselsan.com.aselsan.domain.shoppingCart.ShoppingCart
import com.aselsan.domain.model.CampaignRequest
import com.aselsan.repository.CustomerRepository
import com.aselsan.repository.ProductRepository

class ShoppingRepository {

    fun getShoppingCart(request: CampaignRequest): ShoppingCart {

        val customer  = CustomerRepository.customerById(request.customerId)

        val cartItems: List<CartItem> = request.items.map { item ->
            val product = ProductRepository.productById(item.productId)
            CartItem(product!!, item.quantity)
        }

        val shoppingCart = ShoppingCart(customer!!, cartItems)


        return shoppingCart
    }

}