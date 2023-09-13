package com.example.todolist.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import kotlin.collections.List

@Document
class List {
    @Id
    var id = ""
    var text = ""

    @CreatedDate
    var createdAt: Instant= Instant.now()

    var deliveryDate: LocalDate? = null
    var order = 0
    var items : MutableList<ToDo> = ArrayList()
}