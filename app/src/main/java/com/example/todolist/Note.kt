package com.example.todolist

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var text: String,
    var priority: Int
) {
    @Ignore
    constructor(text: String, priority: Int) : this(0, text, priority)
}