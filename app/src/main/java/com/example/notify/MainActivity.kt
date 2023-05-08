package com.example.notify

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notify.Adapter.NotesAdapter
import com.example.notify.Database.NoteDatabase
import com.example.notify.Models.Note
import com.example.notify.Models.NoteViewModel
import com.example.notify.databinding.ActivityMainBinding


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() ,NotesAdapter.NotesClickedListener , PopupMenu.OnMenuItemClickListener{
    private lateinit var binding : ActivityMainBinding
    private lateinit var database: NoteDatabase
    private lateinit var adapter: NotesAdapter
    private lateinit var selectedNote : Note
    private lateinit var viewModel: NoteViewModel
    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result-> if(result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note") as? Note

        if(note!=null){
            viewModel.updateNote(note)
        }
    }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
      //initialize the UI
        initUI()

       viewModel = ViewModelProvider(this,
       ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

       viewModel.allnotes.observe(this){list->

           list?.let{
               adapter.updateList(list)
           }
       }
        database = NoteDatabase.getDatabase(this)

    }

    private fun initUI() {
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = NotesAdapter(this,this)
        binding.recycler.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> if(result.resultCode == Activity.RESULT_OK){
                val note = result.data?.getSerializableExtra("note")as? Note
                if (note != null){
                    viewModel.insertNote(note)
                }


        }
        }
        binding.fab.setOnClickListener {
            val intent = Intent(this,Note_Add_Activity::class.java)
            getContent.launch(intent)
        }

        binding.search.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 if(newText != null){
                     adapter.filter(newText)
            }
                return true
            }
            })



    }

    override fun onItemClicked(note: Note) {

        val intent = Intent(this@MainActivity,Note_Add_Activity::class.java)
        intent.putExtra("current_note",note )
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
       selectedNote = note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView){
        val popUp = PopupMenu(this,cardView)
        popUp.setOnMenuItemClickListener(this@MainActivity)
        popUp.inflate(R.menu.pop_up_menu)
        popUp.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.menu){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}