package com.example.psychotest.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.psychotest.R
import com.example.psychotest.databinding.FragmentTestingBinding
import com.example.psychotest.view.adapters.TextPagerAdapter
import com.example.psychotest.viewModel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestingFragment : Fragment() {

    private val viewModel: TestViewModel by activityViewModels()
    private lateinit var binding: FragmentTestingBinding
    private lateinit var adapter: TextPagerAdapter
    private lateinit var texts: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestingBinding.inflate(layoutInflater, container, false)
        viewModel.score = 0
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initState()
    }

    private fun initState() {
        setPager()
        setListeners()
        Log.d("NAME", "${viewModel.currentTest.value}")
        binding.testName.text = viewModel.currentTest.value!!.title
    }

    private fun setPager() {
        viewModel.getQuestionsForTest().observe(viewLifecycleOwner) { questions ->
            adapter = TextPagerAdapter(questions.toMutableList())
            binding.viewPager.adapter = adapter
        }
    }

    private fun setListeners() {
        binding.yesButton.setOnClickListener {
            viewModel.score++
            if (adapter.removeItem(binding.viewPager.currentItem)) finishTest()
        }
        binding.noButton.setOnClickListener {
            if (adapter.removeItem(binding.viewPager.currentItem)) finishTest()
        }
    }

    private fun finishTest() {
        viewModel.setResult()
        findNavController().navigate(R.id.action_testingFragment_to_resultFragment)
    }


}