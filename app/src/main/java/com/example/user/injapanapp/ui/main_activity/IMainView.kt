package com.example.user.injapanapp.ui.main_activity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.general_activity.IGeneralView

interface IMainView: IGeneralView {
    fun setAdapter(list: List<TaskObject>)
    fun getList()
}