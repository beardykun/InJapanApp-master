package com.example.user.injapanapp.database

import android.arch.persistence.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskTable")
    fun getAll(): List<TaskObject>

    @Query("SELECT * FROM taskTable WHERE taskFinished LIKE :completed")
    fun getAllNotCompleted(completed: String): List<TaskObject>

    @Query("SELECT * FROM taskTable WHERE taskFinished LIKE :completed AND taskType LIKE :type")
    fun getAllNotCompletedWithTaskType(completed: String, type: String): List<TaskObject>

    @Query("SELECT * FROM taskTable WHERE taskNumber LIKE :number LIMIT 1")
    fun findByTaskNumber(number: String): TaskObject

    //todo check if exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg taskObject: TaskObject)

    @Delete
    fun deleteTask(taskObject: TaskObject)

    @Update
    fun updateTask(taskObject: TaskObject)

}