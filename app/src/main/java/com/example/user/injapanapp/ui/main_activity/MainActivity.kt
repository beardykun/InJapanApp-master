package com.example.user.injapanapp.ui.main_activity

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import com.example.user.injapanapp.ui.adapter.MainAdapter
import com.example.user.injapanapp.ui.create_task_activity.CreateTaskActivity
import com.example.user.injapanapp.ui.general_activity.GeneralActivityWithMenu
import com.example.user.injapanapp.ui.settings.SettingsActivity
import com.example.user.injapanapp.ui.task_detail_activity.TaskDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GeneralActivityWithMenu(), MainAdapter.OnMainTaskListener, IMainView {

    private var repository: TaskRepository? = null
    private var presenter: IMainPresenter? = null
    private var preferencesChanged = true
    private var filter: String = ""
    private var sort: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = TaskRepository(this.application)
        presenter = MainPresenter()

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(onSharedPreferencesListener)
    }

    override fun onStart() {
        super.onStart()
        if (preferencesChanged) {
            filter = PreferenceManager.getDefaultSharedPreferences(this).getString(
                Constants.FILTER_TASK_TYPE,
                getString(R.string.show_all_tasks)
            )!!
            sort = PreferenceManager.getDefaultSharedPreferences(this).getString(
                Constants.SORT_TASK_TYPE,
                getString(R.string.none)
            )!!
            preferencesChanged = false
        }
        getList()
        mainAddTask.setOnClickListener {
            startActivity(CreateTaskActivity::class.java)
        }
    }

    override fun onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(onSharedPreferencesListener)
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

    override fun onMainTaskClick(taskNum: String) {
        SharedPreferencesClass.saveStringInPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS, taskNum)
        startActivity(TaskDetailActivity::class.java)
    }

    override fun onDeleteTaskClick(taskObject: TaskObject) {
        Utils.getAlert(this, getString(R.string.delete_task), fun() {
            presenter?.deleteTask(taskObject)
        })
    }

    override fun setAdapter(list: List<TaskObject>) {
        val adapter = MainAdapter(list)
        adapter.setOnMainTaskListener(this)
        mainRecyclerView.adapter = adapter
    }

    override fun getList() {
        presenter?.onAttachView(this)
        if (filter == Constants.TASK_TYPE)
            presenter?.getTaskList(sort)
        else
            presenter?.getTaskListWithTaskType(filter)
    }

    private val onSharedPreferencesListener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, s ->
            preferencesChanged = true
            if (s == Constants.FILTER_TASK_TYPE) {
                filter = sharedPreferences.getString(Constants.FILTER_TASK_TYPE, null)!!
            } else if (s == Constants.SORT_TASK_TYPE) {
                sort = sharedPreferences.getString(Constants.SORT_TASK_TYPE, null)!!
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_settings)
            startActivity(SettingsActivity::class.java, false)
        return super.onOptionsItemSelected(item)
    }
}
