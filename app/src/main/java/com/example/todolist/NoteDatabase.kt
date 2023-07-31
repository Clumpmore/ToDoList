package com.example.todolist

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        var db: NoteDatabase? = null
        private const val DB_NAME = "main.db"

        fun getInstance(application: Application): NoteDatabase {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    application,
                    NoteDatabase::class.java,
                    DB_NAME
                )
                    .build()
                db = instance
                return instance
            }
        }

    abstract fun notesDao():NotesDao
}