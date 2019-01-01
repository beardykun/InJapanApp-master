package com.example.user.injapanapp.ui.createTaskActivity

import com.example.user.injapanapp.ui.generalActivity.IGeneralPresenter

interface ICreateTaskPresenter : IGeneralPresenter {

    fun onAttachView(view: ICreateTaskView)

    fun validateAndInsert(
        taskNumber: String, taskType: String,
        taskPrice: String, taskShelf: String, taskDescription: String
    )
}