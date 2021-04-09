package com.uc3m.cypherbloc.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.FragmentSecondBinding
import com.uc3m.cypherbloc.viewModels.NotesViewModel


enum class ProviderType {
    BASIC,
    GOOGLE
}

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root


        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.user.text = currentUser?.displayName

        val adapter = NoteAdapter()
        val recyclerView = binding.recyclerView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.readAll.observe(viewLifecycleOwner, { notes -> adapter.setData(notes) })



        binding.buttonSecond.setOnClickListener {
            //hace falta un flush() para borrar cookies pero no encuentro como hacerlo
           /* val db = FirebaseDatabase.getInstance()
            db.goOffline()
            db.setPersistenceEnabled(false)*/
            auth.signOut()
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
    /*
      private fun setup(email: String, provider: String){
          title = "inicio"
          binding.
          providerTextView.text = provider

          logOutButton.setOnclick

    }*/
}


