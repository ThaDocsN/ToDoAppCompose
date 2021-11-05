package com.example.todoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.ToDoItem

class ToDoViewModel: ViewModel() {
    private val _todoItems = MutableLiveData(listOf<ToDoItem>())
    val todoItems : LiveData<List<ToDoItem>> = _todoItems

    fun addItem(item: ToDoItem){
        // TODO: 11/5/21  
    }

    fun removeItem(item: ToDoItem) {
        // TODO: 11/5/21
    }
}