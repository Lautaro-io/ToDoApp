package com.example.todoapp.ui.view

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.database.entities.Task
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.todoapp.R
import com.example.todoapp.notifications.ReminderWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var rvTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI() //Funcion encargada de inicializar el recyclerView
        initListener() // Funcion encargada de escuchar los click en el boton
        observeTasks() // esta funcion mantiene actualizada la vista, trabajando en conjunto el viewmodel con el taskadapter
    }

    private fun initListener() {
        binding.fobBtn.setOnClickListener { showDialog() }
    }

    private fun observeTasks(){
        lifecycleScope.launch {
            viewModel.tasks.collect { tasks ->
                taskAdapter.updateTasks(tasks)
            }
        }
    }

    private fun initUI() {
        rvTasks = binding.rvTask
        rvTasks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false )
        taskAdapter = TaskAdapter(emptyList()) {
                task ->
            showConfirmDialog(task)// hacer un toast o un dialog con dos botones para confirmar
        }
        rvTasks.adapter = taskAdapter
    }

    private fun showConfirmDialog(task: Task) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_confirm)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        val deleteBtn: Button = dialog.findViewById(R.id.confirmBtn)
        val cancelBtn: Button = dialog.findViewById(R.id.cancelBtn)

        deleteBtn.setOnClickListener {
            viewModel.deleteTask(task)
            dialog.dismiss()
        }
        cancelBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun showDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_view )
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        val titleTask: EditText = dialog.findViewById(R.id.dialog_title_task)
        val descriptionTask : EditText = dialog.findViewById(R.id.dialog_description_task)
        var addTask: Button = dialog.findViewById(R.id.dialog_btn_add)

        addTask.setOnClickListener {
            val title = titleTask.text.toString().trim()
            val description = descriptionTask.text.toString().trim()

            if (title.isNotBlank()&& description.isNotEmpty()){
                val newTask = Task(0,title,description)
                viewModel.insertTask(newTask)
                scheduleNotification(newTask)
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    private fun scheduleNotification(task: Task){
        val workManager = WorkManager.getInstance(application)

        val notificationRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(1, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("taskTitle" to task.title))
            .build()
        workManager.enqueue(notificationRequest)
    }
}