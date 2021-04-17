package com.uc3m.cypherbloc.views

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.auth.FirebaseAuth
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.RecyclerViewItemBinding
import com.uc3m.cypherbloc.models.AESEncryptionDecryption
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel

class NoteAdapter(private val viewModel: NotesViewModel, private val context : Context): RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {


    private var notesList = emptyList<Notes>()
    //private lateinit var auth: FirebaseAuth

    private lateinit var comm: Comunicator

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
            //binding.CreadorNota.text = currentItem.creator

            binding.BotonBorrar.setOnClickListener {
                val id = currentItem.id

                notesViewModel.deleteNote(id)
            }

            binding.BotonMostrar.setOnClickListener{
                val password = binding.password.text.toString().toCharArray()
                //MOSTRANDO NOTA
                if(binding.textDecrypted.visibility == View.GONE){
                    val newContent =  currentItem.content
                    val textDecrypted = AESEncryptionDecryption().decrypt(context, password, currentItem.content)

                    //checking password
                    if (textDecrypted == null) Toast.makeText(context,"CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG).show()

                    //password correct
                    else {
                        //showing content decrypted
                        binding.textDecrypted.text = textDecrypted.toEditable()
                        binding.BotonMostrar.text = "GUARDAR"
                        binding.textDecrypted.visibility = View.VISIBLE
                    }
                }
                else {

                    //OCULTANDO NOTA
                    Log.d("aux", binding.textDecrypted.text.toString())
                    val newContent = AESEncryptionDecryption().encrypt(context, binding.textDecrypted.text.toString(), password)
                    notesViewModel.updateNote(currentItem.id, newContent)
                    binding.password.text = "".toEditable()
                    binding.BotonMostrar.text = "MOSTRAR NOTA"
                    binding.textDecrypted.visibility = View.GONE
                    binding.textDecrypted.text = "".toEditable()

                    //comm = activity as Comunicator
                    //comm.passDataCom(currentItem.id)
                    //findNavController(binding.root).navigate(R.id.action_SecondFragment_to_passFragment)


                    // val window = PopupWindow(mContext)
                    //val view = layoutInflater.inflate(R.layout.popup_layout, null)
                    //window.contentView = view
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

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)



}