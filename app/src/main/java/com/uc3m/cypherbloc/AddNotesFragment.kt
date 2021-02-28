package com.uc3m.cypherbloc

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

class AddNotesFragment : Fragment() {

    private lateinit var  binding: FragmentAddNotesBinding
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_notes, container, false)
        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        val view = binding.root

        notesViewModel = ViewModelProvider(this ).get(NotesViewModel::class.java)

        binding.button.setOnClickListener{
            insertDataToDatabase()
        }



        return view
    }

    private fun insertDataToDatabase(){
        val nombreNota = binding.NombreNota.text.toString()
        val creadorNota = binding.CreadorNota.text.toString()
        val contenidoNota = binding.ContenidoNota.text.toString()

        if(inputCheck(nombreNota,creadorNota,contenidoNota)){
            val note = Notes(nombreNota,creadorNota,contenidoNota)
            notesViewModel.addNote(note)
            findNavController().navigate(R.id.action_addNotesFragment2_to_SecondFragment)
        }else{
            Toast.makeText(requireContext(), "Algún campo esta vacio",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(nombreNota: String, creadorNota: String, contenidoNota: String): Boolean{
        return(TextUtils.isEmpty(nombreNota) && TextUtils.isEmpty(creadorNota) && TextUtils.isEmpty(contenidoNota))
    }
}