package com.example.user.injapanapp.ui.mainActivity

import android.os.Bundle
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import com.example.user.injapanapp.ui.createTaskActivity.CreateTaskActivity
import com.example.user.injapanapp.ui.generalActivity.GeneralActivityWithMenu
import com.example.user.injapanapp.ui.taskDetailActivity.TaskDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GeneralActivityWithMenu(), MainAdapter.OnMainTaskListener, IMainView {

    private var repository: TaskRepository? = null
    private var presenter: IMainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = TaskRepository(this.application)
        presenter = MainPresenter()

        mainAddTask.setOnClickListener {
            startActivity(CreateTaskActivity::class.java)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter?.onAttachView(this)
        if (!SharedPreferencesClass.isItInPreferences(Constants.TASK_TYPE))
            presenter?.getTaskList()
        else
            presenter?.getTaskListWithTaskType(SharedPreferencesClass.getStringFromPreferences(Constants.TASK_TYPE))
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

    override fun onMainTaskClick(taskNum: String) {
        SharedPreferencesClass.saveStringInPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS, taskNum)
        startActivity(TaskDetailActivity::class.java)
    }

    override fun setAdapter(list: List<TaskObject>) {
        val adapter = MainAdapter(list)
        adapter.setOnMainTaskListener(this)
        mainRecyclerView.adapter = adapter
    }
}
