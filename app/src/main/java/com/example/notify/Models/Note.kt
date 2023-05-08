package com.example.notify.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_tables")

data class Note(
    @PrimaryKey(autoGenerate = true)val id : Int?,
    @ColumnInfo(name = "Date") val date : String?,
    @ColumnInfo(name = "Description") val description : String?,
    @ColumnInfo(name = "Title") val title : String?

)  : java.io.Serializable
