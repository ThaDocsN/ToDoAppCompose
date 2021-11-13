package com.example.todoapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun ToDoScreen(
    items: List<ToDoItem>,
    currentlyEditing: ToDoItem?,
    onAdd: (ToDoItem) -> Unit,
    onRemove: (ToDoItem) -> Unit,
    onStartEditing: (ToDoItem) -> Unit,
    onEditItemChange: (ToDoItem) -> Unit,
    onEditDone: () -> Unit
) {

    Column {
        val enableTopSection = currentlyEditing == null

        TodoItemInputBackground(elevate = enableTopSection) {

            if (enableTopSection) {
                TodoItemEntryInput(onItemComplete = onAdd)
            } else {
                Text(
                    "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) { todo ->
                if (currentlyEditing?.id == todo.id){
                    ToDoItemInlineEditor(
                        item = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemove = {onRemove(todo)}
                    )
                }else TodoRow(
                    todo,
                    { onStartEditing(it) },
                    Modifier.fillParentMaxWidth()
                )
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

/**
 * @param item: a to do item
 * @param onItemClicked: function item clicked
 * @param modifier
 */
@Composable
fun TodoRow(
    item: ToDoItem,
    onItemClicked: (ToDoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha: Float = remember(item.id) { randomTint() }
) {

    Row(modifier = modifier
        .clickable { onItemClicked(item) }
        .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Text(text = item.task)

        Icon(
            imageVector = item.icon.imageVector,
            tint = LocalContentColor.current.copy(iconAlpha),
            contentDescription = stringResource(id = item.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Composable
fun ToDoItemInlineEditor(
    item: ToDoItem,
    onEditItemChange: (ToDoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemove: (ToDoItem) -> Unit
) = ToDoItemInput(text = item.task, onTextChanged = {onEditItemChange(item.copy(task = it))}, icon = item.icon, onIconChanged = {onEditItemChange(item.copy(icon = it))}, iconVisible = true, onEditDone, buttonSlot = {
    Row {
        val shrinkButtons = Modifier.widthIn(20.dp)
        TextButton(onClick = onEditDone, modifier = shrinkButtons) {
            Text(
                text = "\uD83D\uDCBE", // floppy disk
                textAlign = TextAlign.End,
                modifier = Modifier.width(30.dp)
            )
        }
        TextButton(onClick = {onRemove}, modifier = shrinkButtons) {
            Text(
                text = "❌",
                textAlign = TextAlign.End,
                modifier = Modifier.width(30.dp)
            )
        }
    }
})

@Composable
fun ToDoInputTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier
) {

    TodoInputText(text, onTextChange, modifier)
}

@Composable
fun TodoItemEntryInput(onItemComplete: (ToDoItem) -> Unit) {

    val (text, onTextChange) = remember {
        mutableStateOf("")
    }

    val (icon, onIconChange) = remember {
        mutableStateOf(TodoIcon.Default)
    }

    val iconVisible = text.isNotBlank()
    val submit = {
        if (text.isNotBlank()) {
            onItemComplete(ToDoItem(text, icon))
            onIconChange(TodoIcon.Default)
            onTextChange("")
        }
    }
    ToDoItemInput(
        text = text,
        onTextChanged = onTextChange,
        icon = icon,
        onIconChanged = onIconChange,
        iconVisible = iconVisible,
        submit = submit
    ){
        TodoEditButton(onClick = submit, text = "Add", enabled = text.isNotBlank())
    }
}

@Composable
fun ToDoItemInput(
    text: String,
    onTextChanged: (String) -> Unit,
    icon: TodoIcon,
    onIconChanged: (TodoIcon) -> Unit,
    iconVisible: Boolean,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {

            TodoInputText(
                text,
                onTextChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier.align(Alignment.CenterVertically)){buttonSlot()}
            /*TodoEditButton(
                onClick = submit,
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank()
            )*/
        }
        if (iconVisible) {
            AnimatedIconRow(
                icon = icon,
                onIconChange = onIconChanged,
                Modifier.padding(top = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        ToDoItem("Learn compose", TodoIcon.Event),
        ToDoItem("Take the code lab"),
        ToDoItem("Apply state", TodoIcon.Done),
        ToDoItem("Build dynamic UIs", TodoIcon.Square)
    )
    ToDoScreen(items, null, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(item = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}

@Preview
@Composable
fun PreviewTodoItemInput() = TodoItemEntryInput(onItemComplete = { })