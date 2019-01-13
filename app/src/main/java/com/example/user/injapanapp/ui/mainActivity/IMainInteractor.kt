package com.example.user.injapanapp.ui.mainActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralInteractorListener

interface IMainInteractor {

    interface OnMainListener : IGeneralInteractorListener {
        fun onSuccess(list: List<TaskObject>)
        fun onSuccessDeleted()
    }

    fun getTaskList(listener: OnMainListener)
    fun getTaskListWithTaskType(stringFromPreferences: String, listener: OnMainListener)
    fun deleteTask(taskObject: TaskObject, listener: OnMainListener)
}