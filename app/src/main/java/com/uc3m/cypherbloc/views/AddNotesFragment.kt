package com.uc3m.cypherbloc.views

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.FragmentAddNotesBinding
import com.uc3m.cypherbloc.models.AESEncryptionDecryption
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel
import org.komputing.khash.keccak.Keccak
import org.komputing.khash.keccak.KeccakParameter
import org.komputing.khash.keccak.extensions.digestKeccak

class AddNotesFragment : Fragment() {

    private lateinit var  binding: FragmentAddNotesBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.user.text = currentUser?.displayName

        notesViewModel = ViewModelProvider(this ).get(NotesViewModel::class.java)


        binding.buttonCheckPassword.setOnClickListener{
            val pwd = binding.Password.toString()

            val x1 = "1234".digestKeccak(KeccakParameter.KECCAK_512)
            val x2 = Keccak.digest( "1234".toByteArray(), KeccakParameter.KECCAK_512)

            Log.d("keccak1", x1.toString())
            Log.d("keccak3", x2.toString())
            ///
            /*
            Toast.makeText(context, x.toString(), Toast.LENGTH_LONG).show()
            var url = "https://xposedornot.com/api/v1/pass/anon/" + x.toString()
            URL(url).readText()
            */

        }

        binding.buttonShowPassword.setOnClickListener{
            if(binding.buttonShowPassword.text.toString().equals("Show")){
                binding.Password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.buttonShowPassword.text = "Hide"
            }
            else{
                binding.Password.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.buttonShowPassword.text = "Show"
            }
        }

        binding.buttonAdd.setOnClickListener{
            insertDataToDatabase()
        }

        binding.buttonBack.setOnClickListener(){
            findNavController().navigate(R.id.action_addNotesFragment2_to_SecondFragment)
        }


        return view
    }

    private fun insertDataToDatabase(){
        val password = binding.Password.text.toString().toCharArray()
        val auxPass = password.toString()
        val nombreNota = binding.NombreNota.text.toString()
        val creadorNota = auth.currentUser?.email.toString()
        val AuxcontenidoNota = binding.ContenidoNota.text.toString()

        if(inputCheck(nombreNota, creadorNota, AuxcontenidoNota, auxPass)){
            val contenidoNota = AESEncryptionDecryption().encrypt(context, AuxcontenidoNota, password)
            val note = Notes(0,nombreNota,creadorNota,contenidoNota)
            notesViewModel.addNote(note)
            Toast.makeText(requireContext(), "Nota creada con exito", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addNotesFragment2_to_SecondFragment)
        }else{
            Toast.makeText(requireContext(), "Algún campo esta vacio",Toast.LENGTH_LONG).show()
        }
    }


    private fun inputCheck(nombreNota: String, creadorNota: String, contenidoNota: String, auxPass: String): Boolean{
        return!(TextUtils.isEmpty(nombreNota) || TextUtils.isEmpty(creadorNota) || TextUtils.isEmpty(contenidoNota) || TextUtils.isEmpty(auxPass))
    }


}