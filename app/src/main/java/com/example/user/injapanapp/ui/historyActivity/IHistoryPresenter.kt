package com.example.user.injapanapp.ui.historyActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralPresenter

interface IHistoryPresenter: IGeneralPresenter {

    fun onAttachView(view: IHistoryView)

    fun getAllTasks()
    fun deleteTask(taskObject: TaskObject)
}