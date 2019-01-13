package com.example.user.injapanapp.ui.mainActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralPresenter

interface IMainPresenter: IGeneralPresenter {

    fun onAttachView(view: IMainView)
    fun getTaskList()
    fun getTaskListWithTaskType(stringFromPreferences: String)
    fun deleteTask(taskObject: TaskObject)
}