package com.example.todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.network.OnTaskClickListener
import com.example.todo.network.RetrofitInstance
import com.example.todo.network.Task
import com.example.todo.network.TaskAdapter
import com.example.todo.ui.CreateTaskActivity
import com.example.todo.ui.EditTaskActivity
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

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        taskViewModel.fetchTasks()
    }

    override fun onDeleteClick(taskId: Long) {
        taskViewModel.deleteTask(taskId)
    }

    override fun onChangeStatusClick(taskId: Long, task: Task) {
        val updatedTask = task.copy(completed = !task.completed)
        taskViewModel.updateTask(taskId, updatedTask)
    }

    override fun onUpdateClick(taskId: Long, task: Task) {
        val intent = Intent(this, EditTaskActivity::class.java).apply {
            putExtra("TASK_ID", task.id)
            putExtra("TASK_TITLE", task.title)
            putExtra("TASK_DESCRIPTION", task.description)
            putExtra("TASK_COMPLETED", task.completed)
        }
        startActivity(intent)
    }
}