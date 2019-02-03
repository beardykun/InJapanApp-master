package com.example.user.injapanapp.ui.main_activity

import com.example.user.injapanapp.app.TaskComporator
import com.example.user.injapanapp.app.ThisApplication
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

    override fun getTaskListWithTaskType(stringFromPreferences: String, listener: IMainInteractor.OnMainListener) {
        val repository = TaskRepository(ThisApplication.getInstance())
        doAsync {
            val list = repository.getAllNotCompletedWithType("0", stringFromPreferences)
            uiThread {
                listener.onSuccess(list)
            }
        }
    }

    override fun deleteTask(taskObject: TaskObject, listener: IMainInteractor.OnMainListener) {
        val repository = TaskRepository(ThisApplication.getInstance())
        repository.delete(taskObject)
        listener.onSuccessDeleted()
    }

    private fun sortList(sort: String, list: List<TaskObject>) {
        Collections.sort(list, TaskComporator(sort))
    }
}