package com.example.user.injapanapp.ui.history_activity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.general_activity.IGeneralView

interface IHistoryView: IGeneralView {

    fun getAllTasks(list: List<TaskObject>)
    fun getList()
}