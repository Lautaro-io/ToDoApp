package com.example.todoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.database.entities.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    //Insert data on db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    //Update Task from db
    @Update
    suspend fun updateTask(task: Task)

    //Delete task from DB
    @Delete
    suspend fun deleteTask(task: Task)


    //Query that collect all tasks in the DB ordered by descendent order
    //Flow es una API de Kotlin que te permite manejar flujo de datos en tiempo real y mostrarlos en pantalla principal sin romper el hilo principal de la applicacion
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>

}