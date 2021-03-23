package com.uc3m.cypherbloc.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.FragmentAddNotesBinding
import com.uc3m.cypherbloc.databinding.FragmentSecondBinding
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel

enum class ProviderType {
    BASIC,
    GOOGLE
}

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root


        val adapter = NoteAdapter()
        val recyclerView = binding.recyclerView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.readAll.observe(viewLifecycleOwner, { notes -> adapter.setData(notes) })


        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_addNotesFragment22)
        }
/**
        // setup
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString(key: "email")
        val provider: String? = bundle?.getString(key:"provider")
        setup(email:email ?: "", provider:provider ?: "")
*/
        return view
    }

    private fun setup(email: String, provider: String){
      /*  title = "inicio"
        binding.
        providerTextView.text = provider

        logOutButton.setOnclick
*/
    }
}