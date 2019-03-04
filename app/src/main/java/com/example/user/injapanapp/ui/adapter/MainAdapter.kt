package com.example.user.injapanapp.ui.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.user.injapanapp.R
import com.example.user.injapanapp.app.ThisApplication
import com.example.user.injapanapp.app.Utils
import com.example.user.injapanapp.database.TaskObject
import org.jetbrains.anko.find
import java.util.concurrent.TimeUnit

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>(),
    Filterable {

    private var tasksFilter: TasksFilter? = null
    private var taskListToShow: List<TaskObject>? = null
    private var taskList: List<TaskObject>? = null

    interface OnMainTaskListener {
        fun onMainTaskClick(taskNum: String)
        fun onDeleteTaskClick(taskObject: TaskObject)
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
        return if (taskListToShow != null)
        taskListToShow!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (taskListToShow != null) {
            if (listener != null) {
                holder.itemView.setOnClickListener {
                    listener?.onMainTaskClick(taskListToShow!![holder.adapterPosition].taskNumber)
                }
                holder.itemView.setOnLongClickListener {
                    listener?.onDeleteTaskClick(taskListToShow!![holder.adapterPosition])
                    true
                }
                setStuff(holder, position)
            }
        }else{
            holder.taskNumber.text = "No Task Present"
        }
    }

    private fun setStuff(holder: MainViewHolder, position: Int) {
        if (taskListToShow!!.isNotEmpty()) {
            Utils.setCompletionColor(
                taskListToShow!![position].taskGotMoney!!,
                taskListToShow!![position].taskDone,
                holder.itemView
            )
            holder.taskOrderNum.text = (position + 1).toString()
            holder.taskNumber.text = taskListToShow!![position].taskNumber
            holder.dateText.text = taskListToShow!![position].taskType
            holder.shelfText.text = taskListToShow!![position].taskShelfNumber
            val time =
                TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - taskListToShow!![position].taskStartTime!!.toLong())
            when (time) {
                1L -> holder.dateText.setTextColor(ContextCompat.getColor(ThisApplication.getInstance(), R.color.HIGH))
                2L -> holder.dateText.setTextColor(ContextCompat.getColor(ThisApplication.getInstance(), R.color.payed))
                in 3L..10L -> holder.dateText.setTextColor(
                    ContextCompat.getColor(ThisApplication.getInstance(), R.color.colorAccent)
                )
            }
            if (taskListToShow!![position].taskPriority != null)
                Utils.setPriorityColors(holder.taskNumber, taskListToShow!![position].taskPriority!!)
        }else{
            holder.taskNumber.text = ThisApplication.getInstance().getString(R.string.no_tasks)
        }
    }

    fun setListToShow(list: List<TaskObject>){
        taskList = list
        taskListToShow = taskList
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskOrderNum = itemView.find<TextView>(R.id.mainTaskOrderNum)
        val taskNumber = itemView.find<TextView>(R.id.mainGoodsNumberTV)
        val dateText = itemView.find<TextView>(R.id.mainDateTV)
        val shelfText = itemView.find<TextView>(R.id.mainShelfTV)
    }

    override fun getFilter(): Filter {
        if (tasksFilter == null) {
            tasksFilter = TasksFilter()
        }
        return tasksFilter!!
    }

    private inner class TasksFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (constraint != null && constraint.isNotEmpty()) {
                val filterList = ArrayList<TaskObject>()
                for (task in taskList!!) {
                    if (task.taskNumber.contains(constraint)) {
                        filterList.add(task)
                    }
                }
                filterResults.count = filterList.size
                filterResults.values = filterList
            } else {
                filterResults.count = taskList!!.size
                filterResults.values = taskList
            }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
            taskListToShow = result?.values as List<TaskObject>
            notifyDataSetChanged()
        }
    }
}