package com.example.todoapp

import android.app.ActivityManager
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)val id: Int=0,

    val taskName: String,

    val taskDescription: String
)