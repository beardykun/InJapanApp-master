package com.example.user.injapanapp.ui.taskDetailActivity

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.user.injapanapp.ui.generalActivity.IGeneralPresenter

interface ITaskDetailPresenter: IGeneralPresenter {

    fun onAttachView(view: ITaskDetailView)

    fun getTaskDataFromDb()

    fun updatePaymentStatus()
    fun updateFinishedStatus()
    fun addDescription(string: String)
    fun updateDoneStatus()
    fun startOrStopTimer(floatingActionButton: FloatingActionButton)
    fun enableDisableEditDescription(detailTaskDescriptionTV: EditText, enabled: Boolean)
    fun setTaskData(
        detailTaskNumberTV: TextView,
        detailTaskTypeTV: TextView,
        detailTaskPriceTV: TextView,
        detailTaskShelfTV: TextView,
        detailTaskEndDateTV: TextView,
        detailTaskDescriptionTV: EditText,
        detailPhotoIV: ImageView,
        detailPriorityTV: TextView,
        detailStartTimerFAB: FloatingActionButton,
        detailMainLayout: CoordinatorLayout
    )

     fun updatePriority(priority: String)
}