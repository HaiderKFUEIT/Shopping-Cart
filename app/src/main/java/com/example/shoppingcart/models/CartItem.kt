package com.example.shoppingcart.models

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil

data class CartItem(
    var product: Product,
    var quantity: Int
) {
    override fun toString(): String {
        return "CartItem(product=$product, quantity=$quantity)"
    }

    companion object {
        @JvmStatic
        @BindingAdapter("setVal")
        fun setSelectedSpinnerValue(spinner: Spinner, quantity: Int?) {
            quantity?.let {
                // Ensure the value is valid for spinner's selection range
                if (it > 0 && it <= spinner.adapter.count) {
                    spinner.setSelection(it - 1, true)
                }
            }
        }


        val itemCallback = object : DiffUtil.ItemCallback<CartItem>() {
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem.quantity == newItem.quantity
            }

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
