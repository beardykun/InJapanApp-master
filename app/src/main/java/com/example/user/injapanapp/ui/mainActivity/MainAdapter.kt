package com.example.user.injapanapp.ui.mainActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.TaskObject
import kotlinx.android.synthetic.main.item_main.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find

class MainAdapter(private val taskList: List<TaskObject>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    interface OnMainTaskListener {
        fun onMainTaskClick(taskNum: String)
    }

    private var listener: OnMainTaskListener? = null

    fun setOnMainTaskListener(listener: OnMainTaskListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)

        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (listener != null) {
            holder.itemView.setOnClickListener {
                listener?.onMainTaskClick(taskList[holder.adapterPosition].taskNumber)
            }
        }
        if (taskList[position].taskGotMoney == "1" && taskList[position].taskDone == "0") {
            holder.itemView.backgroundColor = ContextCompat.getColor(ThisApplication.getInstance(), R.color.payed)
        } else if (taskList[position].taskGotMoney == "0" && taskList[position].taskDone == "1") {
            holder.itemView.backgroundColor = ContextCompat.getColor(ThisApplication.getInstance(), R.color.done)
        } else if (taskList[position].taskGotMoney == "1" && taskList[position].taskDone == "1") {
            holder.itemView.backgroundColor = ContextCompat.getColor(ThisApplication.getInstance(), R.color.payed_and_done)
        }
        holder.taskNumber.text = taskList[position].taskNumber
        holder.dateText.text = Utils.getTimeToEnd(taskList[position].taskStartTime!!.toLong())
        holder.taskText.text = taskList[position].taskType
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNumber = itemView.find<TextView>(R.id.mainGoodsNumberTV)
        val dateText = itemView.find<TextView>(R.id.mainDateTV)
        val taskText = itemView.find<TextView>(R.id.mainTaskTV)
        val cardView = itemView.find<LinearLayout>(R.id.mainLinearView)
    }
}