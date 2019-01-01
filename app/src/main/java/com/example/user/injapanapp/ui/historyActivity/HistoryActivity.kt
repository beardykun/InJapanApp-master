package com.example.user.injapanapp.ui.historyActivity

import android.os.Bundle
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.GeneralActivityWithAppBar
import com.example.user.injapanapp.ui.mainActivity.MainAdapter
import com.example.user.injapanapp.ui.taskDetailActivity.TaskDetailActivity
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : GeneralActivityWithAppBar(), IHistoryView, MainAdapter.OnMainTaskListener {

    private var presenter: IHistoryPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        presenter = HistoryPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter?.onAttachView(this)
        presenter?.getAllTasks()
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

    override fun getAllTasks(list: List<TaskObject>) {
        val adapter = MainAdapter(list)
        adapter.setOnMainTaskListener(this)
        historyRecyclerView.adapter = adapter
    }

    override fun onMainTaskClick(taskNum: String) {
        SharedPreferencesClass.saveStringInPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS, taskNum)
        startActivity(TaskDetailActivity::class.java)
    }
}
