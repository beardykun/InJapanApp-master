package com.example.user.injapanapp.ui.main_activity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.general_activity.IGeneralPresenter

interface IMainPresenter : IGeneralPresenter {

    fun onAttachView(view: IMainView)
    fun getTaskList(sort: String)
    fun getTaskListWithTaskType(sort: String, filterSet: Set<String>)
    fun deleteTask(taskObject: TaskObject)
}