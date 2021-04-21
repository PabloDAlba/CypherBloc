package com.uc3m.cypherbloc.views

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.util.Hex
import com.google.firebase.auth.FirebaseAuth
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.apis.XoNAPI
import com.uc3m.cypherbloc.databinding.FragmentAddNotesBinding
import com.uc3m.cypherbloc.models.AESEncryptionDecryption
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.models.XoNAux
import com.uc3m.cypherbloc.utils.Constants
import com.uc3m.cypherbloc.viewModels.NotesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.komputing.khash.keccak.KeccakParameter
import org.komputing.khash.keccak.extensions.digestKeccak
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddNotesFragment : Fragment() {

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
            Log.d("keccak1", hex1)


            //var url = "https://xposedornot.com/api/v1/pass/anon/$hex"
            //URL(url).readText()


        }

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

        webView = binding.web
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        webView.loadUrl("https://emn178.github.io/online-tools/keccak_512.html")


        return view
    }

    private fun insertDataToDatabase() {
        val password = binding.Password.text.toString().toCharArray()
        val auxPass = password.toString()
        val nombreNota = binding.NombreNota.text.toString()
        val creadorNota = auth.currentUser?.email.toString()
        val AuxcontenidoNota = binding.ContenidoNota.text.toString()

        if (inputCheck(nombreNota, creadorNota, AuxcontenidoNota, auxPass)) {
            val contenidoNota =
                AESEncryptionDecryption().encrypt(context, AuxcontenidoNota, password)
            val note = Notes(0, nombreNota, creadorNota, contenidoNota)
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

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.XoNAPI_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchPass(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<XoNAux> =
                getRetrofit().create(XoNAPI::class.java).CheckPass("$query")
            val content: XoNAux? = call.body()
            var count: Int? = 0
            //lo que sea
            if (call.isSuccessful) count = content?.json?.count?.toInt()
            else {
                //gestionar error
            }
        }
    }
}




