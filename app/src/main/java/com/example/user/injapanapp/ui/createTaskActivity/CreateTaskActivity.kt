package com.example.user.injapanapp.ui.createTaskActivity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.widget.Toast
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.ui.generalActivity.GeneralActivityWithAppBar
import com.example.user.injapanapp.ui.mainActivity.MainActivity
import kotlinx.android.synthetic.main.activity_create_task.*
import org.jetbrains.anko.selector

class CreateTaskActivity : GeneralActivityWithAppBar(), ICreateTaskView {

    private var presenter: ICreateTaskPresenter? = null
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_STORAGE_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        presenter = CreateTaskPresenter()
        setClickListeners()
    }

    override fun onStart() {
        super.onStart()
        presenter?.onAttachView(this)
        createTaskBtn.setOnClickListener {
            presenter?.validateAndInsert(
                createTaskRorSTV.text.toString()
                        + createTaskNumberET.text.toString(), createTaskTypeTV.text.toString(),
                createTaskPriceTV.text.toString(), createTaskShelfET.text.toString(),
                createTaskDescriptionET.text.toString()
            )
        }
    }

    override fun onRestart() {
        super.onRestart()
        SharedPreferencesClass.saveBooleanInPreferences(Constants.TAKING_PICKS, false)
    }

    override fun onStop() {
        if (!SharedPreferencesClass.getBooleanFromPreferences(Constants.TAKING_PICKS)) {
            presenter?.onDetachView()
        }
        super.onStop()
    }

    override fun showProgress() {
        showProgressView()
    }

    override fun hideProgress() {
        hideProgressView()
    }

    override fun showError(error: String, vararg code: Int) {
        showErrorSnack(error)
    }

    override fun showError(error: Int, vararg code: Int) {
        showErrorSnack(error.toString())
    }

    private fun setClickListeners() {
        createTaskSavePickFAB.setOnClickListener {
            presenter?.saveImage()
            createTaskSavePickFAB.hide()
        }
        createTaskTakePickFAB.setOnClickListener {
            checkPermissions()
        }
        createTaskRorSTV.setOnClickListener { getSelector(createTaskRorSTV, listOf("R-", "S-")) }
        createTaskTypeTV.setOnClickListener {
            getSelector(
                createTaskTypeTV,
                resources.getStringArray(R.array.tasks).toList()
            )
        }
        createTaskPriceTV.setOnClickListener {
            getSelector(
                createTaskPriceTV,
                resources.getStringArray(R.array.prices).toList()
            )
        }
    }

    private fun getSelector(textView: TextView, choices: List<CharSequence>) {

        selector("Choose value", choices) { _, i ->
            textView.text = choices[i]
        }
    }

    override fun finishActivity() {
        startActivity(MainActivity::class.java)
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSION
            )
        } else {
            presenter?.launchCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter?.launchCamera()
                } else {
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun startPictureActivity(intent: Intent) {
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            presenter?.processAndSetImage(createTaskBitmapIV)
        } else {
            presenter?.deleteImageFile()
        }
    }

    override fun showSaveFAB() {
        createTaskSavePickFAB.show()
    }

    override fun showReplaceDialog() {
        Utils.getAlert(this, getString(R.string.replace_task), fun(){presenter?.replaceTask()})
    }
}
