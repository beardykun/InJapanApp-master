package com.example.user.injapanapp.database

import android.app.IntentService
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.user.injapanapp.app.ThisApplication


class DBUpdateService(private val taskRep: TaskRepository = TaskRepository(ThisApplication.getInstance())) :
    IntentService(TAG) {


    companion object {
        private val TAG = DBUpdateService::class.java.simpleName
        val ACTION_INSERT = "$TAG.INSERT"
        val ACTION_UPDATE = "$TAG.UPDATE"
        val ACTION_DELETE = "$TAG.DELETE"

        val EXTRA_VALUES = "$TAG.ContentValues"

        fun insertTask(context: Context, taskObject: TaskObject) {
            val intent = Intent(context, DBUpdateService::class.java)
            intent.action = ACTION_INSERT
            intent.putExtra(EXTRA_VALUES, taskObject)
            context.startService(intent)
        }

        fun updateTask(context: Context, taskObject: TaskObject) {
            val intent = Intent(context, DBUpdateService::class.java)
            intent.action = ACTION_UPDATE
            intent.putExtra(EXTRA_VALUES, taskObject)
            context.startService(intent)
        }

        fun deleteTask(context: Context, taskObject: TaskObject) {
            val intent = Intent(context, DBUpdateService::class.java)
            intent.action = ACTION_DELETE
            intent.putExtra(EXTRA_VALUES, taskObject)
            context.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        when {
            ACTION_INSERT == intent?.action -> {
                val values = intent.getParcelableExtra<TaskObject>(EXTRA_VALUES)
                performInsert(values)
            }
            ACTION_UPDATE == intent?.action -> {
                val values = intent.getParcelableExtra<TaskObject>(EXTRA_VALUES)
                performUpdate(values)
            }
            ACTION_DELETE == intent?.action -> {
                val values = intent.getParcelableExtra<TaskObject>(EXTRA_VALUES)
                performDelete(values)
            }
        }
    }

    private fun performDelete(taskObject: TaskObject) {
        taskRep.delete(taskObject)
        Log.d(TAG, "Deleted ${taskObject.taskNumber} trainings")
    }

    private fun performUpdate(taskObject: TaskObject) {
        taskRep.update(taskObject)
        Log.d(TAG, "Updated ${taskObject.taskNumber} task item")
    }

    private fun performInsert(taskObject: TaskObject) {
        taskRep.insert(taskObject)
            Log.d(TAG, "Inserted new task ${taskObject.taskNumber}")
    }
}
