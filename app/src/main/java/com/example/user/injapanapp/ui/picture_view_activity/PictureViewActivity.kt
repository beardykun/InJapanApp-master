package com.example.user.injapanapp.ui.picture_view_activity

import android.os.Bundle
import android.util.DisplayMetrics
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.database.TaskRepository
import com.example.user.injapanapp.ui.create_task_activity.BitmapUtils
import com.example.user.injapanapp.ui.general_activity.GeneralActivityWithAppBar
import kotlinx.android.synthetic.main.activity_picture_view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PictureViewActivity : GeneralActivityWithAppBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_view)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        paintView.init(displayMetrics)

        val repository = TaskRepository(this.application)
        doAsync {
            val taskObject =
                repository.findByTaskNumber(SharedPreferencesClass.getStringFromPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS))
            uiThread {
                val bitmap = BitmapUtils.resamplePic(ThisApplication.getInstance(), taskObject.taskPhoto!!)
                pictureIV.setImageBitmap(bitmap)
            }
        }
    }
}
