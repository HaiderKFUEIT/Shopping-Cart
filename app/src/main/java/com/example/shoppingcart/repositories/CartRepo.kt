package com.example.shoppingcart.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppingcart.models.CartItem
import com.example.shoppingcart.models.Product

class CartRepo {

    private val mutableCart = MutableLiveData<MutableList<CartItem>>()
    private val mutableTotalPrice = MutableLiveData<Double>()

    fun getCart(): LiveData<MutableList<CartItem>> {
        if (mutableCart.value == null) {
            initCart()
        }
        return mutableCart
    }

    fun initCart() {
        mutableCart.value = mutableListOf()
        calculateCartTotal()
    }

    fun addItemToCart(product: Product): Boolean {
        if (mutableCart.value == null) {
            initCart()
        }
        val cartItemList = mutableCart.value ?: mutableListOf()
        for (cartItem in cartItemList) {
            if (cartItem.product.id == product.id) {
                if (cartItem.quantity == 5) {
                    return false
                }

                val index = cartItemList.indexOf(cartItem)
                cartItem.quantity += 1
                cartItemList[index] = cartItem

                mutableCart.value = cartItemList
                calculateCartTotal()
                return true
            }
        }
        val cartItem = CartItem(product, 1)
        cartItemList.add(cartItem)
        mutableCart.value = cartItemList
        calculateCartTotal()
        return true
    }

    fun removeItemFromCart(cartItem: CartItem) {
        if (mutableCart.value == null) return

        val cartItemList = mutableCart.value ?: return
        cartItemList.remove(cartItem)
        mutableCart.value = cartItemList
        calculateCartTotal()
    }

    fun changeQuantity(cartItem: CartItem, quantity: Int) {
        if (mutableCart.value == null) return

        val cartItemList = mutableCart.value ?: return
        val updatedItem = CartItem(cartItem.product, quantity)
        val index = cartItemList.indexOf(cartItem)
        if (index != -1) {
            cartItemList[index] = updatedItem
            mutableCart.value = cartItemList
            calculateCartTotal()
        }
    }

    private fun calculateCartTotal() {
        if (mutableCart.value == null) return

        val cartItemList = mutableCart.value ?: return
        val total = cartItemList.sumOf { it.product.price * it.quantity }
        mutableTotalPrice.value = total
    }

    fun getTotalPrice(): LiveData<Double> {
        if (mutableTotalPrice.value == null) {
            mutableTotalPrice.value = 0.0
        }
        return mutableTotalPrice
    }
}
