package com.example.user.injapanapp.ui.create_task_activity

import android.content.Intent
import com.example.user.injapanapp.ui.general_activity.IGeneralView

interface ICreateTaskView: IGeneralView {

    fun finishActivity()
    fun startPictureActivity(intent: Intent)
    fun showSaveFAB()
    fun showReplaceDialog()
}