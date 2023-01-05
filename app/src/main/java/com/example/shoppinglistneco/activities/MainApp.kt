package com.example.shoppinglistneco.activities

import android.app.Application
import com.example.shoppinglistneco.database.MainDatabase

class MainApp : Application() {

    val database by lazy {
        MainDatabase.getDatabase(this)
    }
}