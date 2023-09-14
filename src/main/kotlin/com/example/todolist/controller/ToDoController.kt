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
    fun returnToDoLists(@RequestParam(required = false)sort : String?) : ResponseEntity<kotlin.collections.List<List>> = listService.getLists(sort)

    @GetMapping("/{id}")
    fun returnOneList(@PathVariable id : String) : ResponseEntity<List> = listService.getList(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addList(@RequestBody list:List):ResponseEntity<List> = listService.addList(list) // change later

    @PatchMapping("/{id}")
    fun updateList(@PathVariable id:String, @RequestBody list:List):ResponseEntity<List> = listService.updateList(id,list)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteList(@PathVariable id:String):ResponseEntity<Unit> = listService.deleteList(id)

    @GetMapping("{listId}/items/{id}")
    fun returnToDos(@PathVariable listId:String , @PathVariable(required = false) id: String?) : ResponseEntity<kotlin.collections.List<ToDo>> = toDoService.getToDos(listId,id)

    @PostMapping("{listId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToDo(@PathVariable listId: String,@RequestBody toDo: ToDo): ResponseEntity<List> = toDoService.addToDo(listId,toDo) // change later

    @PatchMapping("{listId}/items/{id}")
    fun updateToDo(@RequestBody toDo:ToDo,@PathVariable listId: String , @PathVariable(required = false) id: String?): ResponseEntity<List> = toDoService.updateToDo(toDo,listId,id)

    @DeleteMapping("{listId}/items/{id}")
    fun deleteToDo(@PathVariable listId: String,@PathVariable(required = false) id:String?): ResponseEntity<List> = toDoService.deleteToDo(listId,id)
}