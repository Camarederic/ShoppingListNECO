package com.example.shoppinglistneco.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ActivityMainBinding
import com.example.shoppinglistneco.dialogs.NewListDialog
import com.example.shoppinglistneco.fragments.FragmentManager
import com.example.shoppinglistneco.fragments.NoteFragment
import com.example.shoppinglistneco.fragments.ShopListNamesFragment
import com.example.shoppinglistneco.settings.SettingsActivity

class MainActivity : AppCompatActivity(), NewListDialog.Listener {

    private lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shop_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)

        setBottomNavListener()
    }

    // метод для нажатия на 4 элемента
    private fun setBottomNavListener() {
        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.settings -> {
                    Log.d("MyLog", "Settings")
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                R.id.notes -> {
                    currentMenuItemId = R.id.notes
                    Log.d("MyLog", "Notes")
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {
                    currentMenuItemId = R.id.shop_list
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.new_item -> {
                    Log.d("MyLog", "New")
                    FragmentManager.currentFragment?.onClickNew()

                }
            }

            true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.selectedItemId = currentMenuItemId
    }

    override fun onClick(name: String) {
        Log.d("MyLog", "Name: $name")
    }
}