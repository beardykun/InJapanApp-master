package com.example.user.injapanapp.ui.createTaskActivity

import com.example.user.injapanapp.ui.generalActivity.IGeneralInteractorListener

interface ICreateTaskInteractor {

    interface OnCreateTaskListener : IGeneralInteractorListener {

        fun onSuccess()
    }

    fun validateAndInsert(
        taskNumber: String, taskType: String,
        taskPrice: String, taskShelf: String, taskDescription: String,
        listener: OnCreateTaskListener
    )
}