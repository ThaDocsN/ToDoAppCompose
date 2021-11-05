package com.example.todoapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun ToDoScreen(
    items: List<ToDoItem>,
    onAdd: (ToDoItem) -> Unit,
    onRemove: (ToDoItem) -> Unit
){

    Column {
        LazyColumn(modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)){
            items(items = items){ it ->
                TodoRow(item = it, onItemClicked = {onRemove(it)}, Modifier.fillParentMaxWidth())
            }
        }
        Button(
            onClick = { onAdd(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text("Add random item")
        }
    }
}
/*
* @param item: a to do item
* @param onItemClicked: function item clicked
* @param modifier*/
@Composable
fun TodoRow(item: ToDoItem, onItemClicked: (ToDoItem) -> Unit, modifier: Modifier = Modifier){

    Row(modifier = modifier
        .clickable { onItemClicked(item) }
        .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = item.task)
        Icon(imageVector = item.icon.imageVector,
            contentDescription = stringResource(id = item.icon.contentDescription))
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        ToDoItem("Learn compose", ToDoIcon.Event),
        ToDoItem("Take the codelab"),
        ToDoItem("Apply state", ToDoIcon.Done),
        ToDoItem("Build dynamic UIs", ToDoIcon.Square)
    )
    ToDoScreen(items, {}, {})
}

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(item = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}
