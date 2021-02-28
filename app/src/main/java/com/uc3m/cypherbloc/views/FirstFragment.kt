package com.uc3m.cypherbloc.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.uc3m.cypherbloc.R
import com.uc3m.cypherbloc.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        binding.buttonFirst.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}