package com.uc3m.cypherbloc.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.uc3m.cypherbloc.databinding.FragmentSecondBinding
import com.uc3m.cypherbloc.databinding.RecyclerViewItemBinding
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.MyViewHolder>(){

    private var notesList = emptyList<Notes>()

    private lateinit var notesViewModel: NotesViewModel

    private lateinit var auth: FirebaseAuth

    class MyViewHolder(val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //notesViewModel = ViewModelProvider(this ).get(NotesViewModel::class.java) aqui esta el problema
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notesList[position]
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        with(holder){

           if(currentItem.creator.toString() == currentUser?.displayName.toString()) {
                binding.NombreNota.text = currentItem.title.toString()
                binding.CreadorNota.text = currentItem.creator.toString()
                binding.ContenidoNota.text = currentItem.content.toString()

                binding.BotonBorrar.setOnClickListener {
                    val id = currentItem.id.toInt()
                    notesViewModel.deleteNote(id)
                }
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