package com.example.user.injapanapp.ui.taskDetailActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralPresenter

interface ITaskDetailPresenter: IGeneralPresenter {

    fun onAttachView(view: ITaskDetailView)

    fun getTaskDataFromDb(taskNumber: String)

    fun updatePaymentStatus(taskObject: TaskObject)
    fun updateFinishedStatus(taskObject: TaskObject)
    fun addDescription(taskObject: TaskObject)
}