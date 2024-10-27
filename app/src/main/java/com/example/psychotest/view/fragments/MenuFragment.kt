package com.example.psychotest.view.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.psychotest.R
import com.example.psychotest.databinding.FragmentMenuBinding
import com.example.psychotest.db.data.TestCard
import com.example.psychotest.view.adapters.OnTestClickListener
import com.example.psychotest.view.adapters.TestAdapter
import com.example.psychotest.viewModel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment(), OnTestClickListener {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var adapter: TestAdapter
    private val viewModel: TestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initState()

    }

    override fun onStart() {
        super.onStart()
        viewModel.updateTest()
    }

    private fun initState() {
        setRecycler()
        setListeners()
        observeViewModel()
    }

    private fun setRecycler() {
        adapter = TestAdapter(this)
        binding.testRecycler.adapter = adapter
        binding.testRecycler.layoutManager = LinearLayoutManager(context)
        binding.testRecycler.setPadding(20, 0, 20, 0)
        binding.testRecycler.clipToPadding = false
        binding.testRecycler.clipChildren = false

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.testRecycler)


    }

    private fun setListeners() {

        binding.testButton.setOnClickListener {
            viewModel.testMode()
        }

        binding.resultButton.setOnClickListener {
            viewModel.resultMode()
        }

        binding.exitButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_helloFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.allSessionResults.observe(viewLifecycleOwner) {cards ->
            adapter.refresh(cards ?: listOf())
            Log.d("OBSERV_SES","$cards")
            if (viewModel.mode.value == false) viewModel.updateTest()
        }
        viewModel.allTests.observe(viewLifecycleOwner) { cards ->
            adapter.refresh(cards ?: listOf())
            Log.d("OBSERV","$cards")
            if (viewModel.mode.value == true) viewModel.updateRes()
        }

        viewModel.mode.observe(viewLifecycleOwner) {
            adapter.changeMode(it)

            if (it) {
                binding.testButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
                binding.resultButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.green_300)
            } else {
                binding.testButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.green_300)
                binding.resultButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
            }
        }


    }

    override fun onClick(hide: Boolean, testCard: TestCard) {
        viewModel.setTest(testCard)
        if (!hide) {
            findNavController().navigate(R.id.action_menuFragment_to_testingFragment)
        } else {
            findNavController().navigate(R.id.action_menuFragment_to_resultFragment)
        }

    }


}