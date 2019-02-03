package com.example.user.injapanapp.ui.main_activity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.general_activity.IGeneralInteractorListener

interface IMainInteractor {

    interface OnMainListener : IGeneralInteractorListener {
        fun onSuccess(list: List<TaskObject>)
        fun onSuccessDeleted()
    }

    fun getTaskList(sort: String, listener: OnMainListener)
    fun getTaskListWithTaskType(stringFromPreferences: String, listener: OnMainListener)
    fun deleteTask(taskObject: TaskObject, listener: OnMainListener)
}