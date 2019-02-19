package com.example.user.injapanapp.ui.history_activity

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.MenuItem
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.adapter.MainAdapter
import com.example.user.injapanapp.ui.general_activity.GeneralActivity
import com.example.user.injapanapp.ui.task_detail_activity.TaskDetailActivity
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : GeneralActivity(), IHistoryView, MainAdapter.OnMainTaskListener, SearchView.OnQueryTextListener {

    private var presenter: IHistoryPresenter? = null
    private var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        presenter = HistoryPresenter()
        setSearchViewSettings()
    }

    override fun onStart() {
        super.onStart()
        presenter?.onAttachView(this)
        getList()
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
        adapter = MainAdapter()
        adapter?.setListToShow(list)
        adapter?.setOnMainTaskListener(this)
        historyRecyclerView.adapter = adapter
    }

    override fun onMainTaskClick(taskNum: String) {
        SharedPreferencesClass.saveStringInPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS, taskNum)
        startActivity(TaskDetailActivity::class.java)
    }

    override fun onDeleteTaskClick(taskObject: TaskObject) {
        Utils.getAlert(this, getString(R.string.delete_task), fun() { presenter?.deleteTask(taskObject) })
    }

    override fun getList() {
        presenter?.getAllTasks()
    }

    private fun setSearchViewSettings() {
        setSupportActionBar(toolbar_history)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
        }
        search.isActivated = true
        search.onActionViewExpanded()
        search.isIconified = false
        search.clearFocus()
        search.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        adapter?.filter?.filter(p0)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
