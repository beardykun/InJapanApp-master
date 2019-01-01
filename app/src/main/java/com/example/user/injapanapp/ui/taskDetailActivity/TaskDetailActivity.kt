package com.example.user.injapanapp.ui.taskDetailActivity

import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.GeneralActivityWithAppBar
import kotlinx.android.synthetic.main.activity_task_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.okButton

class TaskDetailActivity : GeneralActivityWithAppBar(), ITaskDetailView, TextView.OnEditorActionListener {

    private var presenter: ITaskDetailPresenter? = null
    private var taskObject: TaskObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        presenter = TaskDetailPresenter()
    }

    override fun onStart() {
        super.onStart()
        setOnClicks()
        presenter?.onAttachView(this)
        presenter?.getTaskDataFromDb(
            SharedPreferencesClass
                .getStringFromPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS)
        )

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

    override fun loadTaskInfo() {
        presenter?.getTaskDataFromDb(
            SharedPreferencesClass.getStringFromPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS)
        )
    }

    override fun setTaskData(taskObject: TaskObject) {
        this.taskObject = taskObject
        detailTaskNumberTV.text = taskObject.taskNumber
        detailTaskTypeTV.text = taskObject.taskType
        if (taskObject.taskGotMoney == "1") {
            detailTaskPriceTV.setTextColor(resources.getColor(R.color.colorAccent))
        } else {
            detailTaskPriceTV.setTextColor(resources.getColor(R.color.black))
        }
        if (taskObject.taskDone == "1"){
            detailTaskTypeTV.setTextColor(resources.getColor(R.color.colorAccent))
        }else{
            detailTaskTypeTV.setTextColor(resources.getColor(R.color.black))
        }
        detailTaskPriceTV.text = taskObject.taskPrice
        detailTaskShelfTV.text = taskObject.taskShelfNumber
        detailTaskEndDateTV.text = Utils.getTimeToEnd(taskObject.taskStartTime!!.toLong())
        detailTaskDescriptionTV.setText(taskObject.taskDescription)
    }

    private fun setOnClicks() {
        detailTaskPriceTV.setOnClickListener {
            alert("Payment received?") {
                okButton { presenter?.updatePaymentStatus(taskObject!!) }
                noButton { }
            }.show()
        }
        detailFinishedBtn.setOnClickListener {
            alert("Close the Task?") {
                okButton { presenter?.updateFinishedStatus(taskObject!!) }
                noButton { }
            }.show()
        }
        detailTaskTypeTV.setOnClickListener {
            alert("Task Accomplished?") {
                okButton { presenter?.updateDoneStatus(taskObject!!) }
                noButton { }
            }.show()
        }
        detailTaskDescriptionTV.setOnEditorActionListener(this)
        detailWhatToDoTV.setOnClickListener {
            alert("Edit description?") {
                okButton {
                    editText(true)
                }
                noButton { }
            }.show()
        }
    }

    override fun onEditorAction(textView: TextView, p1: Int, keyEvent: KeyEvent?): Boolean {
        if (p1 == EditorInfo.IME_ACTION_DONE) {
            taskObject?.taskDescription = textView.text.toString()
            presenter?.addDescription(taskObject!!)
            editText(false)
            return true
        }
        return false
    }

    override fun finishActivity() {
        finish()
    }

    private fun editText(boolean: Boolean) {
        if (boolean) {
            detailTaskDescriptionTV.isFocusableInTouchMode = true
            detailTaskDescriptionTV.isFocusable = true
            detailTaskDescriptionTV.isEnabled = true
            detailTaskDescriptionTV.inputType = InputType.TYPE_CLASS_TEXT
        } else {
            detailTaskDescriptionTV.isFocusable = false
            detailTaskDescriptionTV.isEnabled = false
            detailTaskDescriptionTV.inputType = InputType.TYPE_NULL
        }
    }
}
