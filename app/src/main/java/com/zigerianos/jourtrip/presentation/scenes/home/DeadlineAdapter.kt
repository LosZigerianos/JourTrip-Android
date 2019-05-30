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
): ItemClickAdapter<Location, BaseAdapter.BaseViewHolder>(context) {

    private val TYPE_ITEM = 1
    private val TYPE_LOADER = 2
    private var mLoaderVisible = false

    protected fun isLoaderVisible(): Boolean {
        return mLoaderVisible
    }

    fun setLoaderVisible(loaderVisible: Boolean) {
        if (loaderVisible != mLoaderVisible) {
            mLoaderVisible = loaderVisible
            if (mLoaderVisible) {
                notifyItemInserted(itemCount)
            } else {
                notifyItemRemoved(itemCount)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mLoaderVisible && getItems().count() == position) {
            TYPE_LOADER
        } else TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (mLoaderVisible) 1 else 0
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val view = LayoutInflater.from(recyclerView.context)
                    .inflate(R.layout.row_location, recyclerView, false)

                DeadlineViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(recyclerView.context)
                    .inflate(R.layout.row_loader, recyclerView, false)

                BaseViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            (holder as DeadlineViewHolder).bind(getItem(position))
        }
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