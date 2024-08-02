package com.example.todo.ui

import android.os.Bundle
import android.widget.Toast
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
            val title = binding.taskTitleEditText.text.toString()
            val description = binding.taskDescriptionEditText.text.toString()

            if (title.isBlank()) {
                Toast.makeText(this, "Заполните название", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (description.isBlank()) {
                Toast.makeText(this, "Заполните описание", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(
                id = 0,
                title = title,
                description = description,
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