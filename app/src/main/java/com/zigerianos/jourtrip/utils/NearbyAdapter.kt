package com.zigerianos.jourtrip.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.presentation.base.BaseAdapter
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import kotlinx.android.synthetic.main.row_nearby.view.*

class NearbyAdapter(
    context: Context,
    private val picasso: Picasso
) : ItemClickAdapter<Location, NearbyAdapter.NearbyViewHolder>(context) {

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): NearbyViewHolder {
        val view = LayoutInflater.from(recyclerView.context)
            .inflate(R.layout.row_nearby, recyclerView, false)

        return NearbyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NearbyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NearbyViewHolder(view: View) : BaseAdapter.BaseViewHolder(view) {
        init {
            itemView.container.setOnClickListener {
                listener?.onItemClick(getItem(adapterPosition), adapterPosition, itemView)
            }
        }

        fun bind(location: Location) {
            with(itemView) {
                picasso
                    .load(location.photos?.first())
                    .into(itemView.imageViewLocation)

                location.let { location ->
                    textViewTitle.text = location.name
                    textViewCaption.text = location.formattedAddress

                    textViewTitle.visibility = View.VISIBLE
                    textViewCaption.visibility = View.VISIBLE
                }

            }
        }
    }
}
