package com.example.notify.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notify.Models.Note
import com.example.notify.R
import kotlin.random.Random

class NotesAdapter (private val context : Context,val listner : NotesClickedListener) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    private val NotesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       return NoteViewHolder(
           LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
       )
    }

    override fun getItemCount(): Int {
       return NotesList.size
    }


    fun updateList(newList : List<Note>){
        fullList.clear()
        fullList.addAll(newList)

        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()

    }
    fun filter(search : String){
        NotesList.clear()
        for(item in fullList){
            if(item.title?.lowercase()?.contains(search.lowercase()) == true || item.description?.lowercase()?.contains(search.lowercase()) == true){
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val currentNote = NotesList[position]
        holder.title.text = currentNote.title
        holder.date.text = currentNote.date
        holder.title.isSelected = true
        holder.date.isSelected = true
        holder.note.text = currentNote.description

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(),null))

        holder.notes_layout.setOnClickListener {
            listner.onItemClicked(NotesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener{
            listner.onLongItemClicked((NotesList[holder.adapterPosition]),holder.notes_layout)
            true
        }
    }

    fun randomColor(): Int {
          val list = ArrayList<Int>()
        list.add(R.color.Notes1)
        list.add(R.color.Notes2)
        list.add(R.color.Notes3)
        list.add(R.color.Notes4)
        list.add(R.color.Notes5)
        list.add(R.color.Notes6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)   // random color will be assigned to the notes background on which text will be displayed
        return list[randomIndex]



    }
    inner class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

            val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
            val title = itemView.findViewById<TextView>(R.id.tv_title)
            val note = itemView.findViewById<TextView>(R.id.tv_note)
            val date = itemView.findViewById<TextView>(R.id.tv_date)
    }

    interface NotesClickedListener{
        fun onItemClicked(note : Note)
        fun onLongItemClicked(note: Note, cardView : CardView)

    }
}