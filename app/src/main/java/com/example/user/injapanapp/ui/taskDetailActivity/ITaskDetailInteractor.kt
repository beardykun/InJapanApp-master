package com.example.user.injapanapp.ui.taskDetailActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralInteractorListener

interface ITaskDetailInteractor {

    interface OnTaskDetailListener : IGeneralInteractorListener{
        fun onSuccess(taskObject: TaskObject)
        fun onSuccessUpdatePay()
        fun onSuccessUpdateFinished()
    }
    fun getTaskDataFromDb(taskNumber: String, listener: OnTaskDetailListener)

    fun updatePaymentStatus(taskObject: TaskObject, listener: OnTaskDetailListener)
    fun updateFinishedStatus(taskObject: TaskObject, listener: OnTaskDetailListener)
    fun editDescription(taskObject: TaskObject, listener: OnTaskDetailListener)
}