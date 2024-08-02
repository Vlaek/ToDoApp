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

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateOrEditTaskBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(RetrofitInstance.api)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskId = intent.getLongExtra("TASK_ID", -1)
        val taskTitle = intent.getStringExtra("TASK_TITLE")
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION")
        val taskCompleted = intent.getBooleanExtra("TASK_COMPLETED", false)

        if (taskId == -1L) {
            Toast.makeText(this, "Ошибка загрузки задачи", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.taskTitleEditText.setText(taskTitle)
        binding.taskDescriptionEditText.setText(taskDescription)
        binding.taskCompletedCheckbox.isChecked = taskCompleted

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

            val updatedTask = Task(
                id = taskId,
                title = title,
                description = description,
                completed = binding.taskCompletedCheckbox.isChecked
            )
            taskViewModel.updateTask(taskId, updatedTask)

            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}