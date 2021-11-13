package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.viewmodels.ToDoViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ToDoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    TodoActivityScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun TodoActivityScreen(viewModel: ToDoViewModel) {

  //  val items: List<ToDoItem> by viewModel.todoItems.observeAsState(listOf())

    ToDoScreen(
        items = viewModel.todoItems,
        currentlyEditing = viewModel.currentEditItem,
        onAdd = viewModel::addItem,
        onRemove = viewModel::removeItem,
        onStartEditing = viewModel::onEditItemSelected,
        onEditItemChange = viewModel::onEditItemChange,
        onEditDone = viewModel::onEditDone
    )
}
