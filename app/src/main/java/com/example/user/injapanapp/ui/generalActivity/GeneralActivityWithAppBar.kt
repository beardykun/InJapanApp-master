package com.example.user.injapanapp.ui.generalActivity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.user.injapanapp.R
import kotlinx.android.synthetic.main.activity_general_with_appbar.*
import kotlinx.android.synthetic.main.toolbar.*

abstract class GeneralActivityWithAppBar : GeneralActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setContentView(layoutResID: Int) {
        val layoutInflater = layoutInflater

        val container =
            layoutInflater.inflate(R.layout.activity_general_with_appbar, window.decorView as ViewGroup, false)
        layoutInflater.inflate(layoutResID, container.findViewById(R.id.cont_root) as ViewGroup, true)
        super.setContentView(container)

        progressLay.visibility = View.GONE

        setSupportActionBar(toolbar)
        val bar = supportActionBar
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false)
            bar.setDisplayHomeAsUpEnabled(true)
            bar.setDisplayShowHomeEnabled(true)
        }
    }

    override fun showErrorSnack(error: String) {
        showErrorSnack(error, coordinatorLayout)
    }

    override fun showProgressView() {
        progressLay.visibility = View.INVISIBLE
    }

    override fun hideProgressView() {
        progressLay.visibility = View.INVISIBLE
    }
}
