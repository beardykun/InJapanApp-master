package com.example.user.injapanapp.ui.mainActivity

import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.database.TaskRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteractor : IMainInteractor {

    override fun getTaskList(listener: IMainInteractor.OnMainListener) {
        val repository = TaskRepository(ThisApplication.getInstance())

        doAsync {
            val list = repository.getAllNotCompleted("0")
            uiThread {
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
}