package com.example.user.injapanapp.ui.historyActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralView

interface IHistoryView: IGeneralView {

    fun getAllTasks(list: List<TaskObject>)
}