package com.example.todolist.service

import com.example.todolist.datasource.ListDataSource
import com.example.todolist.model.ToDo
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ToDoService(private val dataSource: ListDataSource) {
    fun getToDos(listId:String,id:String?): ResponseEntity<List<ToDo>> = dataSource.getToDos(listId,id)
    fun addToDo(listId:String,toDo : ToDo): ResponseEntity<com.example.todolist.model.List> = dataSource.addToDo(listId,toDo) // change later
    fun updateToDo(toDo: ToDo,listId: String,id: String?): ResponseEntity<com.example.todolist.model.List> = dataSource.updateToDo(toDo,listId,id)
    fun deleteToDo(listId:String,id:String?): ResponseEntity<com.example.todolist.model.List> = dataSource.deleteToDo(listId,id)
}