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

    fun fetchTasks() {
        viewModelScope.launch {
            try {
                val response: Response<List<Task>> = taskApi.getTasks()
                if (response.isSuccessful) {
                    _tasks.value = response.body()
                }
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun createTask(task: Task) {
        viewModelScope.launch {
            try {
                val response: Response<Task> = taskApi.createTask(task)
                if (response.isSuccessful) {
                    _taskCreation.value = response.body()
                }
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun updateTask(id: Long, task: Task) {
        viewModelScope.launch {
            try {
                val response: Response<Task> = taskApi.updateTask(id, task)
                if (response.isSuccessful) {
                    _taskUpdate.value = response.body()
                    fetchTasks();
                }
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            try {
                val response: Response<Void> = taskApi.deleteTask(id)
                _taskDeletion.value = response.isSuccessful
                fetchTasks();
            } catch (e: Exception) {
                // Handle the exception
                _taskDeletion.value = false
            }
        }
    }
}