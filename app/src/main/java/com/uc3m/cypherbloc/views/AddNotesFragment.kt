package com.uc3m.cypherbloc.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.FragmentAddNotesBinding
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel
import com.uc3m.cypherbloc.models.AESEncryptionDecryption

class AddNotesFragment : Fragment() {

    private lateinit var  binding: FragmentAddNotesBinding
    private lateinit var notesViewModel: NotesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        val view = binding.root

        notesViewModel = ViewModelProvider(this ).get(NotesViewModel::class.java)

        binding.button.setOnClickListener{
            insertDataToDatabase()
        }



        return view
    }

    private fun insertDataToDatabase(){
        val password = binding.Password.text.toString().toCharArray()

        val nombreNota = binding.NombreNota.text.toString()
        //nombreNota = AESEncryptionDecryption().encrypt(context, nombreNota).toString()
        var creadorNota = binding.CreadorNota.text.toString()
        creadorNota = AESEncryptionDecryption().encrypt(context, creadorNota, password).toString()
        var contenidoNota = binding.ContenidoNota.text.toString()
        contenidoNota = AESEncryptionDecryption().encrypt(context, contenidoNota, password).toString()

        if(inputCheck(nombreNota, creadorNota, contenidoNota)){

            val note = Notes(nombreNota,creadorNota,contenidoNota)
            notesViewModel.addNote(note)
            Toast.makeText(requireContext(), "Nota creada con exito", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addNotesFragment2_to_SecondFragment)
        }else{
            Toast.makeText(requireContext(), "Algún campo esta vacio",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(nombreNota: String, creadorNota: String, contenidoNota: String): Boolean{
        return!(TextUtils.isEmpty(nombreNota) || TextUtils.isEmpty(creadorNota) || TextUtils.isEmpty(contenidoNota))
    }
}