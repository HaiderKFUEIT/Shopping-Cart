package com.example.shoppingcart.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppingcart.models.Product
import com.example.shoppingcart.R
import java.util.UUID

class ShopRepo {

    private val mutableProductList: MutableLiveData<List<Product>> = MutableLiveData()

    init {
        loadProducts()
    }

    fun getProducts(): LiveData<List<Product>> {
        return mutableProductList
    }

    private fun loadProducts() {
        val productList = listOf(
            // Mobiles
            Product(
                UUID.randomUUID().toString(),
                "IPhone 14 Pro",
                1299.0,
                true,
                R.drawable.iphone14pro,
                "Mobiles" // Category
            ),
            Product(
                UUID.randomUUID().toString(),
                "Samsung S22",
                1199.0,
                false,
                R.drawable.samsungs22,
                "Mobiles"
            ),
            // Appliances
            Product(
                UUID.randomUUID().toString(),
                "Dishwasher",
                499.0,
                true,
                R.drawable.dishwasher,
                "Appliances" // Category
            ),
            Product(
                UUID.randomUUID().toString(),
                "Microwave",
                99.0,
                true,
                R.drawable.microwave,
                "Appliances"
            ),
            // Groceries
            Product(
                UUID.randomUUID().toString(),
                "Tomato",
                3.0,
                true,
                R.drawable.tomato,
                "Groceries" // Category
            ),
            Product(
                UUID.randomUUID().toString(),
                "Red Grapes",
                15.0,
                true,
                R.drawable.grapes,
                "Groceries"
            ),
            Product(
                UUID.randomUUID().toString(),
                "Rice",
                15.0,
                true,
                R.drawable.rice,
                "Groceries"
            ),

            Product(
                UUID.randomUUID().toString(),
                "Flour",
                15.0,
                true,
                R.drawable.flour,
                "Groceries"
            ),

            // Household
            Product(
                UUID.randomUUID().toString(),
                "Cleaning Spray",
                10.0,
                true,
                R.drawable.cleaning,
                "Household" // Category
            ),
            Product(
                UUID.randomUUID().toString(),
                "Toilet Paper",
                5.0,
                true,
                R.drawable.toiletpaper,
                "Household"
            )
        )
        mutableProductList.value = productList
    }
}
