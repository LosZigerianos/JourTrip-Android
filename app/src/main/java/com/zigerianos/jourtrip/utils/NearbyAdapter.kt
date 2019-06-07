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
) : ItemClickAdapter<Location, BaseAdapter.BaseViewHolder>(context) {

    val TYPE_ITEM = 0
    val TYPE_LOADER = 1
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
                    .inflate(R.layout.row_nearby, recyclerView, false)

                return NearbyViewHolder(view)
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
            (holder as NearbyViewHolder).bind(getItem(position))
        }
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
