package com.example.user.injapanapp.ui.historyActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralInteractorListener

interface IHistoryInteractor {

    interface OnHistoryListener : IGeneralInteractorListener{
        fun onSuccess(list: List<TaskObject>)
        fun onSuccessDelete()
    }

    fun getAllTasks(listener: OnHistoryListener)
    fun deleteTask(taskObject: TaskObject, listener: OnHistoryListener)
}