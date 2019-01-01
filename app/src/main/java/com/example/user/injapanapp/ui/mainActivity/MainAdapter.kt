package com.example.user.injapanapp.ui.mainActivity

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.TaskObject
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

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
        if (listener != null){
            holder.itemView.setOnClickListener {
                listener?.onMainTaskClick(taskList[holder.adapterPosition].taskNumber) }
        }
        if (taskList[position].taskGotMoney == "1"){
            holder.taskNumber.textColor = Color.RED
        }
        holder.taskNumber.text = taskList[position].taskNumber
        holder.dateText.text = Utils.getTimeToEnd(taskList[position].taskStartTime!!.toLong())
        holder.taskText.text = taskList[position].taskType
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNumber = itemView.find<TextView>(R.id.mainGoodsNumberTV)
        val dateText = itemView.find<TextView>(R.id.mainDateTV)
        val taskText = itemView.find<TextView>(R.id.mainTaskTV)
    }
}