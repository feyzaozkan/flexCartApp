package com.aselsan.com.aselsan.domain.cartItem

import com.aselsan.domain.product.Product

class CartItem(
    val product: Product,
    val quantity: Int
) {
}