package com.example.user.injapanapp.app

import com.example.user.injapanapp.database.TaskObject

class TaskComparator(private val filter: String) : Comparator<TaskObject> {
    private val orderList = listOf("IMMEDIATE", "HIGH", "NORMAL", "LOW")

    override fun compare(task1: TaskObject?, task2: TaskObject?): Int {
        val first: String
        val second: String
        return when (filter) {
            "priority" -> {
                first = task1?.taskPriority!!
                second = task2?.taskPriority!!
                Integer.valueOf(orderList.indexOf(first))
                    .compareTo(
                        Integer.valueOf(
                            orderList.indexOf(second)
                        )
                    )
            }
            else -> {
                first = task1?.taskStartTime!!
                second = task2?.taskStartTime!!
                first.compareTo(second)
            }
        }
    }
}