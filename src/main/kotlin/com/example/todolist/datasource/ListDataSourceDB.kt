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


    override fun getLists(sort:String?): ResponseEntity<kotlin.collections.List<List>> {
        return when (sort){
            "order" -> {
                ResponseEntity.ok(this.listRepository.findAll(Sort.by(Sort.Order.asc("order"))))
            }
            "deliveryDate" -> {
                ResponseEntity.ok(this.listRepository.findAll(Sort.by(Sort.Order.asc("deliveryDate"))))
            }
            else -> {
                ResponseEntity.ok(this.listRepository.findAll(Sort.by(Sort.Order.asc("createdAt"))))
            }
        }
    }

    override fun getList(id: String): ResponseEntity<List> = ResponseEntity.ok(this.listRepository.findById(id).orElse(null))

    override fun addList(list: List): ResponseEntity<List> {
        if (this.listRepository.existsById(list.id)) {
            throw IllegalArgumentException("List with ID ${list.id} already exists.")
        }

        val listBody = this.listRepository.findAll(Sort.by(Sort.Order.asc("order"))).lastOrNull()
        if (listBody != null){
            val order = listBody.order
            list.order = order+1
        }
        else{
            list.order = 1
        }

        return ResponseEntity.ok(this.listRepository.save(list))
    }

    override fun getToDos(listId: String,id:String?): ResponseEntity<kotlin.collections.List<ToDo>> {
        val list = this.listRepository.findById(listId).orElse(null)
        if (id!=null){
            val item = list.items.find { it.id == id }
                    ?:throw NoSuchElementException("No item found with such ID: $id")
                    return ResponseEntity.ok(listOf(item))
        }
       return ResponseEntity.ok(list.items)
    }
    override fun addToDo(listId: String,toDo: ToDo): ResponseEntity<List> {
        val list = this.listRepository.findById(listId).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${listId} to add it's items!)")
        for (item in list.items){
            if (item.id == toDo.id){
                throw IllegalArgumentException("Item with ID ${toDo.id} already exists.")
            }
        }
        toDo.isChecked = false
        list.items.add(toDo)
        return ResponseEntity.ok(this.listRepository.save(list))
    }

    override fun updateToDo(toDo: ToDo, listId: String, id: String?): ResponseEntity<List> {
        val oldList = this.listRepository.findById(listId).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${listId} to be updated!)")
        return if (id!=null){
            val item = oldList.items.find { it.id == id }
                    ?:throw NoSuchElementException("No item found with such ID: $id")
            toDo.text.takeIf { it.isNotEmpty() }?.let { item.text = it }
            toDo.isChecked?.let { item.isChecked = it }
            ResponseEntity.ok(this.listRepository.save(oldList))
        }
        else{
            for (item in oldList.items){
                toDo.text.takeIf {it.isNotEmpty()}?.let{item.text = it}
                toDo.isChecked?.let { item.isChecked = it }
            }
            ResponseEntity.ok(this.listRepository.save(oldList))
        }

    }

    override fun deleteToDo(listId: String, id: String?): ResponseEntity<List> {
        val list = this.listRepository.findById(listId).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${listId} to delete it's items!)")

        return if(id == null){
            list.items.clear()
            ResponseEntity.ok(this.listRepository.save(list))
        }
        else{
            val item = list.items.find { it.id == id }
            if (item != null){
                list.items.remove(item)
            }
            else{
                throw NoSuchElementException("Item with this id not found: $id")
            }
            ResponseEntity.ok(this.listRepository.save(list))
        }
    }

    override fun updateList(id:String,list: List): ResponseEntity<List> {
        val oldList = this.listRepository.findById(id).orElse(null)
                ?: throw NoSuchElementException("No list with id number: (${list.id} to be updated!)")
        list.deliveryDate?.let { oldList.deliveryDate = it }
        list.order.takeIf {it != 0}?.let{ oldList.order = it }
        list.text.takeIf { it.isNotEmpty() }?.let { oldList.text = it }
        return ResponseEntity.ok(this.listRepository.save(oldList))
    }

    override fun deleteList(id: String?) : ResponseEntity<Unit> {
        return if (id != null){
            this.listRepository.findById(id).orElse(null)
                    ?: throw NoSuchElementException("No list with id number: (${id} to be deleted!)")
            ResponseEntity.ok(this.listRepository.deleteById(id))
        }
        else{
            return ResponseEntity.ok(this.listRepository.deleteAll())
        }
    }
}