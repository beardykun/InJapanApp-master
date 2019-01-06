package com.example.user.injapanapp.ui.taskDetailActivity

import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralInteractorListener

interface ITaskDetailInteractor {

    interface OnTaskDetailListener : IGeneralInteractorListener{
        fun onSuccess(taskObject: TaskObject)
        fun onSuccessUpdatePay()
        fun onSuccessUpdateFinished()
        fun onSuccessTimerStopped(time: String)
    }
    fun getTaskDataFromDb(listener: OnTaskDetailListener)

    fun updatePaymentStatus(listener: OnTaskDetailListener)
    fun updateFinishedStatus(listener: OnTaskDetailListener)
    fun editDescription(string: String, listener: OnTaskDetailListener)
    fun updateDoneStatus(listener: OnTaskDetailListener)
    fun startOrStopTimer(floatingActionButton: FloatingActionButton, listener: OnTaskDetailListener)
    fun enableDisableEditDescription(detailTaskDescriptionTV: EditText, enabled: Boolean)
}