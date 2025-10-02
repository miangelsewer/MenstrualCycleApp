package com.example.menstrualcycleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CycleAdapter : ListAdapter<CycleEntity, CycleAdapter.CycleViewHolder>(DiffCallback()) {

    class CycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateText: TextView = itemView.findViewById(R.id.textDate)
        private val flowText: TextView = itemView.findViewById(R.id.textFlow)
        private val symptomsText: TextView = itemView.findViewById(R.id.textSymptoms)

        fun bind(entry: CycleEntity) {
            dateText.text = entry.startDate
            flowText.text = "Flow: ${entry.flowLevel}"
            symptomsText.text = "Symptoms: ${entry.symptoms}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CycleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cycle_entry, parent, false)
        return CycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: CycleViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry)
    }

    class DiffCallback : DiffUtil.ItemCallback<CycleEntity>() {
        override fun areItemsTheSame(oldItem: CycleEntity, newItem: CycleEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CycleEntity, newItem: CycleEntity) = oldItem == newItem
    }
}