package com.example.notify

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import com.example.notify.Models.Note
import com.example.notify.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.logging.SimpleFormatter

class Note_Add_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var old_note : Note
    private lateinit var note : Note
    var isUpdate = false

    val note_title :EditText = findViewById(R.id.note_title)
    val search : SearchView = findViewById(R.id.search)
    val note_des : EditText = findViewById(R.id.note_)
    val imgsave : ImageView = findViewById(R.id.ig_save)
    val imgback : ImageView = findViewById(R.id.ig_back)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try{
            old_note = intent.getSerializableExtra("current_note") as Note
            note_title.setText(old_note.title)
            note_des.setText(old_note.description)
            isUpdate=true

        }catch (e :Exception){
             e.printStackTrace()
        }

         imgsave.setOnClickListener {
             val title = note_title.text.toString()
             val note_des = note_des.text.toString()
             if(title.isNotEmpty() || note_des.isNotEmpty()){
                 val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
                 if(isUpdate){
                     note = Note(
                         old_note.id,title,note_des,formatter.format(Date())
                     )
                 }
                 else{
                     note = Note(
                         null,title,note_des,formatter.format(Date())
                     )
                 }

                 val intent = Intent()
                 intent.putExtra("note",note)
                 setResult(Activity.RESULT_OK,intent)
                 finish()
             }
             else
             {
                 Toast.makeText(this@Note_Add_Activity,"Please Entter some data",Toast.LENGTH_SHORT).show()
                 return@setOnClickListener
             }
         }
        imgback.setOnClickListener {
            onBackPressed()
        }
    }
}