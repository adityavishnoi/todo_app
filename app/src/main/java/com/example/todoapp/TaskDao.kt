package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
@Insert
suspend fun insertTask(task: Task)

@Delete
suspend fun deleteTask(task: Task)

@Query("SELECT * FROM task_table ORDER BY id ASC")
fun getAllTasks(): LiveData<List<Task>>
}