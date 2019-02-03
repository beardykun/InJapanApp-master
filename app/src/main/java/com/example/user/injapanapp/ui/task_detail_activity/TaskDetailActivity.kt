package com.example.user.injapanapp.ui.task_detail_activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.ui.general_activity.GeneralActivityWithAppBar
import com.example.user.injapanapp.ui.picture_view_activity.PictureViewActivity
import kotlinx.android.synthetic.main.activity_task_detail.*

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

    override fun setTaskData() {
        presenter?.setTaskData(
            detailTaskNumberTV, detailTaskTypeTV, detailTaskPriceTV, detailTaskShelfTV,
            detailTaskEndDateTV, detailTaskDescriptionTV, detailPhotoIV, detailPriorityTV,
            detailStartTimerFAB, detailMainLayout
        )

    }

    private fun setOnClicks() {
        detailStartTimerFAB.setOnClickListener {
            Utils.getAlert(
                this,
                getString(R.string.start_stop_timer),
                fun() { presenter?.startOrStopTimer(detailStartTimerFAB) })
        }
        detailPhotoIV.setOnClickListener { startActivity(PictureViewActivity::class.java) }
        detailTaskPriceTV.setOnClickListener {
            Utils.getAlert(this, getString(R.string.payment_received), fun() { presenter?.updatePaymentStatus() })
        }
        detailFinishedBtn.setOnClickListener {
            Utils.getAlert(this, getString(R.string.close_the_task), fun() { presenter?.updateFinishedStatus() })
        }
        detailTaskTypeTV.setOnClickListener {
            Utils.getAlert(this, getString(R.string.task_accomplished), fun() { presenter?.updateDoneStatus() })
        }
        detailTaskDescriptionTV.setOnEditorActionListener(this)
        detailWhatToDoTV.setOnClickListener {
            Utils.getAlert(this, getString(R.string.edit_description),
                fun() { presenter?.enableDisableEditDescription(detailTaskDescriptionTV, true) })
        }
        detailPriorityTV.setOnClickListener {
            Utils.getSelector(this, it as TextView, resources.getStringArray(R.array.priority).toList())
            presenter?.updatePriority(detailPriorityTV.text.toString())
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
