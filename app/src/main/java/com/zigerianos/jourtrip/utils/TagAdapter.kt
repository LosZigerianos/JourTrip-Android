package com.zigerianos.jourtrip.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseAdapter
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import kotlinx.android.synthetic.main.row_tag.view.*
import android.graphics.Color
import java.util.*


class TagAdapter(context: Context): ItemClickAdapter<String, TagAdapter.TagViewHolder>(context) {

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): TagViewHolder {
        val itemView = LayoutInflater.from(recyclerView.context)
            .inflate(R.layout.row_tag, recyclerView, false)

        return TagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TagViewHolder(view: View) : BaseAdapter.BaseViewHolder(view) {
        fun bind(tag: String) {
            with(itemView) {
                background.setTint(Utils.getRandomDarkColor())
                textViewTag.text = tag
            }
        }
    }
}