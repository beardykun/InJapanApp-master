package com.example.user.injapanapp.ui.general_activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.ui.history_activity.HistoryActivity
import com.example.user.injapanapp.ui.main_activity.MainActivity
import kotlinx.android.synthetic.main.activity_general_with_menu.*
import kotlinx.android.synthetic.main.toolbar.*


abstract class GeneralActivityWithMenu : GeneralActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        val layoutInflater = layoutInflater

        val container =
            layoutInflater.inflate(R.layout.activity_general_with_menu, window.decorView as ViewGroup, false)
        layoutInflater.inflate(layoutResID, container.findViewById(R.id.cont_root) as ViewGroup, true)
        super.setContentView(container)

        progressLay.visibility = (View.GONE)

        setToolbar(toolbar)

        setDrawer(toolbar, drawer_layout, nav_view)
    }

    private fun setDrawer(toolbar: Toolbar, drawer: DrawerLayout, navigationView: NavigationView) {
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.app_navigation_drawer_open, R.string.app_navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val bar = supportActionBar
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false)
            bar.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> {
                startActivity(HistoryActivity::class.java)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun showProgressView() {
        progressLay.visibility = (View.VISIBLE)
    }

    override fun hideProgressView() {
        progressLay.visibility = (View.GONE)
    }

    override fun showErrorSnack(error: String) {
        showErrorSnack(error, coordinatorLayout)
    }

}
