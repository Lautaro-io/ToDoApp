package com.example.todoapp.database.db

import android.content.Context
import androidx.annotation.UiContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.database.dao.TaskDao
import com.example.todoapp.database.entities.Task


@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDataBase : RoomDatabase(){

    abstract fun taskDao(): TaskDao

    companion object {  //Se usa un companion object porque unicamente necesitamos una unica instancia de la db, si se crean multiples instancias de la base de datos podria generar concurrencia y mal funcionamiento de la applicacion
        
        @Volatile // Asegura que todos los cambios sean visibles por todos los hilos
        private var INSTANCE: TaskDataBase? = null

        fun getDataBase(context: Context): TaskDataBase{
            return INSTANCE ?: synchronized(this) { // INSTANCE ?: es una especie de if , devuelve la instancia de la base de datos, en caso que no este creada la crea.
                val instance  = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDataBase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}