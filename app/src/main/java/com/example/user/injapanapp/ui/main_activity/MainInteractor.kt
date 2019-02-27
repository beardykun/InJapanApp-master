package com.example.user.injapanapp.ui.main_activity

import android.os.Handler
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.TaskComparator
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.database.DBUpdateService
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class MainInteractor : IMainInteractor {

    override fun getTaskList(sort: String, listener: IMainInteractor.OnMainListener) {
        val repository = TaskRepository(ThisApplication.getInstance())

        doAsync {
            val list = repository.getAllNotCompleted("0")
            uiThread {
                sortList(sort, list)
                listener.onSuccess(list)
            }
        }
    }

    override fun getTaskListWithTaskType(
        sort: String,
        listener: IMainInteractor.OnMainListener
    ) {
        val repository = TaskRepository(ThisApplication.getInstance())
        doAsync {
            val list = repository.getAllNotCompletedWithType(
                "0",
                SharedPreferencesClass.getStringFromPreferences(Constants.TASK_TYPE)
            )
            uiThread {
                sortList(sort, list)
                listener.onSuccess(list)
            }
        }
    }

    override fun deleteTask(taskObject: TaskObject, listener: IMainInteractor.OnMainListener) {
        DBUpdateService.deleteTask(ThisApplication.getInstance(), taskObject)
        Handler().postDelayed(
            {listener.onSuccessDeleted()}, 300)
    }

    private fun sortList(sort: String, list: List<TaskObject>) {
        Collections.sort(list, TaskComparator(sort))
    }
}