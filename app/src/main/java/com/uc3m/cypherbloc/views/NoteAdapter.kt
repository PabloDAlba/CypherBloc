package com.uc3m.cypherbloc.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uc3m.cypherbloc.databinding.RecyclerViewItemBinding
import com.uc3m.cypherbloc.models.Notes

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.MyViewHolder>(){

    private var notesList = emptyList<Notes>()

    class MyViewHolder(val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notesList[position]
        with(holder){
            binding.NombreNota.text = currentItem.title.toString()
            binding.CreadorNota.text = currentItem.creator.toString()
            binding.ContenidoNota.text = currentItem.content.toString()
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