package com.example.user.injapanapp.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = DatabaseContract.TASK_TABLE)
data class TaskObject (
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
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_TIME_PASSED) var taskTimePassed: String? = "0",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_TIMER_IS_RUNNING) var taskTimerIsRunning: String? = "0",
    @ColumnInfo(name = DatabaseContract.TaskTable.TASK_PRIORITY) var taskPriority: String? = "NORMAL"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(taskNumber)
        parcel.writeString(taskStartTime)
        parcel.writeString(taskDone)
        parcel.writeString(taskGotMoney)
        parcel.writeString(taskShelfNumber)
        parcel.writeString(taskType)
        parcel.writeString(taskPrice)
        parcel.writeString(taskDescription)
        parcel.writeString(taskFinished)
        parcel.writeString(taskPhoto)
        parcel.writeString(taskStartTimer)
        parcel.writeString(taskTimePassed)
        parcel.writeString(taskTimerIsRunning)
        parcel.writeString(taskPriority)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskObject> {
        override fun createFromParcel(parcel: Parcel): TaskObject {
            return TaskObject(parcel)
        }

        override fun newArray(size: Int): Array<TaskObject?> {
            return arrayOfNulls(size)
        }
    }
}
