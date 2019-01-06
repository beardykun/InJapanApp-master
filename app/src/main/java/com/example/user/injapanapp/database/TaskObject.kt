package com.example.user.injapanapp.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = DatabaseContract.TASK_TABLE)
data class TaskObject(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_NUMBER) var taskNumber: String = "",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_START) var taskStartTime: String? = "",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_DONE) var taskDone: String = "0",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_GOT_MONEY) var taskGotMoney: String? = "0",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_SHELF) var taskShelfNumber: String? = "",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_TYPE) var taskType: String? = "",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_COST) var taskPrice: String? = "",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_DESCRIPTION) var taskDescription: String? = "",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_FINISHED) var taskFinished: String? = "0",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_PICTURE) var taskPhoto: String? = "",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_START_TIMER) var taskStartTimer: String? = "0",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_TIME_PASSED) var taskTimePassed: String? = "0"
)

