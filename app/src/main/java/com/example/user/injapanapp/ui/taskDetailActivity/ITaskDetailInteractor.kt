package com.example.user.injapanapp.ui.taskDetailActivity

import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.user.injapanapp.ui.generalActivity.IGeneralInteractorListener

interface ITaskDetailInteractor {

    interface OnTaskDetailListener : IGeneralInteractorListener {
        fun onSuccess()
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
    fun setTaskData(
        detailTaskNumberTV: TextView, detailTaskTypeTV: TextView, detailTaskPriceTV: TextView,
        detailTaskShelfTV: TextView, detailTaskEndDateTV: TextView, detailTaskDescriptionTV: EditText,
        detailPhotoIV: ImageView, detailStartTimerFAB: FloatingActionButton, listener: OnTaskDetailListener
    )
}