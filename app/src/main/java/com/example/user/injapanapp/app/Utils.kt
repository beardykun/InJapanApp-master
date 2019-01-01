package com.example.user.injapanapp.app

import java.util.concurrent.TimeUnit

class Utils {

    companion object {
        fun getTimeToEnd(startTime: Long): String {
            val past = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - startTime)
            val days = past / 1440

            val hours = if (days == 0L)
                past / 60
            else{
                (past / 60) % days
            }
            val minutes = past % 60

            return String.format("$days d: $hours h: $minutes m")
        }
    }
}