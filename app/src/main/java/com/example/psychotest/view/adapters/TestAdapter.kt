package com.example.psychotest.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psychotest.R
import com.example.psychotest.databinding.TestItemBinding
import com.example.psychotest.db.data.TestCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TestAdapter(private val listener: OnTestClickListener) :
    RecyclerView.Adapter<TestAdapter.TestHolder>() {

    private var testList: List<TestCard> = listOf()
    private var hide: Boolean = true

    class TestHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = TestItemBinding.bind(item)

        fun bind(testCard: TestCard, listener: OnTestClickListener, hide: Boolean) {
            binding.testName.text = testCard.title
            binding.testerName.text = testCard.name
            val date = Date(testCard.completionDate)
            val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            binding.testDate.text = dateFormatter.format(date)
            binding.testTime.text = timeFormatter.format(date)

            Log.d("HIDE", "$hide")
            binding.testerName.visibility = if (hide) View.VISIBLE else View.GONE
            binding.testDate.visibility = if (hide) View.VISIBLE else View.GONE
            binding.testTime.visibility = if (hide) View.VISIBLE else View.GONE

            binding.root.setOnClickListener {
                Log.d("CLICK", "${testCard.id}")
                listener.onClick(hide, testCard)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        return TestHolder(view)
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        val test = testList[position]
        holder.bind(test, listener, hide)
    }

    fun refresh(list: List<TestCard>?) {
        testList = list ?: emptyList()
        testList = testList.sortedBy { it.completionDate }.reversed()
        notifyDataSetChanged()
    }

    fun changeMode(mode: Boolean) {
        hide = mode
        notifyDataSetChanged()
    }

}