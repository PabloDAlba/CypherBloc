package com.uc3m.cypherbloc.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uc3m.cypherbloc.databinding.PassLayoutBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.FragmentSecondBinding
import com.uc3m.cypherbloc.viewModels.NotesViewModel

class PassFragment : Fragment() {

    private lateinit var binding: PassLayoutBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var auth: FirebaseAuth
    private var Noteid: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PassLayoutBinding.inflate(inflater, container, false)
        val view = binding.root

        //Fiebase
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        binding.user.text = currentUser?.displayName

        Noteid = arguments?.getInt("ID")
        //Notes
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        val adapter = NoteAdapter(notesViewModel, requireContext())

        //var nota = notesViewModel.findNote()

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_passFragment_to_SecondFragment)
        }

        return view

    }


}



