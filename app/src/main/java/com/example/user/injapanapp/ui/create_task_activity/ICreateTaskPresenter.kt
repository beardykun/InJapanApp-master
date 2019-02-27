package com.example.user.injapanapp.ui.create_task_activity

import android.widget.ImageView
import com.example.user.injapanapp.ui.general_activity.IGeneralPresenter

interface ICreateTaskPresenter : IGeneralPresenter {

    fun onAttachView(view: ICreateTaskView)

    fun validateAndInsert(
        taskNumber: String, taskType: String,
        taskPrice: String, taskShelf: String,
        taskDescription: String, taskPriority: String
    )

    fun launchCamera()
    fun deleteImageFile()
    fun processAndSetImage(imageView: ImageView)
    fun clearImage()
    fun replaceTask()
}