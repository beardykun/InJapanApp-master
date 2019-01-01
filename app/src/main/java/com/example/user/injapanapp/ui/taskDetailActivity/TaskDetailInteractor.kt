package com.example.user.injapanapp.ui.taskDetailActivity

import android.os.Handler
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TaskDetailInteractor(private val repository: TaskRepository =
                               TaskRepository(ThisApplication.getInstance())) : ITaskDetailInteractor {

    override fun getTaskDataFromDb(taskNumber: String, listener: ITaskDetailInteractor.OnTaskDetailListener) {
        doAsync {
            val taskObject = repository.findByTaskNumber(taskNumber)
            uiThread {
                listener.onSuccess(taskObject)
            }
        }
    }

    override fun updatePaymentStatus(taskObject: TaskObject, listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (taskObject.taskGotMoney == "0")
            taskObject.taskGotMoney = "1"
        else
            taskObject.taskGotMoney = "0"

        repository.update(taskObject)
        listener.onSuccessUpdatePay()
    }

    override fun updateFinishedStatus(taskObject: TaskObject,
                                      listener: ITaskDetailInteractor.OnTaskDetailListener) {
        if (taskObject.taskGotMoney == "1") {
            taskObject.taskCompleted = "1"
            repository.update(taskObject)
            listener.onSuccessUpdateFinished()
        }else{
            listener.onError("Don't forget to take money first!!!", 7)
        }
    }

    override fun editDescription(taskObject: TaskObject, listener: ITaskDetailInteractor.OnTaskDetailListener) {
        repository.update(taskObject)
        Handler().postDelayed({listener.onSuccessUpdatePay()}, 1000)
    }
}