package com.example.shoppingcart.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.shoppingcart.R
import com.example.shoppingcart.viewmodels.ShopViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var shopViewModel: ShopViewModel

    private var cartQuantity = 0 // Holds the total number of items in the cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up NavController and action bar navigation
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        // Initialize ViewModel
        shopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]

        // Observe cart changes to update the cart badge
        shopViewModel.getCart().observe(this) { cartItems ->
            cartQuantity = cartItems.sumOf { it.quantity }
            invalidateOptionsMenu() // Force recreation of the options menu to update the badge
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the main menu
        menuInflater.inflate(R.menu.main_menu, menu)

        // Find the cart menu item and set up its action view
        val menuItem = menu.findItem(R.id.cartFragment)
        val actionView = menuItem.actionView

        val cartBadgeTextView = actionView?.findViewById<TextView>(R.id.cart_badge_text_view)
        if (cartBadgeTextView != null) {
            cartBadgeTextView.text = cartQuantity.toString()
        }
        if (cartBadgeTextView != null) {
            cartBadgeTextView.visibility = if (cartQuantity == 0) View.GONE else View.VISIBLE
        }

        // Handle click on the cart icon
        if (actionView != null) {
            actionView.setOnClickListener {
                onOptionsItemSelected(menuItem)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.cartFragment) {
            // Navigate to the cart fragment when the cart menu item is clicked
            navController.navigate(R.id.cartFragment)
            true
        } else {
            NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
        }
    }
}
