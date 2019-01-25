package com.example.user.injapanapp.app

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.example.user.injapanapp.R
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit

class Utils {

    companion object {
        fun getTimeToEnd(startTime: Long): String {
            val past = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - startTime)
            val days = past / 1440

            val hours = if (days == 0L)
                past / 60
            else {
                (past / 60) % days
            }
            val minutes = past % 60

            return String.format("$days d: $hours h: $minutes m")
        }

        fun getTimerTime(startTime: Long): String {
            val past = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - startTime)


            return String.format("$past m")
        }

        fun getAlert(context: Context, message: String, func: () -> Unit?) {
            context.alert(message) {
                yesButton { func() }
                noButton { }
            }.show()
        }

        fun setPriorityColors(textView: TextView, priority: String) {
            when (priority) {
                "LOW" -> textView.textColor = ContextCompat.getColor(ThisApplication.getInstance(), R.color.LOW)
                "NORMAL" -> textView.textColor = ContextCompat.getColor(ThisApplication.getInstance(), R.color.NORMAL)
                "HIGH" -> textView.textColor = ContextCompat.getColor(ThisApplication.getInstance(), R.color.HIGH)
                "IMMEDIATE" -> textView.textColor =
                    ContextCompat.getColor(ThisApplication.getInstance(), R.color.IMMEDIATE)
            }
        }

        fun setComplitionColor(payed: String, done: String, view: View) {
            if (payed == "1" && done == "0") {
                view.backgroundColor = ContextCompat.getColor(ThisApplication.getInstance(), R.color.payed)
            } else if (payed == "0" && done == "1") {
                view.backgroundColor = ContextCompat.getColor(ThisApplication.getInstance(), R.color.done)
            } else if (payed == "1" && done == "1") {
                view.backgroundColor =
                    ContextCompat.getColor(ThisApplication.getInstance(), R.color.payed_and_done)
            }
        }
    }
}