package com.example.todoapp.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.entities.Task

class TaskAdapter (private var tasks:List<Task> , private val onDeleteClick :(Task) -> Unit): RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_task,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {

        holder.render(tasks[position], onDeleteClick)
    }

    override fun getItemCount() :Int = tasks.size

    fun updateTasks(newTasks: List<Task>){
        tasks = newTasks
        notifyDataSetChanged()
    }
}