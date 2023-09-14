package com.example.todolist.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class ToDo {
    @Id
    var id = ""
    var text = ""
    var isChecked : Boolean?= null
}