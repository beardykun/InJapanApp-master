package com.example.user.injapanapp.ui.task_detail_activity

import com.example.user.injapanapp.ui.general_activity.IGeneralView

interface ITaskDetailView : IGeneralView{

    fun setTaskData()

    fun loadTaskInfo()
    fun finishActivity()
    fun setTime(time: String)
}