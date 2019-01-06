package com.example.user.injapanapp.ui.taskDetailActivity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.createTaskActivity.BitmapUtils
import com.example.user.injapanapp.ui.generalActivity.GeneralActivityWithAppBar
import com.example.user.injapanapp.ui.pictureViewActivity.PictureViewActivity
import kotlinx.android.synthetic.main.activity_task_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.image
import org.jetbrains.anko.noButton
import org.jetbrains.anko.okButton

class TaskDetailActivity : GeneralActivityWithAppBar(), ITaskDetailView, TextView.OnEditorActionListener {

    private var presenter: ITaskDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        presenter = TaskDetailPresenter()
    }

    override fun onStart() {
        super.onStart()
        setOnClicks()
        presenter?.onAttachView(this)
        presenter?.getTaskDataFromDb()
    }

    override fun onResume() {
        super.onResume()
        if (SharedPreferencesClass.getBooleanFromPreferences(Constants.TIMER_IS_WORKING)) {
            detailStartTimerFAB.image = ContextCompat.getDrawable(this, R.drawable.ic_timer_off_black_24dp)
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

    override fun loadTaskInfo() {
        presenter?.getTaskDataFromDb()
    }

    override fun setTaskData(taskObject: TaskObject) {
        detailTaskNumberTV.text = taskObject.taskNumber
        detailTaskTypeTV.text = taskObject.taskType
        if (taskObject.taskGotMoney == "1") {
            detailTaskPriceTV.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        } else {
            detailTaskPriceTV.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
        if (taskObject.taskDone == "1") {
            detailTaskTypeTV.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        } else {
            detailTaskTypeTV.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
        detailTaskPriceTV.text = taskObject.taskPrice
        detailTaskShelfTV.text = taskObject.taskShelfNumber
        detailTaskEndDateTV.text = Utils.getTimeToEnd(taskObject.taskStartTime!!.toLong())
        detailTaskDescriptionTV.setText(taskObject.taskDescription)

        if (!taskObject.taskPhoto!!.isEmpty()) {
            val bitmap = BitmapUtils.resamplePic(this.application, taskObject.taskPhoto!!)
            detailPhotoIV.setImageBitmap(bitmap)
        }
        if (taskObject.taskTimePassed != "0") {
            setTime(taskObject.taskTimePassed!!)
        }
    }

    private fun setOnClicks() {
        detailStartTimerFAB.setOnClickListener {
            alert(getString(R.string.start_stop_timer)) {
                okButton { presenter?.startOrStopTimer(detailStartTimerFAB) }
                noButton { }
            }.show()
        }
        detailPhotoIV.setOnClickListener { startActivity(PictureViewActivity::class.java) }
        detailTaskPriceTV.setOnClickListener {
            alert(getString(R.string.payment_received)) {
                okButton { presenter?.updatePaymentStatus() }
                noButton { }
            }.show()
        }
        detailFinishedBtn.setOnClickListener {
            alert(getString(R.string.close_the_task)) {
                okButton { presenter?.updateFinishedStatus() }
                noButton { }
            }.show()
        }
        detailTaskTypeTV.setOnClickListener {
            alert(getString(R.string.task_accomplished)) {
                okButton { presenter?.updateDoneStatus() }
                noButton { }
            }.show()
        }
        detailTaskDescriptionTV.setOnEditorActionListener(this)
        detailWhatToDoTV.setOnClickListener {
            alert(getString(R.string.edit_description)) {
                okButton {
                    presenter?.enableDisableEditDescription(detailTaskDescriptionTV, true)
                }
                noButton { }
            }.show()
        }
    }

    override fun onEditorAction(textView: TextView, p1: Int, keyEvent: KeyEvent?): Boolean {
        if (p1 == EditorInfo.IME_ACTION_DONE) {
            presenter?.addDescription(textView.text.toString())
            presenter?.enableDisableEditDescription(detailTaskDescriptionTV, false)
            return true
        }
        return false
    }

    override fun finishActivity() {
        finish()
    }

    override fun setTime(time: String) {
        detailTimerTimeTV.visibility = View.VISIBLE
        val text = getString(R.string.timer_prefix) + ": " + time
        detailTimerTimeTV.text = text
    }
}
