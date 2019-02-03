package com.example.user.injapanapp.app

import com.example.user.injapanapp.database.TaskObject

class TaskComporator(private val filter: String) : Comparator<TaskObject> {

    override fun compare(task1: TaskObject?, task2: TaskObject?): Int {
        val first: String
        val second: String
        when (filter) {
            "priority" -> {
                first = task1?.taskPriority!!.toLowerCase().trim()
                second = task2?.taskPriority!!.toLowerCase().trim()
            }
            else -> {
                first = task1?.taskStartTime!!.toLowerCase().trim()
                second = task2?.taskStartTime!!.toLowerCase().trim()
            }
        }
        return first.compareTo(second)
    }
}