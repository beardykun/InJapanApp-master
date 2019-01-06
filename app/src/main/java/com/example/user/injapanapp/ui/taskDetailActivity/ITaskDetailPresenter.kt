package com.example.user.injapanapp.ui.taskDetailActivity

import android.support.design.widget.FloatingActionButton
import android.widget.EditText
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
}