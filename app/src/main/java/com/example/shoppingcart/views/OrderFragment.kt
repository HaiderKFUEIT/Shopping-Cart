package com.example.shoppingcart.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.FragmentOrderBinding
import com.example.shoppingcart.viewmodels.ShopViewModel

class OrderFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var fragmentOrderBinding: FragmentOrderBinding
    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false)
        return fragmentOrderBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)

        fragmentOrderBinding.continueShoppingButton.setOnClickListener {
            shopViewModel.resetCart()
            navController.navigate(R.id.action_orderFragment_to_shopFragment)
        }
    }
}
