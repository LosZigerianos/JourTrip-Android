package com.zigerianos.jourtrip.presentation.base

import android.content.Context
import android.view.View

abstract class ItemClickAdapter<T, U : BaseAdapter.BaseViewHolder>(context: Context) : BaseAdapter<T, U>(context) {

    protected var listener: OnItemClickListener<T>? = null

    interface OnItemClickListener<T> {
        fun onItemClick(item: T, position: Int, view: View)
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        this.listener = listener
    }

}