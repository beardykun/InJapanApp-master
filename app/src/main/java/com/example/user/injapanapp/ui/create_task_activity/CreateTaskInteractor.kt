package com.example.user.injapanapp.ui.create_task_activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.ImageView
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.database.DBUpdateService
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.uiThread
import java.io.File
import java.io.IOException

class CreateTaskInteractor(private val taskObject: TaskObject = TaskObject(null)) : ICreateTaskInteractor {

    private var mTempPhotoPath: String? = null
    private var mResultsBitmap: Bitmap? = null
    private val FILE_PROVIDER_AUTHORITY = "com.example.user.fileprovider"

    override fun validateAndInsert(
        taskNumber: String, taskType: String,
        taskPrice: String, taskShelf: String,
        taskDescription: String, taskPriority: String,
        listener: ICreateTaskInteractor.OnCreateTaskListener
    ) {
        when {
            taskNumber.length < 6 -> listener.onError("Enter valid number", 1)
            taskType == "" -> listener.onError("Choose task type", 2)
            taskPrice == "" -> listener.onError("Enter price", 3)
            taskShelf == "" -> listener.onError("Enter a shelf number", 4)
            taskDescription == "" -> listener.onError("Enter task Description", 5)
            else -> {
                val time = System.currentTimeMillis()
                taskObject.taskNumber = rOrS(taskNumber.toInt())
                taskObject.taskStartTime = time.toString()
                taskObject.taskType = taskType
                taskObject.taskPrice = taskPrice
                taskObject.taskShelfNumber = taskShelf
                taskObject.taskDescription = taskDescription
                taskObject.taskPriority = taskPriority
                insert(taskObject, listener)
            }
        }
    }

    private fun insert(taskObject: TaskObject, listener: ICreateTaskInteractor.OnCreateTaskListener) {
        val repository = TaskRepository(ThisApplication.getInstance())
        doAsync {
            val checkTaskExists = repository.findByTaskNumber(taskObject.taskNumber)
            uiThread {
                if (checkTaskExists == null) {
                    DBUpdateService.insertTask(ThisApplication.getInstance(), taskObject)
                    listener.onSuccess()
                } else {
                    taskObject.id = checkTaskExists.id
                    listener.onErrorTaskInside()
                }
            }
        }
    }

    override fun replaceTask(listener: ICreateTaskInteractor.OnCreateTaskListener) {
        DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject)
        Handler().postDelayed({ listener.onSuccess() }, 300)
    }

    override fun launchCamera(listener: ICreateTaskInteractor.OnCreateTaskListener) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(ThisApplication.getInstance().packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = BitmapUtils.createTempImageFile(ThisApplication.getInstance())
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            if (photoFile != null) {
                mTempPhotoPath = photoFile.absolutePath

                val photoURI = FileProvider.getUriForFile(
                    ThisApplication.getInstance(),
                    FILE_PROVIDER_AUTHORITY, photoFile
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                SharedPreferencesClass.saveBooleanInPreferences(Constants.TAKING_PICKS, true)
                listener.onPictureSuccess(takePictureIntent)
            }
        }
    }

    override fun deleteImageFile(listener: ICreateTaskInteractor.OnCreateTaskListener) {
        BitmapUtils.deleteImageFile(ThisApplication.getInstance(), mTempPhotoPath!!)
        listener.onSaveAndClearAndDeleteSuccess()
    }

    override fun processAndSetImage(imageView: ImageView, listener: ICreateTaskInteractor.OnCreateTaskListener) {
        mResultsBitmap = BitmapUtils.resamplePic(ThisApplication.getInstance(), mTempPhotoPath!!)
        imageView.imageBitmap = mResultsBitmap
        saveImage()
        listener.onProcessAndSetImageSuccess()
    }

    private fun saveImage() {
        BitmapUtils.deleteImageFile(ThisApplication.getInstance(), mTempPhotoPath!!)
        val savedPath: String? = BitmapUtils.saveImage(ThisApplication.getInstance(), mResultsBitmap!!)
        taskObject.taskPhoto = savedPath
    }

    override fun clearImage(listener: ICreateTaskInteractor.OnCreateTaskListener) {
        BitmapUtils.deleteImageFile(ThisApplication.getInstance(), mTempPhotoPath!!)
        listener.onSaveAndClearAndDeleteSuccess()
    }

    private fun rOrS(num:Int):String{
        return if (num > 500000){
            "R-$num"
        }else{
            "S-$num"
        }
    }
}
