package com.example.shoppinglistneco.fragments

import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglistneco.R

object FragmentManager {

    var currentFragment: BaseFragment? = null

    fun setFragment(newFragment: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, newFragment)
        transaction.commit()
        currentFragment = newFragment
    }
}