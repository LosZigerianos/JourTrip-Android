package com.zigerianos.jourtrip.presentation.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

open abstract class BaseAdapter<T, U : BaseAdapter.BaseViewHolder>(val context: Context) : RecyclerView.Adapter<U>() {

    private var items: MutableList<T> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: T) {
        items.add(item)

        notifyItemInserted(itemCount - 1)
    }

    fun addItems(items: List<T>) {
        if (items.size > 0) {
            this.items.addAll(items)

            // TODO: REPASAR
            //notifyItemRangeInserted(itemCount - items.size - 1, itemCount - 1)
            notifyItemRangeInserted(itemCount - items.size, itemCount)
        }
    }

    fun removeItem(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItem(position: Int, item: T) {
        this.items.removeAt(position)
        this.items.add(position, item)
        notifyItemChanged(position)
    }

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    fun getItems(): List<T> {
        return items
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    open class BaseViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}