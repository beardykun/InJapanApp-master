package com.example.user.injapanapp.ui.taskDetailActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralView

interface ITaskDetailView : IGeneralView{

    fun setTaskData(taskObject: TaskObject)

    fun loadTaskInfo()
    fun finishActivity()
    fun setTime(time: String)
}