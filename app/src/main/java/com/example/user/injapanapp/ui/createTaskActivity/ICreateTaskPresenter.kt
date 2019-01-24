package com.example.user.injapanapp.ui.createTaskActivity

import android.widget.ImageView
import com.example.user.injapanapp.ui.generalActivity.IGeneralPresenter

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
    fun saveImage()
    fun clearImage()
    fun replaceTask()
}