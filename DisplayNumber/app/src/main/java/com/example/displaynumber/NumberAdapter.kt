package com.example.displaynumber

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumberAdapter(private var numbers: List<Int> = emptyList()) :
    RecyclerView.Adapter<NumberAdapter.NumberVH>() {

    fun submitList(newList: List<Int>) {
        numbers = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_number, parent, false)
        return NumberVH(view)
    }

    override fun onBindViewHolder(holder: NumberVH, position: Int) {
        holder.bind(numbers[position])
    }

    override fun getItemCount(): Int = numbers.size

    class NumberVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv: TextView = itemView.findViewById(R.id.tvNumber)
        fun bind(value: Int) {
            tv.text = value.toString()
        }
    }
}
