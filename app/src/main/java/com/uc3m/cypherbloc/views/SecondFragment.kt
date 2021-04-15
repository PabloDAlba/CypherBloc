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
    private lateinit var comm: Comunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root


        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser


        binding.user.text = currentUser?.displayName

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        val adapter = NoteAdapter(notesViewModel, requireContext())


        notesViewModel.readAll.observe(viewLifecycleOwner, { notes -> adapter.setData(notes) })

        val recyclerView = binding.recyclerView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


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
        return view
    }

}


