package com.example.user.injapanapp.ui.taskDetailActivity

import com.example.user.injapanapp.ui.generalActivity.IGeneralView

interface ITaskDetailView : IGeneralView{

    fun setTaskData()

    fun loadTaskInfo()
    fun finishActivity()
    fun setTime(time: String)
}