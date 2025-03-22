package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.todoapp.database.dao.TaskDao
import com.example.todoapp.database.db.TaskDataBase
import com.example.todoapp.database.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDataBase(app: Application): TaskDataBase {
        return Room.databaseBuilder(app, TaskDataBase::class.java, "task_db").build()
    }

    @Provides
    fun providesDao(dataBase: TaskDataBase): TaskDao {
        return dataBase.taskDao()
    }

    @Provides
    fun providesTaskRepository(dao: TaskDao): TaskRepository {
        return TaskRepository(dao)
    }
}
