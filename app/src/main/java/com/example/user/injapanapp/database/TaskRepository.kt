package com.example.user.injapanapp.database

import android.app.Application
import org.jetbrains.anko.doAsync

class TaskRepository(application: Application) {
    private val taskDao: TaskDao

    init {
        val db = TaskDatabase.getDatabase(application)
        taskDao = db!!.taskDao()
    }

    fun returnAllTasks(): List<TaskObject> {
        return taskDao.getAll()
    }

    fun insert(taskObject: TaskObject) {
        taskDao.insert(taskObject)
    }

    fun delete(taskObject: TaskObject) {
        taskDao.deleteTask(taskObject)
    }

    fun update(taskObject: TaskObject) {
        taskDao.updateTask(taskObject)
    }

    fun findByTaskNumber(number: String): TaskObject {
        return taskDao.findByTaskNumber(number)
    }

    fun getAllNotCompleted(completed: String): List<TaskObject> {
        return taskDao.getAllNotCompleted(completed)
    }

    fun getAllNotCompletedWithType(completed: String, type: String): List<TaskObject> {
        return taskDao.getAllNotCompletedWithTaskType(completed, type)
    }
}