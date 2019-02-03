package com.example.user.injapanapp.ui.history_activity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.general_activity.IGeneralPresenter

interface IHistoryPresenter: IGeneralPresenter {

    fun onAttachView(view: IHistoryView)

    fun getAllTasks()
    fun deleteTask(taskObject: TaskObject)
}