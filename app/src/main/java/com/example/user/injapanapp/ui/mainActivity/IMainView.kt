package com.example.user.injapanapp.ui.mainActivity

import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.ui.generalActivity.IGeneralView

interface IMainView: IGeneralView {
    fun setAdapter(list: List<TaskObject>)
}