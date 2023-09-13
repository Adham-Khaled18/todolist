package com.example.todolist.datasource

import com.example.todolist.model.List
import com.example.todolist.model.ToDo
import org.springframework.http.ResponseEntity

interface ListDataSource {

    fun getLists(): ResponseEntity<kotlin.collections.List<List>>
    fun getListsOrder(): ResponseEntity<kotlin.collections.List<List>>
    fun getListsDelivery(): ResponseEntity<kotlin.collections.List<List>>
    fun getList(id:String):ResponseEntity<List>
    fun addList(list : List):ResponseEntity<List>
    fun updateList(list:List):ResponseEntity<List>
    fun deleteList(id:String):ResponseEntity<Unit>
    fun getToDos(listId: String): ResponseEntity<kotlin.collections.List<ToDo>>
    fun addToDo(toDo:ToDo):ResponseEntity<List>
    fun updateToDo(toDo: ToDo):ResponseEntity<List>
    fun deleteToDo(listId:String,id:String):ResponseEntity<List>
}