package com.aselsan.com.aselsan.domain.shoppingCart

import com.aselsan.com.aselsan.domain.cartItem.CartItem
import com.aselsan.domain.customer.Customer

class ShoppingCart(
    val customer: Customer,
    val items: List<CartItem>
) {
    val total: Double
        get() = items.sumOf { it.product.price * it.quantity }
}