package com.example.psychotest.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.psychotest.R
import com.example.psychotest.databinding.FragmentHelloBinding
import com.example.psychotest.viewModel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HelloFragment : Fragment() {

    private lateinit var binding: FragmentHelloBinding
    private val viewModel: TestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHelloBinding.inflate(inflater, container, false)

        initState()

        return binding.root
    }

    private fun initState() {
        binding.nameEditText.setText(viewModel.getUserName())

        binding.nextButton.setOnClickListener {
            Log.d("TEXT", "${binding.nameEditText.text}")
            viewModel.changeName(binding.nameEditText.text.toString())
            viewModel.testMode()
            findNavController().navigate(R.id.action_helloFragment_to_menuFragment)
        }
    }



}