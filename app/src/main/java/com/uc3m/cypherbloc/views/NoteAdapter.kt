package com.uc3m.cypherbloc.views

import android.content.Context

import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.uc3m.cypherbloc.databinding.RecyclerViewItemBinding
import com.uc3m.cypherbloc.models.AESEncryptionDecryption
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel

class NoteAdapter(private val viewModel: NotesViewModel, private val context : Context): RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {


    private var notesList = emptyList<Notes>()


    private val notesViewModel: NotesViewModel = viewModel



    class MyViewHolder(val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notesList[position]


        with(holder){
            binding.NombreNota.text = currentItem.title

            binding.BotonBorrar.setOnClickListener {
                val id = currentItem.id

                notesViewModel.deleteNote(id)
            }
            binding.BotonShowPassword.setOnClickListener{
                if(binding.BotonShowPassword.text.toString().equals("Show")){
                    binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    binding.BotonShowPassword.text = "Hide"
                }
                else{
                    binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                    binding.BotonShowPassword.text = "Show"
                }
            }
            binding.BotonMostrar.setOnClickListener{
                val before = binding.password.text.toString()
                if(before.equals("")){
                   Toast.makeText(context, "Introduce una contraseña", Toast.LENGTH_LONG).show()
                }

                else{
                    val password = binding.password.text.toString().toCharArray()
                    //MOSTRANDO NOTA
                    if(binding.textDecrypted.visibility == View.GONE){
                        val newContent =  currentItem.content
                        Log.d("aux12", newContent.toString())
                        val textDecrypted = AESEncryptionDecryption().decrypt(context, password, newContent)

                        //password correct
                        if (textDecrypted != null){
                            //showing content decrypted
                            binding.textDecrypted.text = textDecrypted.toEditable()
                            binding.BotonMostrar.text = "GUARDAR"
                            binding.textDecrypted.visibility = View.VISIBLE
                        }

                        else{
                            binding.password.text = "".toEditable()
                        }
                    }
                    else {

                        //OCULTANDO NOTA
                        val newContent = AESEncryptionDecryption().encrypt(context, binding.textDecrypted.text.toString(), password)
                        notesViewModel.updateNote(currentItem.id, newContent)
                        binding.password.text = "".toEditable()
                        binding.BotonMostrar.text = "MOSTRAR NOTA"
                        binding.textDecrypted.visibility = View.GONE
                        binding.textDecrypted.text = "".toEditable()


                   }
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