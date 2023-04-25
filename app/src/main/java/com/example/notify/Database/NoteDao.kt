package com.example.notify.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notify.Models.Note

@Dao

interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note : Note)

    @Delete
    suspend fun delete(note : Note)

     @Query("Select * from notes_tables Order BY Id Asc")
     fun getAllNotes() : LiveData<List<Note>>

     @Query("UPDATE notes_tables Set title=:Title , Description =:Note WHERE id = :Id")
     suspend fun update(Id: Int?,Title : String?,Note: String?)
}