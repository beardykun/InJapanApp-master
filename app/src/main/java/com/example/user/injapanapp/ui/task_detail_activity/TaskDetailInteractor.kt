package com.example.user.injapanapp.ui.task_detail_activity

import android.os.Handler
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.DBUpdateService
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import com.example.user.injapanapp.ui.create_task_activity.BitmapUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.image
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class TaskDetailInteractor : ITaskDetailInteractor {

    private var taskObject: TaskObject? = null

    override fun getTaskDataFromDb(listener: ITaskDetailInteractor.OnTaskDetailListener) {
        val repository = TaskRepository(ThisApplication.getInstance())
        doAsync {
            taskObject =
                repository.findByTaskNumber(SharedPreferencesClass.getStringFromPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS))
            uiThread {
                listener.onSuccess()
            }
        }
    }

    override fun updatePaymentStatus(listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (taskObject?.taskGotMoney == "0")
            taskObject?.taskGotMoney = "1"
        else
            taskObject?.taskGotMoney = "0"

        DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
        Utils.getHandler { listener.onSuccessUpdate() }
    }

    override fun updateFinishedStatus(listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (taskObject?.taskFinished == "0") {
            if (taskObject?.taskGotMoney == "1" && taskObject?.taskDone == "1") {
                taskObject?.taskFinished = "1"
                DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
                listener.onSuccessUpdateFinished()
            } else {
                listener.onError("Don't forget to take money first!!!", 7)
            }
        } else {
            taskObject?.taskFinished = "0"
            DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
            Utils.getHandler { listener.onSuccessUpdateFinished() }
        }
    }

    override fun editDescription(string: String, listener: ITaskDetailInteractor.OnTaskDetailListener) {
        taskObject?.taskDescription = string
        DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
        Utils.getHandler { listener.onSuccessUpdate() }
    }

    override fun updateDoneStatus(listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (taskObject?.taskDone == "0")
            taskObject?.taskDone = "1"
        else
            taskObject?.taskDone = "0"

        DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
        Utils.getHandler { listener.onSuccessUpdate() }
    }

    override fun startOrStopTimer(
        floatingActionButton: FloatingActionButton,
        listener: ITaskDetailInteractor.OnTaskDetailListener
    ) {
        if (taskObject?.taskTimerIsRunning == "0") {
            floatingActionButton.image =
                ContextCompat.getDrawable(ThisApplication.getInstance(), R.drawable.ic_timer_off_black_24dp)
            taskObject?.taskStartTimer = System.currentTimeMillis().toString()
            taskObject?.taskTimerIsRunning = "1"
            DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
            Handler().postDelayed({ listener.onSuccessUpdate() }, 300)
        } else {
            floatingActionButton.image =
                ContextCompat.getDrawable(ThisApplication.getInstance(), R.drawable.ic_timer_black_24dp)
            taskObject?.taskTimerIsRunning = "0"
            val time = Utils.getTimerTime(taskObject!!.taskStartTimer!!.toLong())
            taskObject?.taskTimePassed = time
            DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
            Utils.getHandler { listener.onSuccessTimerStopped(time) }
        }
    }

    override fun setTaskData(
        detailTaskNumberTV: TextView,
        detailTaskTypeTV: TextView,
        detailTaskPriceTV: TextView,
        detailTaskShelfTV: TextView,
        detailTaskEndDateTV: TextView,
        detailTaskDescriptionTV: EditText,
        detailPhotoIV: ImageView,
        detailPriorityTV: TextView,
        detailStartTimerFAB: FloatingActionButton,
        detailMainLayout: CoordinatorLayout,
        listener: ITaskDetailInteractor.OnTaskDetailListener
    ) {
        detailTaskNumberTV.text = taskObject?.taskNumber
        detailTaskTypeTV.text = taskObject?.taskType
        detailTaskPriceTV.text = taskObject?.taskPrice
        detailTaskShelfTV.text = taskObject?.taskShelfNumber
        detailTaskEndDateTV.text = Utils.getTimeToEnd(taskObject?.taskStartTime!!.toLong())
        detailTaskDescriptionTV.setText(taskObject?.taskDescription)
        detailPriorityTV.text = taskObject?.taskPriority
        if (taskObject?.taskPriority != null)
            Utils.setPriorityColors(detailPriorityTV, taskObject?.taskPriority!!)
        Utils.setCompletionColor(taskObject?.taskGotMoney!!, taskObject?.taskDone!!, detailMainLayout)

        if (taskObject?.taskGotMoney == "1") {
            detailTaskPriceTV.setTextColor(ContextCompat.getColor(ThisApplication.getInstance(), R.color.colorAccent))
        } else {
            detailTaskPriceTV.setTextColor(ContextCompat.getColor(ThisApplication.getInstance(), R.color.black))
        }
        if (taskObject?.taskDone == "1") {
            detailTaskTypeTV.setTextColor(ContextCompat.getColor(ThisApplication.getInstance(), R.color.colorAccent))
        } else {
            detailTaskTypeTV.setTextColor(ContextCompat.getColor(ThisApplication.getInstance(), R.color.black))
        }
        if (!taskObject?.taskPhoto!!.isEmpty()) {
            try {
                val bitmap = BitmapUtils.resamplePic(ThisApplication.getInstance(), taskObject?.taskPhoto!!)
                detailPhotoIV.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                ThisApplication.getInstance().toast("Image was deleted")
            }
        }
        if (taskObject?.taskTimePassed != "0") {
            listener.onSuccessTimerStopped(taskObject?.taskTimePassed!!)
        }
        if (taskObject?.taskTimerIsRunning == "1") {
            detailStartTimerFAB.image =
                ContextCompat.getDrawable(ThisApplication.getInstance(), R.drawable.ic_timer_off_black_24dp)
        }
    }

    override fun updateTaskObject(
        detailPriority: String,
        detailTaskShelf: String
    ) {
        taskObject?.taskPriority = detailPriority
        taskObject?.taskShelfNumber = detailTaskShelf
        DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
    }

    override fun updateShelf(newShelf: String, listener: ITaskDetailInteractor.OnTaskDetailListener) {
        taskObject?.taskShelfNumber = newShelf
        DBUpdateService.updateTask(ThisApplication.getInstance(), taskObject!!)
        Utils.getHandler { listener.onSuccessUpdate() }
    }
}