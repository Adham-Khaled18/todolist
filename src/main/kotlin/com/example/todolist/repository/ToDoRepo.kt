package com.example.todolist.repository

import com.example.todolist.model.ToDo
import org.springframework.data.mongodb.repository.MongoRepository

interface ToDoRepo :MongoRepository<ToDo,String>{
}