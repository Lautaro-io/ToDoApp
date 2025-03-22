package com.example.todoapp.ui.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.entities.Task

class TaskViewHolder (view: View): RecyclerView.ViewHolder(view) {
    private val tvTaskTitle: TextView = view.findViewById(R.id.title_task)
    private val tvDescription: TextView = view.findViewById(R.id.description_task)
    private val deleteBtn : Button = view.findViewById(R.id.delete_task)


    fun render(task: Task , onDeleteCLick : (Task) -> Unit ){ //Ponemos como parametro una funcion lambda para poder hacer el callback de la dfuncion encargada de eliminar la tarea
        tvTaskTitle.text = task.title
        tvDescription.text = task.description
        deleteBtn.setOnClickListener { onDeleteCLick(task)

        }
    }
}