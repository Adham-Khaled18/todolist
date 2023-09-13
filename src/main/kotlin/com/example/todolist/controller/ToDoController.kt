package com.example.todolist.controller

import com.example.todolist.model.List
import com.example.todolist.model.ToDo
import com.example.todolist.service.ListService
import com.example.todolist.service.ToDoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/todo")
class ToDoController(private val listService:ListService , private val toDoService: ToDoService ) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String> =
            ResponseEntity(e.message,HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e:IllegalArgumentException):ResponseEntity<String> =
            ResponseEntity(e.message,HttpStatus.BAD_REQUEST)

    @GetMapping
    fun returnToDoLists() : ResponseEntity<kotlin.collections.List<List>> = listService.getLists()

    @GetMapping("/order")
    fun returnToDoListsOrder() : ResponseEntity<kotlin.collections.List<List>> = listService.getListsOrder()

    @GetMapping("/delivery")
    fun returnToDoListsDelivery() : ResponseEntity<kotlin.collections.List<List>> = listService.getListsDelivery()

    @GetMapping("/{id}")
    fun returnOneList(@PathVariable id : String) : ResponseEntity<List> = listService.getList(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addList(@RequestBody list:List):ResponseEntity<List> = listService.addList(list) // change later

    @PatchMapping
    fun updateList(@RequestBody list:List):ResponseEntity<List> = listService.updateList(list)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteList(@PathVariable id:String):ResponseEntity<Unit> = listService.deleteList(id)

    @GetMapping("/items/{listId}")
    fun returnToDos(@PathVariable listId:String) : ResponseEntity<kotlin.collections.List<ToDo>> = toDoService.getToDos(listId)

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToDo(@RequestBody toDo: ToDo): ResponseEntity<List> = toDoService.addToDo(toDo) // change later

    @PatchMapping("/items")
    fun updateToDo(@RequestBody toDo:ToDo): ResponseEntity<List> = toDoService.updateToDo(toDo)

    @DeleteMapping("/items/{id}")
    fun deleteToDo(@PathVariable listId: String,id:String): ResponseEntity<List> = toDoService.deleteToDo(listId,id)
}