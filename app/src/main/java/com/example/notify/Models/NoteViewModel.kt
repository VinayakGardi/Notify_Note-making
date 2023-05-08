package com.example.notify.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

import com.example.notify.Database.NoteDatabase
import com.example.notify.Database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application)  : AndroidViewModel(application){
    private val repository : NoteRepository

    val allnotes : LiveData<List<Note>>
    init{
        val dao = NoteDatabase.getDatabase(application).getNotedao()
        repository = NoteRepository(dao)
        allnotes=repository.allNotes
    }

    fun deleteNote(note : Note)= viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }

    fun insertNote(note : Note)= viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }

    fun updateNote(note : Note)= viewModelScope.launch(Dispatchers.IO){
        repository.update(note)
    }



}