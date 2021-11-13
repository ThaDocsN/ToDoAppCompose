package com.example.todoapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.ToDoItem

class ToDoViewModel: ViewModel() {
//    private val _todoItems = MutableLiveData(listOf<ToDoItem>())
//    val todoItems : LiveData<List<ToDoItem>> = _todoItems

    private var currentEditPosition by mutableStateOf(-1)

    val currentEditItem: ToDoItem?
        get() = todoItems.getOrNull(currentEditPosition)

    var todoItems = mutableStateListOf<ToDoItem>()
        private set

    fun onEditItemSelected(item: ToDoItem) {
        currentEditPosition = todoItems.indexOf(item)
    }

    fun onEditDone(){
        currentEditPosition = -1
    }

    fun onEditItemChange(item: ToDoItem){
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        todoItems[currentEditPosition] = item
    }

    fun addItem(item: ToDoItem){
        todoItems.add(item)
    }

    fun removeItem(item: ToDoItem) {
        todoItems.remove(item)
        onEditDone()
    }
}