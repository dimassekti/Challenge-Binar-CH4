package com.example.challengechapterfour.note

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun insertNote(note: Note) : Long

    @Query("SELECT * FROM Note")
    fun getAllNote() : List<Note>

    @Delete
    fun deleteNote(note: Note) : Int

    @Update
    fun updateNote(note: Note) : Int



}

