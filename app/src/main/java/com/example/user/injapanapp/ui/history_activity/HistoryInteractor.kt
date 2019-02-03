package com.example.user.injapanapp.ui.history_activity

import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HistoryInteractor : IHistoryInteractor {

    override fun getAllTasks(listener: IHistoryInteractor.OnHistoryListener) {
        val repository = TaskRepository(ThisApplication.getInstance())
        doAsync {
            val list = repository.returnAllTasks()
            uiThread {
                listener.onSuccess(list)
            }
        }
    }

    override fun deleteTask(taskObject: TaskObject, listener: IHistoryInteractor.OnHistoryListener) {
        val repository = TaskRepository(ThisApplication.getInstance())
        repository.delete(taskObject)
        listener.onSuccessDelete()
    }
}