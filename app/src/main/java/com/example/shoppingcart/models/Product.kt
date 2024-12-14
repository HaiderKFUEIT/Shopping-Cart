package com.example.shoppingcart.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide

data class Product(
    var id: String,
    var name: String,
    var price: Double,
    var isAvailable: Boolean,
    var imageResId: Int,
    var category: String // Added category
) {
    override fun toString(): String {
        return "Product(id='$id', name='$name', price=$price, isAvailable=$isAvailable, imageResId=$imageResId, category='$category')"
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:productImage")
        fun loadImage(imageView: ImageView, imageResId: Int) {
            imageView.setImageResource(imageResId)
        }

        val itemCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}
