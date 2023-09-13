package com.example.todolist.service

import com.example.todolist.datasource.ListDataSource
import com.example.todolist.model.List
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class ListService(private val dataSource: ListDataSource) {
    fun getLists(): ResponseEntity<kotlin.collections.List<List>> = dataSource.getLists()
    fun getListsOrder(): ResponseEntity<kotlin.collections.List<List>> = dataSource.getListsOrder()
    fun getListsDelivery(): ResponseEntity<kotlin.collections.List<List>> = dataSource.getListsDelivery()
    fun getList(id:String): ResponseEntity<List> = dataSource.getList(id)
    fun addList(list : List): ResponseEntity<List> = dataSource.addList(list) // change later
    fun updateList(list: List): ResponseEntity<List> = dataSource.updateList(list)
    fun deleteList(id:String):ResponseEntity<Unit> = dataSource.deleteList(id)
}