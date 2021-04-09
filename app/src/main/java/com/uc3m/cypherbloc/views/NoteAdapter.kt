package com.uc3m.cypherbloc.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.uc3m.cypherbloc.databinding.FragmentSecondBinding
import com.uc3m.cypherbloc.databinding.RecyclerViewItemBinding
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.EnumSet.of
import java.util.List.of

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

    private var notesList = emptyList<Notes>()
    private lateinit var auth: FirebaseAuth

    private lateinit var notesViewModel: NotesViewModel



    class MyViewHolder(val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        //la linea de abajo da error
        //notesViewModel = ViewModelProvider(this ).get(NotesViewModel::class.java)


        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notesList[position]

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        with(holder){
            binding.NombreNota.text = currentItem.title.toString()
            binding.CreadorNota.text = currentItem.creator.toString()
            binding.ContenidoNota.text = currentItem.content.toString()

            binding.BotonBorrar.setOnClickListener {
                val id = currentItem.id.toInt()

                //no funciona debido a que no podemos inicializar notesVidewModel
                //notesViewModel.deleteNote(id)
            }

        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setData(notesList: List<Notes>){
        this.notesList = notesList
        notifyDataSetChanged()
    }



}