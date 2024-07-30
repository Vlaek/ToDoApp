package com.example.todo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.network.OnTaskClickListener
import com.example.todo.network.RetrofitInstance
import com.example.todo.network.Task
import com.example.todo.network.TaskAdapter
import com.example.todo.viewmodel.TaskViewModel
import com.example.todo.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity(), OnTaskClickListener {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(RetrofitInstance.api)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TaskAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        taskViewModel.tasks.observe(this) { tasks ->
            tasks?.let { adapter.submitList(it) }
        }

        taskViewModel.fetchTasks()
    }

    override fun onDeleteClick(taskId: Long) {
        taskViewModel.deleteTask(taskId);
    }

    override fun onUpdateClick(taskId: Long, task: Task) {
        val updatedTask = task.copy(completed = !task.completed)
        taskViewModel.updateTask(taskId, updatedTask)
    }
}