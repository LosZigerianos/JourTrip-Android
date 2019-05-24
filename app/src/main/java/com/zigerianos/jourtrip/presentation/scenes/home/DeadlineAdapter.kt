package com.zigerianos.jourtrip.presentation.scenes.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.presentation.base.BaseAdapter
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import kotlinx.android.synthetic.main.row_location.view.*

class DeadlineAdapter(
    context: Context
): ItemClickAdapter<Location, DeadlineAdapter.DeadlineViewHolder>(context) {
    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): DeadlineViewHolder {
        val view = LayoutInflater.from(recyclerView.context)
            .inflate(R.layout.row_location, recyclerView, false)

        return DeadlineViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeadlineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class DeadlineViewHolder(view: View) : BaseAdapter.BaseViewHolder(view) {
        init {
            itemView.container.setOnClickListener {
                listener?.onItemClick(getItem(adapterPosition), adapterPosition, itemView)
            }
        }

        fun bind(location: Location) {
            with(itemView) {
                textViewTitle.text = "TEST TEXT"

            }
        }
    }
}