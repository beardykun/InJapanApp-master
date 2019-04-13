package com.example.user.injapanapp.ui.task_detail_activity

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.ui.general_activity.GeneralActivityWithAppBar
import com.example.user.injapanapp.ui.main_activity.MainActivity
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
            detailTaskNumberTV, detailTaskTypeTV, detailTaskPriceTV, detailTaskShelfET,
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
        detailTaskShelfET.setOnEditorActionListener(this)
        detailTaskShelfET.imeOptions = EditorInfo.IME_ACTION_DONE
        detailTaskShelfET.setRawInputType(InputType.TYPE_CLASS_TEXT)
        detailTaskDescriptionTV.setOnEditorActionListener(this)
        detailTaskDescriptionTV.imeOptions = EditorInfo.IME_ACTION_DONE
        detailTaskDescriptionTV.setRawInputType(InputType.TYPE_CLASS_TEXT)
        detailPriorityTV.setOnClickListener {
            Utils.getSelector(this, it as TextView, resources.getStringArray(R.array.priority).toList())
        }
        detailTaskShelfIB.setOnClickListener {
            Utils.getSelector(
                this,
                detailTaskShelfET,
                resources.getStringArray(R.array.shelf).toList()
            )
        }
    }

    override fun onEditorAction(textView: TextView, p1: Int, keyEvent: KeyEvent?): Boolean {
        if (p1 == EditorInfo.IME_ACTION_DONE) {
            val view = this.currentFocus
            view?.let { v ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as
                        InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
            if (textView == detailTaskDescriptionTV) {
                presenter?.addDescription(textView.text.toString())
            }else{
                presenter?.changeShelf(textView.text.toString())
            }
            return true
        }
        return false
    }

    override fun finishActivity() {
        startActivity(MainActivity::class.java)
    }

    override fun setTime(time: String) {
        detailTimerTimeTV.visibility = View.VISIBLE
        val text = getString(R.string.timer_prefix) + ": " + time
        detailTimerTimeTV.text = text
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            presenter?.updateTaskObject(detailPriorityTV.text.toString(), detailTaskShelfET.text.toString())
            startActivity(MainActivity::class.java)
        }
        return super.onOptionsItemSelected(item)
    }
}
