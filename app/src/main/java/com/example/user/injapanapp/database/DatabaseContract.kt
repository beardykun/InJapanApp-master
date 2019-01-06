package com.example.user.injapanapp.database

class DatabaseContract {
    companion object {
        const val TASK_TABLE = "taskTable"
    }

    class TaskTable {
        companion object {
            const val TASK_NUMBER = "taskNumber"
            const val TASK_TYPE = "taskType"
            const val TASK_START = "taskStart"
            const val TASK_DONE = "taskDone"
            const val TASK_GOT_MONEY = "taskEnd"
            const val TASK_COST = "taskCost"
            const val TASK_DESCRIPTION = "taskDescription"
            const val TASK_SHELF = "taskShelf"
            const val TASK_FINISHED = "taskFinished"
            const val TASK_PICTURE = "taskPicture"
            const val TASK_START_TIMER = "taskStartTimer"
            const val TASK_TIME_PASSED = "taskTimePassed"
        }
    }
}