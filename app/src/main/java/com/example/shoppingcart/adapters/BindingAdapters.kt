package com.example.shoppingcart.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter

// Define the custom BindingAdapter
@BindingAdapter("productImage")
fun setProductImage(imageView: ImageView, imageResource: Int?) {
    // Set the image resource if it's not null
    imageResource?.let {
        imageView.setImageResource(it)
    }
}
