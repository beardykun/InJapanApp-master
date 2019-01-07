package com.example.user.injapanapp.ui.taskDetailActivity

import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.widget.EditText
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Constants
import com.example.user.injapanapp.app.SharedPreferencesClass
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.image
import org.jetbrains.anko.uiThread

class TaskDetailInteractor(
    private val repository: TaskRepository =
        TaskRepository(ThisApplication.getInstance())
) : ITaskDetailInteractor {

    private var taskObject: TaskObject? = null

    override fun getTaskDataFromDb(listener: ITaskDetailInteractor.OnTaskDetailListener) {
        doAsync {
            taskObject =
                    repository.findByTaskNumber(SharedPreferencesClass.getStringFromPreferences(Constants.PASS_TASK_NUMBER_WITH_PREFS))
            uiThread {
                listener.onSuccess(taskObject!!)

            }
        }
    }

    override fun updatePaymentStatus(listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (taskObject?.taskGotMoney == "0")
            taskObject?.taskGotMoney = "1"
        else
            taskObject?.taskGotMoney = "0"

        repository.update(taskObject!!)
        Handler().postDelayed({ listener.onSuccessUpdatePay() }, 300)
    }

    override fun updateFinishedStatus(listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (taskObject?.taskGotMoney == "1") {
            taskObject?.taskFinished = "1"
            repository.update(taskObject!!)
            listener.onSuccessUpdateFinished()
        } else {
            listener.onError("Don't forget to take money first!!!", 7)
        }
    }

    override fun editDescription(string: String, listener: ITaskDetailInteractor.OnTaskDetailListener) {
        taskObject?.taskDescription = string
        repository.update(taskObject!!)
        Handler().postDelayed({ listener.onSuccessUpdatePay() }, 300)
    }

    override fun updateDoneStatus(listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (taskObject?.taskDone == "0")
            taskObject?.taskDone = "1"
        else
            taskObject?.taskDone = "0"

        repository.update(taskObject!!)
        Handler().postDelayed({ listener.onSuccessUpdatePay() }, 300)
    }

    override fun startOrStopTimer(floatingActionButton: FloatingActionButton, listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (!SharedPreferencesClass.getBooleanFromPreferences(Constants.TIMER_IS_WORKING)) {
            floatingActionButton.image = ContextCompat.getDrawable(ThisApplication.getInstance(), R.drawable.ic_timer_off_black_24dp)
            taskObject?.taskStartTimer = System.currentTimeMillis().toString()
            repository.update(taskObject!!)
            //todo add timerIsStarted value to fix the bag when saving timer results to different task
            SharedPreferencesClass.saveBooleanInPreferences(Constants.TIMER_IS_WORKING, true)
            Handler().postDelayed({ listener.onSuccessUpdatePay() }, 300)
        } else {
            floatingActionButton.image = ContextCompat.getDrawable(ThisApplication.getInstance(), R.drawable.ic_timer_black_24dp)
            SharedPreferencesClass.saveBooleanInPreferences(Constants.TIMER_IS_WORKING, false)
            val time = Utils.getTimerTime(taskObject!!.taskStartTimer!!.toLong())
            taskObject?.taskTimePassed = time
            repository.update(taskObject!!)
            Handler().postDelayed({ listener.onSuccessTimerStopped(time) }, 300)
        }
    }

    override fun enableDisableEditDescription(detailTaskDescriptionTV: EditText, enabled: Boolean) {
        if (enabled) {
            detailTaskDescriptionTV.isFocusableInTouchMode = true
            detailTaskDescriptionTV.isFocusable = true
            detailTaskDescriptionTV.inputType = InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
        } else {
            detailTaskDescriptionTV.isFocusable = false
            detailTaskDescriptionTV.inputType = InputType.TYPE_NULL
        }
    }
}