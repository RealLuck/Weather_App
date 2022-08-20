package com.realluck.whether_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.realluck.wheater_app.R
import com.realluck.whether_app.fragments.MainFragment

const val API_KEY = "2bb95ee6c7f14c32b5f163052222008"
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen, MainFragment.newInstance())
            .commit()
    }
}
