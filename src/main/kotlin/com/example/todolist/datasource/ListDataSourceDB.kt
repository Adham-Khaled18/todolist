package com.example.todolist.datasource

import com.example.todolist.model.List
import com.example.todolist.model.ToDo
import com.example.todolist.repository.ListRepo
import com.example.todolist.repository.ToDoRepo
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository


@Repository
class ListDataSourceDB (private val listRepository:ListRepo, private val toDoRepository:ToDoRepo):ListDataSource {


    override fun getLists(): ResponseEntity<kotlin.collections.List<List>> = ResponseEntity.ok(this.listRepository.findAll(Sort.by(Sort.Order.asc("createdAt"))))
    override fun getListsOrder(): ResponseEntity<kotlin.collections.List<List>> = ResponseEntity.ok(this.listRepository.findAll(Sort.by(Sort.Order.asc("order"))))

    override fun getListsDelivery(): ResponseEntity<kotlin.collections.List<List>> = ResponseEntity.ok(this.listRepository.findAll(Sort.by(Sort.Order.asc("deliveryDate"))))

    override fun getList(id: String): ResponseEntity<List> = ResponseEntity.ok(this.listRepository.findById(id).orElse(null))

    override fun addList(list: List): ResponseEntity<List> {
        if (this.listRepository.existsById(list.id)) {
            throw IllegalArgumentException("List with ID ${list.id} already exists.")
        }
        return ResponseEntity.ok(this.listRepository.save(list))
    }

    override fun getToDos(listId: String): ResponseEntity<kotlin.collections.List<ToDo>> {
        val items = this.listRepository.findById(listId).orElse(null).items
       return ResponseEntity.ok(items)
    }
    override fun addToDo(toDo: ToDo): ResponseEntity<List> {
        val list = this.listRepository.findById(toDo.listId).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${toDo.listId} to add it's items!)")
        list.items.add(toDo)
        return ResponseEntity.ok(this.listRepository.save(list))
    }

    override fun updateToDo(toDo: ToDo): ResponseEntity<List> {
        val oldList = this.listRepository.findById(toDo.listId).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${toDo.listId} to be updated!)")
        oldList.items = mutableListOf(toDo)
        return ResponseEntity.ok(this.listRepository.save(oldList))
    }

    override fun deleteToDo(listId: String, id: String): ResponseEntity<List> {
        val list = this.listRepository.findById(listId).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${listId} to delete it's items!)")
        val item = list.items.find { it.id == id }
        if (item != null){
            list.items.remove(item)
        }
        else{
            throw NoSuchElementException("Item with this id not found: $id")
        }
        return ResponseEntity.ok(this.listRepository.save(list))
    }

    override fun updateList(list: List): ResponseEntity<List> {
        val oldList = this.listRepository.findById(list.id).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${list.id} to be updated!)")
        oldList.id = list.id
        oldList.createdAt = list .createdAt
        oldList.deliveryDate = list.deliveryDate
        oldList.order = list.order
        oldList.text = list.text
        return ResponseEntity.ok(this.listRepository.save(list))
    }

    override fun deleteList(id: String) : ResponseEntity<Unit> {
        this.listRepository.findById(id).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${id} to be deleted!)")
        return  ResponseEntity.ok(this.listRepository.deleteById(id))
    }
}