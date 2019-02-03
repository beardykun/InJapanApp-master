package com.example.user.injapanapp.ui.settings

import android.os.Bundle
import com.example.user.injapanapp.R
import com.example.user.injapanapp.ui.general_activity.GeneralActivityWithAppBar

class SettingsActivity : GeneralActivityWithAppBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
