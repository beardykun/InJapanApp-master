package com.example.user.injapanapp.ui.createTaskActivity

import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository

class CreateTaskInteractor : ICreateTaskInteractor {

    override fun validateAndInsert(
        taskNumber: String, taskType: String,
        taskPrice: String, taskShelf: String, taskDescription: String,
        listener: ICreateTaskInteractor.OnCreateTaskListener
    ) {
        when {
            taskNumber.length < 7 -> listener.onError("Enter valid number", 1)
            taskType == "" -> listener.onError("Choose task type", 2)
            taskPrice == "" -> listener.onError("Enter price", 3)
            taskShelf == "" -> listener.onError("Enter a shelf number", 4)
            taskDescription == "" -> listener.onError("Enter task Description", 5)
            else -> {
                val time = System.currentTimeMillis()
                val taskObject = TaskObject(null)
                taskObject.taskNumber = taskNumber
                taskObject.taskStartTime = time.toString()
                taskObject.taskType = taskType
                taskObject.taskPrice = taskPrice
                taskObject.taskShelfNumber = taskShelf
                taskObject.taskDescription = taskDescription
                insert(taskObject, listener)
            }
        }
    }

    private fun insert(taskObject: TaskObject, listener: ICreateTaskInteractor.OnCreateTaskListener) {
        val repository = TaskRepository(ThisApplication.getInstance())
        repository.insert(taskObject)
        listener.onSuccess()
    }
}