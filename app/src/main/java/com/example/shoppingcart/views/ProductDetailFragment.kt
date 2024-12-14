package com.example.shoppingcart.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingcart.databinding.FragmentProductDetailBinding
import com.example.shoppingcart.viewmodels.ShopViewModel

class ProductDetailFragment : Fragment() {

    private lateinit var fragmentProductDetailBinding: FragmentProductDetailBinding
    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductDetailBinding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return fragmentProductDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)
        fragmentProductDetailBinding.shopViewModel = shopViewModel
    }
}
