package com.example.todolist.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
class ToDo {
    var id = ""
    var text = ""
    var isChecked = false
    var listId = ""
}