package com.example.todolist.service

import com.example.todolist.datasource.ListDataSource
import com.example.todolist.model.List
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class ListService(private val dataSource: ListDataSource) {
    fun getLists(sort:String?): ResponseEntity<kotlin.collections.List<List>> = dataSource.getLists(sort)
    fun getList(id:String): ResponseEntity<List> = dataSource.getList(id)
    fun addList(list : List): ResponseEntity<List> = dataSource.addList(list) // change later
    fun updateList(id:String,list: List): ResponseEntity<List> = dataSource.updateList(id,list)
    fun deleteList(id:String):ResponseEntity<Unit> = dataSource.deleteList(id)
}