package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 1. Added a lambda function (onDeleteClick) so MainActivity handles the database deletion
class TaskAdapter(private var taskList: List<Task>,private val onDeleteClick: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]

        holder.tvTaskTitle.text = task.taskName
        holder.tvTaskDescription.text = task.taskDescription

        holder.btnDeleteTask.setOnClickListener {
            onDeleteClick(task)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }


    fun updateTasks(newTasks: List<Task>) {
        taskList = newTasks
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val tvTaskDescription: TextView = itemView.findViewById(R.id.tvTaskDescription)
        val btnDeleteTask: ImageButton = itemView.findViewById(R.id.btnDeleteTask)
    }
}