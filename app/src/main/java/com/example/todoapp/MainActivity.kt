package com.example.todoapp


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddTask: FloatingActionButton
    private lateinit var adapter: TaskAdapter

    // Grab a reference to our Room database
    private val database: TaskDatabase by lazy{ TaskDatabase.getDatabase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Link the UI
        recyclerView = findViewById(R.id.recyclerViewTasks)
        fabAddTask = findViewById(R.id.fabAddTask)

        // 2. Setup the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter and define what happens when the delete button is clicked
        adapter = TaskAdapter(emptyList()) { taskToDelete ->
            // Delete the task in a background thread
            lifecycleScope.launch(Dispatchers.IO) {
                database.taskDao().deleteTask(taskToDelete)
                launch(Dispatchers.Main) {
                    // We anchor the Snackbar to the recyclerView
                    Snackbar.make(recyclerView, "Task Deleted", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        recyclerView.adapter = adapter

        // 3. Observe the database (The Magic Part)
        // LiveData automatically triggers this block whenever a task is added or deleted
        database.taskDao().getAllTasks().observe(this, Observer { tasks ->
            adapter.updateTasks(tasks)
        })

        // 4. Handle the "+" button click
        fabAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}