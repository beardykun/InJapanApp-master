package com.example.user.injapanapp.ui.generalActivity

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.user.injapanapp.R
import kotlinx.android.synthetic.main.activity_general_without_appbar.*


abstract class GeneralActivityWithoutAppbar : GeneralActivity() {

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
            layoutInflater.inflate(R.layout.activity_general_without_appbar, window.decorView as ViewGroup, false)
        layoutInflater.inflate(layoutResID, container.findViewById<View>(R.id.cont_root) as ViewGroup, true)
        super.setContentView(container)

        progressLay.visibility = View.GONE

    }

    override fun showProgressView() {
        progressLay.visibility = View.VISIBLE
    }

    override fun hideProgressView() {
        progressLay.visibility = View.GONE
    }

    override fun showErrorSnack(error: String, coordinatorLayout: CoordinatorLayout) {
        val snackbar = Snackbar
            .make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.app_error_dismiss) { }
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.black))

        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        textView.maxLines = 10
        snackbar.show()
    }

    override fun showErrorSnack(error: String) {
        showErrorSnack(error, coordinatorLayout)
    }
}
