package com.example.user.injapanapp.ui.main_activity

import com.example.user.injapanapp.app.TaskComparator
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.database.TaskObject
import com.example.user.injapanapp.database.TaskRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class MainInteractor : IMainInteractor {

    override fun getTaskList(sort: String, listener: IMainInteractor.OnMainListener) {
        val repository = TaskRepository(ThisApplication.getInstance())

        doAsync {
            val list = repository.getAllNotCompleted("0")
            uiThread {
                sortList(sort, list)
                listener.onSuccess(list)
            }
        }
    }

    override fun getTaskListWithTaskType(
        sort: String,
        filterSet: Set<String>,
        listener: IMainInteractor.OnMainListener
    ) {
        val repository = TaskRepository(ThisApplication.getInstance())
        doAsync {
            val list = repository.getAllNotCompleted("0")
            uiThread {
                val filteredList = getRightTypes(filterSet, list)
                sortList(sort, filteredList)
                listener.onSuccess(filteredList)
            }
        }
    }

    override fun deleteTask(taskObject: TaskObject, listener: IMainInteractor.OnMainListener) {
        val repository = TaskRepository(ThisApplication.getInstance())
        repository.delete(taskObject)
        listener.onSuccessDeleted()
    }

    private fun sortList(sort: String, list: List<TaskObject>) {
        Collections.sort(list, TaskComparator(sort))
    }

    private fun getRightTypes(filterSet: Set<String>, list: List<TaskObject>): List<TaskObject> {
        val newList:MutableList<TaskObject> = mutableListOf()
        for (i in list) {
            if (filterSet.contains(i.taskType)) {
                newList.add(i)
            }
        }
        return newList
    }
}