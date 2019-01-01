package com.example.user.injapanapp.ui.historyActivity

import com.example.user.injapanapp.app.ThisApplication
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
}