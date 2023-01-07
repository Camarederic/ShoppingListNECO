package com.example.shoppinglistneco.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ActivityMainBinding
import com.example.shoppinglistneco.fragments.FragmentManager
import com.example.shoppinglistneco.fragments.NoteFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavListener()
    }

    // метод для нажатия на 4 элемента
    private fun setBottomNavListener() {
        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.settings -> {
                    Log.d("MyLog", "Settings")
                }
                R.id.notes -> {
                    Log.d("MyLog", "Notes")
                    FragmentManager.setFragment(NoteFragment.newInstance(),this)
                }
                R.id.shop_list -> {
                    Log.d("MyLog", "Shop list")
                }
                R.id.new_item -> {
                    Log.d("MyLog", "New")
                }
            }

            true
        }
    }
}