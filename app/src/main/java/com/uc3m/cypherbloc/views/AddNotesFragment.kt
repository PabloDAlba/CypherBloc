package com.uc3m.cypherbloc.views

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.util.Hex
import com.google.firebase.auth.FirebaseAuth
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.FragmentAddNotesBinding
import com.uc3m.cypherbloc.models.AESEncryptionDecryption
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.viewModels.NotesViewModel
import org.komputing.khash.keccak.KeccakParameter
import org.komputing.khash.keccak.extensions.digestKeccak

class AddNotesFragment : Fragment() {

    private var myCount: Int = -1

    private lateinit var binding: FragmentAddNotesBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.user.text = currentUser?.displayName

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)


        binding.buttonCheckPassword.setOnClickListener {
            val pwd = binding.Password.text.toString()
            val x1 = pwd.digestKeccak(KeccakParameter.KECCAK_512)
            val hex1 = Hex.bytesToStringLowercase(x1)
            val query = hex1.substring(0 ,10)
            Log.d("keccak", query)

            notesViewModel.searchPass(query)

            notesViewModel.myResponse.observe(viewLifecycleOwner, { response ->
                Log.d("http body", response.body().toString())
                Log.d("http code", response.code().toString())
                Log.d("http headers", response.headers().toString())
                Log.d("http message", response.message())
                Log.d("http error body", response.errorBody().toString())
                if(response.isSuccessful) Toast.makeText(requireContext(), "Contraseña insegura, prueba otra", Toast.LENGTH_LONG).show()
                else Toast.makeText(requireContext(), "Contraseña segura", Toast.LENGTH_LONG).show()

            })


        } //fin listener

        binding.buttonShowPassword.setOnClickListener {
            if (binding.buttonShowPassword.text.toString().equals("Show")) {
                binding.Password.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.buttonShowPassword.text = "Hide"
            } else {
                binding.Password.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.buttonShowPassword.text = "Show"
            }
        }

        binding.buttonAdd.setOnClickListener {
            insertDataToDatabase()
        }

        binding.buttonBack.setOnClickListener() {
            findNavController().navigate(R.id.action_addNotesFragment2_to_SecondFragment)
        }


        return view
    }

    private fun insertDataToDatabase() {
        var aes = AESEncryptionDecryption()
        val password = binding.Password.text.toString()
        val nombreNota = binding.NombreNota.text.toString()
        val creadorNota = auth.currentUser?.email.toString()
        val AuxcontenidoNota = binding.ContenidoNota.text.toString()

        if (inputCheck(nombreNota, creadorNota, AuxcontenidoNota, password)) {
            val contenidoNota = aes.encrypt(AuxcontenidoNota, password)
            val note = Notes(0, nombreNota, creadorNota, contenidoNota, aes.data[0], aes.data[1])
            notesViewModel.addNote(note)
            Toast.makeText(requireContext(), "Nota creada con exito", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addNotesFragment2_to_SecondFragment)
        } else {
            Toast.makeText(requireContext(), "Algún campo esta vacio", Toast.LENGTH_LONG).show()
        }
    }


    private fun inputCheck(
        nombreNota: String,
        creadorNota: String,
        contenidoNota: String,
        auxPass: String
    ): Boolean {
        return !(TextUtils.isEmpty(nombreNota) || TextUtils.isEmpty(creadorNota) || TextUtils.isEmpty(
            contenidoNota
        ) || TextUtils.isEmpty(auxPass))
    }

}





