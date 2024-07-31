package com.example.todo.network

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ItemTaskBinding

interface OnTaskClickListener {
    fun onDeleteClick(taskId: Long)
    fun onChangeStatusClick(taskId: Long, task: Task)
    fun onUpdateClick(taskId: Long, task: Task)
}

class TaskAdapter(private val listener: OnTaskClickListener) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, listener)
    }

    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, listener: OnTaskClickListener) {
            binding.task = task

            binding.deleteButton.setOnClickListener {
                listener.onDeleteClick(task.id)
            }

            binding.changeStatusButton.setOnClickListener {
                listener.onChangeStatusClick(task.id, task)
            }

            binding.updateButton.setOnClickListener {
                listener.onUpdateClick(task.id, task)
            }
        }
    }

    private class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}