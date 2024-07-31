package com.example.todo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivityCreateOrEditTaskBinding
import com.example.todo.network.RetrofitInstance
import com.example.todo.network.Task
import com.example.todo.viewmodel.TaskViewModel
import com.example.todo.viewmodel.TaskViewModelFactory

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateOrEditTaskBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(RetrofitInstance.api)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val task = Task(
                id = 0,
                title = binding.taskTitleEditText.text.toString(),
                description = binding.taskDescriptionEditText.text.toString(),
                completed = binding.taskCompletedCheckbox.isChecked
            )
            taskViewModel.createTask(task)

            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}