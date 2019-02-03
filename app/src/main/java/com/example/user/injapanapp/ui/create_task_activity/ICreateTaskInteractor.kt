package com.example.user.injapanapp.ui.create_task_activity

import android.content.Intent
import android.widget.ImageView
import com.example.user.injapanapp.ui.general_activity.IGeneralInteractorListener

interface ICreateTaskInteractor {

    interface OnCreateTaskListener : IGeneralInteractorListener {

        fun onSuccess()
        fun onPictureSuccess(intent: Intent)
        fun onSaveAndClearAndDeleteSuccess()
        fun onProcessAndSetImageSuccess()
        fun onErrorTaskInside()
    }

    fun validateAndInsert(
        taskNumber: String, taskType: String,
        taskPrice: String, taskShelf: String,
        taskDescription: String, taskPriority: String,
        listener: OnCreateTaskListener
    )

    fun launchCamera(listener: OnCreateTaskListener)
    fun deleteImageFile(listener: OnCreateTaskListener)
    fun processAndSetImage(imageView: ImageView, listener: OnCreateTaskListener)
    fun saveImage(listener: OnCreateTaskListener)
    fun clearImage(listener: OnCreateTaskListener)
    fun replaceTask(listener: OnCreateTaskListener)
}