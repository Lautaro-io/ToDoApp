package com.example.todoapp.database.repository

import com.example.todoapp.database.dao.TaskDao
import com.example.todoapp.database.entities.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository (private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}