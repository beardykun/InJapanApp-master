package com.example.user.injapanapp.ui.createTaskActivity

import android.content.Intent
import com.example.user.injapanapp.ui.generalActivity.IGeneralView

interface ICreateTaskView: IGeneralView {

    fun finishActivity()
    fun startPictureActivity(intent: Intent)
    fun showSaveFAB()
}