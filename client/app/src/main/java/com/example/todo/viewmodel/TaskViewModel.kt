package com.example.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.network.Task
import com.example.todo.network.TaskApi
import kotlinx.coroutines.launch
import retrofit2.Response

class TaskViewModel(private val taskApi: TaskApi) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    private val _taskCreation = MutableLiveData<Task>()
    val taskCreation: LiveData<Task> get() = _taskCreation

    private val _taskUpdate = MutableLiveData<Task>()
    val taskUpdate: LiveData<Task> get() = _taskUpdate

    private val _taskDeletion = MutableLiveData<Boolean>()
    val taskDeletion: LiveData<Boolean> get() = _taskDeletion

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchTasks() {
        viewModelScope.launch {
            try {
                val response: Response<List<Task>> = taskApi.getTasks()
                if (response.isSuccessful) {
                    _tasks.value = response.body()
                } else {
                    _error.value = "Error fetching tasks: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception fetching tasks: ${e.message}"
            }
        }
    }

    fun createTask(task: Task) {
        viewModelScope.launch {
            try {
                val response: Response<Task> = taskApi.createTask(task)
                if (response.isSuccessful) {
                    fetchTasks()
                } else {
                    _error.value = "Error creating task: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception creating task: ${e.message}"
            }
        }
    }

    fun updateTask(id: Long, task: Task) {
        viewModelScope.launch {
            try {
                val response: Response<Task> = taskApi.updateTask(id, task)
                if (response.isSuccessful) {
                    _taskUpdate.value = response.body()
                    fetchTasks()
                } else {
                    _error.value = "Error updating task: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception updating task: ${e.message}"
            }
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            try {
                val response: Response<Void> = taskApi.deleteTask(id)
                if (response.isSuccessful) {
                    _taskDeletion.value = true
                    fetchTasks()
                } else {
                    _taskDeletion.value = false
                    _error.value = "Error deleting task: ${response.message()}"
                }
            } catch (e: Exception) {
                _taskDeletion.value = false
                _error.value = "Exception deleting task: ${e.message}"
            }
        }
    }
}