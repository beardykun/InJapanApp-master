package com.example.user.injapanapp.ui.settings

import android.os.Bundle
import com.example.user.injapanapp.R
import com.example.user.injapanapp.ui.general_activity.GeneralActivityWithAppBar
import com.example.user.injapanapp.ui.main_activity.MainActivity

class SettingsActivity : GeneralActivityWithAppBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(MainActivity::class.java)
    }
}
