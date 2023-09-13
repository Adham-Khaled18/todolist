package com.example.todolist.repository

import com.example.todolist.model.List
import org.springframework.data.mongodb.repository.MongoRepository

interface ListRepo:MongoRepository<List,String> {
}