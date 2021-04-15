package com.uc3m.cypherbloc.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.auth.FirebaseAuth
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.RecyclerViewItemBinding
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel

class NoteAdapter(private val viewModel: NotesViewModel, private val context : Context): RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {


    private var notesList = emptyList<Notes>()
    //private lateinit var auth: FirebaseAuth

    private val notesViewModel: NotesViewModel = viewModel
    //private val mContext = context


    class MyViewHolder(val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        //la linea de abajo da error
        //notesViewModel = ViewModelProvider(this ).get(NotesViewModel::class.java)


        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notesList[position]


        with(holder){
            binding.NombreNota.text = currentItem.title
            binding.CreadorNota.text = currentItem.creator
            binding.ContenidoNota.text = currentItem.content.toString()

            binding.BotonBorrar.setOnClickListener {
                val id = currentItem.id

                notesViewModel.deleteNote(id)
            }

            binding.BotonMostrar.setOnClickListener{
                //decode

                Navigation.createNavigateOnClickListener(R.id.action_SecondFragment_to_passFragment)
                //var text : CharArray = "1234".toCharArray()
                //AESEncryptionDecryption().decrypt(context, text, currentItem.content)


               // val window = PopupWindow(mContext)
                //val view = layoutInflater.inflate(R.layout.popup_layout, null)
                //window.contentView = view
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