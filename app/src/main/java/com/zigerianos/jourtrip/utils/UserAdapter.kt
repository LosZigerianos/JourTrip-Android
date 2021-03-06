package com.zigerianos.jourtrip.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.presentation.base.BaseAdapter
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import kotlinx.android.synthetic.main.row_user.view.*
import org.joda.time.format.DateTimeFormat
import org.ocpsoft.prettytime.PrettyTime

class UserAdapter(
    context: Context,
    private val picasso: Picasso
) : ItemClickAdapter<Comment, BaseAdapter.BaseViewHolder>(context) {

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
                    .inflate(R.layout.row_user, recyclerView, false)

                return UserViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(recyclerView.context)
                    .inflate(R.layout.row_loader, recyclerView, false)

                BaseViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseAdapter.BaseViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            (holder as UserViewHolder).bind(getItem(position))
        }
    }

    inner class UserViewHolder(view: View) : BaseAdapter.BaseViewHolder(view) {
        init {
            with(itemView) {
                container.setOnClickListener {
                    listener?.onItemClick(getItem(adapterPosition), adapterPosition, this)
                }

                imageViewUser.setOnClickListener {
                    listener?.onItemClick(getItem(adapterPosition), adapterPosition, this.imageViewUser)
                }
            }
        }

        fun bind(comment: Comment) {
            with(itemView) {
                comment.user?.let { user ->
                    picasso
                        .load(comment.user.photo)
                        .placeholder(R.drawable.ic_user_profile)
                        .error(R.drawable.ic_user_profile)
                        .into(imageViewUser)

                    textViewName.text = user.fullname

                    cardViewUserImage.visibility = View.VISIBLE
                    imageViewUser.visibility = View.VISIBLE
                    textViewName.visibility = View.VISIBLE
                } ?: run {
                    cardViewUserImage.visibility = View.GONE
                    imageViewUser.visibility = View.GONE
                    textViewName.visibility = View.GONE
                }

                textViewComment.text = comment.description

                val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val creationDate = dateTimeFormatter.parseDateTime(comment.creationDate)
                textViewDate.text = PrettyTime().format(creationDate.toDate())
            }
        }
    }
}