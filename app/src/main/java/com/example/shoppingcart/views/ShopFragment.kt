package com.example.shoppingcart.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.shoppingcart.R
import com.example.shoppingcart.adapters.ShopListAdapter
import com.example.shoppingcart.databinding.FragmentShopBinding
import com.example.shoppingcart.models.Product
import com.example.shoppingcart.viewmodels.ShopViewModel
import com.google.android.material.snackbar.Snackbar

class ShopFragment : Fragment(), ShopListAdapter.ShopInterface {

    private lateinit var fragmentShopBinding: FragmentShopBinding
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentShopBinding = FragmentShopBinding.inflate(inflater, container, false)
        return fragmentShopBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shopListAdapter = ShopListAdapter(this)
        fragmentShopBinding.shopRecyclerView.apply {
            adapter = shopListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        }

        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)
        shopViewModel.getProducts().observe(viewLifecycleOwner, Observer { products ->
            shopListAdapter.submitList(products)
        })

        navController = Navigation.findNavController(view)

        // Category Spinner setup
        val categories = listOf("All", "Mobiles", "Appliances", "Groceries", "Household")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        fragmentShopBinding.categorySpinner.adapter = spinnerAdapter

        fragmentShopBinding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                if (selectedCategory == "All") {
                    shopViewModel.getProducts().observe(viewLifecycleOwner, Observer { products ->
                        shopListAdapter.submitList(products)
                    })
                } else {
                    val filteredProducts = shopViewModel.getProducts().value?.filter { it.category == selectedCategory }
                    shopListAdapter.submitList(filteredProducts)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    override fun addItem(product: Product) {
        val isAdded = shopViewModel.addItemToCart(product)
        if (isAdded) {
            Snackbar.make(requireView(), "${product.name} added to cart.", Snackbar.LENGTH_LONG)
                .setAction("Checkout") {
                    navController.navigate(R.id.action_shopFragment_to_cartFragment)
                }
                .show()
        } else {
            Snackbar.make(requireView(), "Already have the max quantity in cart.", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    override fun onItemClick(product: Product) {
        shopViewModel.setProduct(product)
        navController.navigate(R.id.action_shopFragment_to_productDetailFragment)
    }
}
