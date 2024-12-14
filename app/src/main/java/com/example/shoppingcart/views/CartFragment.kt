package com.example.shoppingcart.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.shoppingcart.R
import com.example.shoppingcart.adapters.CartListAdapter
import com.example.shoppingcart.databinding.FragmentCartBinding
import com.example.shoppingcart.models.CartItem
import com.example.shoppingcart.viewmodels.ShopViewModel

class CartFragment : Fragment(), CartListAdapter.CartInterface {

    companion object {
        private const val TAG = "CartFragment"
    }

    private lateinit var shopViewModel: ShopViewModel
    private var fragmentCartBinding: FragmentCartBinding? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using data binding
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)
        return fragmentCartBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        // Initialize ViewModel
        shopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]

        // Initialize RecyclerView Adapter
        val cartListAdapter = CartListAdapter(this)
        fragmentCartBinding?.cartRecyclerView?.apply {
            adapter = cartListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        // Observe cart items and update RecyclerView
        shopViewModel.getCart().observe(viewLifecycleOwner) { cartItems ->
            cartListAdapter.submitList(cartItems)
            fragmentCartBinding?.placeOrderButton?.isEnabled = cartItems.isNotEmpty()
        }

        // Observe total price and update the UI
        shopViewModel.getTotalPrice().observe(viewLifecycleOwner) { totalPrice ->
            fragmentCartBinding?.orderTotalTextView?.text = "Total: $ $totalPrice"
        }

        // Handle place order button click
        fragmentCartBinding?.placeOrderButton?.setOnClickListener {
            navController.navigate(R.id.action_cartFragment_to_orderFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentCartBinding = null
    }

    override fun deleteItem(cartItem: CartItem) {
        // Remove item from cart via ViewModel
        shopViewModel.removeItemFromCart(cartItem)
    }

    override fun changeQuantity(cartItem: CartItem, quantity: Int) {
        // Update quantity via ViewModel
        shopViewModel.changeQuantity(cartItem, quantity)
    }
}
