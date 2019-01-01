package com.example.user.injapanapp.ui.createTaskActivity

import android.os.Bundle
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.ui.generalActivity.GeneralActivityWithAppBar
import com.example.user.injapanapp.ui.mainActivity.MainActivity
import kotlinx.android.synthetic.main.activity_create_task.*
import org.jetbrains.anko.selector

class CreateTaskActivity : GeneralActivityWithAppBar(), ICreateTaskView {

    private var presenter: ICreateTaskPresenter? = null
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_STORAGE_PERMISSION = 1

    private val FILE_PROVIDER_AUTHORITY = "com.example.user.fileprovider"

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

    override fun onStop() {
        presenter?.onDetachView()
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
}
