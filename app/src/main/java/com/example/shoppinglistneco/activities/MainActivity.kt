package com.example.shoppinglistneco.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ActivityMainBinding
import com.example.shoppinglistneco.dialogs.NewListDialog
import com.example.shoppinglistneco.fragments.FragmentManager
import com.example.shoppinglistneco.fragments.NoteFragment
import com.example.shoppinglistneco.fragments.ShopListNamesFragment
import com.example.shoppinglistneco.settings.SettingsActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity(), NewListDialog.Listener {

    private lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shop_list
    private lateinit var defPref: SharedPreferences
    private var currentTheme = ""
    private var interAd: InterstitialAd? = null
    private var adShowCounter = 0
    private var adShowCounterMax = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme = defPref.getString("theme_key", "blue").toString()
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)

        setBottomNavListener()

        loadInterAd()
    }

    // функция для загрузки рекламы
    private fun loadInterAd() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            getString(R.string.inter_advertisement_id),
            request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interAd = ad
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interAd = null
                }
            })
    }

    // функция для показа рекламы пользователю
    private fun showInterAd(adListener: AdListener) {
        if (interAd != null && adShowCounter > adShowCounterMax) {
            interAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interAd = null
                    loadInterAd()
                    adListener.onFinish()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    interAd = null
                    loadInterAd()
                }

                override fun onAdShowedFullScreenContent() {
                    interAd = null
                    loadInterAd()
                }
            }
            adShowCounter = 0
            interAd?.show(this)

        } else {
            adShowCounter++
            adListener.onFinish()
        }
    }

    // метод для нажатия на 4 элемента
    private fun setBottomNavListener() {
        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.settings -> {
                    Log.d("MyLog", "Settings")
                    showInterAd(object : AdListener {

                        override fun onFinish() {
                            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                        }

                    })

                }
                R.id.notes -> {
                    showInterAd(object : AdListener {

                        override fun onFinish() {
                            currentMenuItemId = R.id.notes
                            Log.d("MyLog", "Notes")
                            FragmentManager.setFragment(NoteFragment.newInstance(), this@MainActivity)
                        }

                    })

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
        if (defPref.getString("theme_key", "blue") != currentTheme) recreate()
    }

    private fun getSelectedTheme(): Int {
        return if (defPref.getString("theme_key", "blue") == "blue") {
            R.style.Theme_ShoppingListBlueNECO
        } else {
            R.style.Theme_ShoppingListRedNECO
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLog", "Name: $name")
    }

    interface AdListener {
        fun onFinish()
    }
}