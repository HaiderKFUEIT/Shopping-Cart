package com.example.shoppingcart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.databinding.ShopRowBinding
import com.example.shoppingcart.models.Product

class ShopListAdapter(private val shopInterface: ShopInterface) :
    ListAdapter<Product, ShopListAdapter.ShopViewHolder>(Product.itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val shopRowBinding = ShopRowBinding.inflate(layoutInflater, parent, false)
        shopRowBinding.shopInterface = shopInterface
        return ShopViewHolder(shopRowBinding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val product = getItem(position)
        holder.shopRowBinding.product = product
        holder.shopRowBinding.executePendingBindings()
    }

    inner class ShopViewHolder(val shopRowBinding: ShopRowBinding) :
        RecyclerView.ViewHolder(shopRowBinding.root)

    interface ShopInterface {
        fun addItem(product: Product)
        fun onItemClick(product: Product)
    }
}
