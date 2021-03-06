package com.example.user.injapanapp.ui.task_detail_activity

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.user.injapanapp.ui.general_activity.IGeneralInteractorListener

interface ITaskDetailInteractor {

    interface OnTaskDetailListener : IGeneralInteractorListener {
        fun onSuccess()
        fun onSuccessUpdate()
        fun onSuccessUpdateFinished()
        fun onSuccessTimerStopped(time: String)
    }

    fun getTaskDataFromDb(listener: OnTaskDetailListener)

    fun updatePaymentStatus(listener: OnTaskDetailListener)
    fun updateFinishedStatus(listener: OnTaskDetailListener)
    fun editDescription(string: String, listener: OnTaskDetailListener)
    fun updateDoneStatus(listener: OnTaskDetailListener)
    fun startOrStopTimer(floatingActionButton: FloatingActionButton, listener: OnTaskDetailListener)
    fun setTaskData(
        detailTaskNumberTV: TextView, detailTaskTypeTV: TextView, detailTaskPriceTV: TextView,
        detailTaskShelfTV: TextView, detailTaskEndDateTV: TextView, detailTaskDescriptionTV: EditText,
        detailPhotoIV: ImageView, detailPriorityTV: TextView, detailStartTimerFAB: FloatingActionButton,
        detailMainLayout: CoordinatorLayout, listener: OnTaskDetailListener
    )

    fun updateTaskObject(detailPriority: String, detailTaskShelf: String)
    fun updateShelf(newShelf: String, listener: OnTaskDetailListener)
}