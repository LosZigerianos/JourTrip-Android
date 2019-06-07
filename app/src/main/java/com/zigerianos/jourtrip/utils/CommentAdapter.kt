package com.zigerianos.jourtrip.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.presentation.base.BaseAdapter
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import kotlinx.android.synthetic.main.row_comment.view.*
import org.joda.time.format.DateTimeFormat
import org.ocpsoft.prettytime.PrettyTime


class CommentAdapter(
    context: Context,
    private val picasso: Picasso,
    private val isShownUser: Boolean = true
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
                    .inflate(R.layout.row_comment, recyclerView, false)

                return CommentViewHolder(view)
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
            (holder as CommentViewHolder).bind(getItem(position))
        }
    }

    inner class CommentViewHolder(view: View) : BaseAdapter.BaseViewHolder(view) {
        init {
            itemView.container.setOnClickListener {
                listener?.onItemClick(getItem(adapterPosition), adapterPosition, itemView)
            }
        }

        fun bind(comment: Comment) {
            with(itemView) {
                picasso
                    .load(comment.location?.photos?.first())
                    .into(imageViewLocation)

                comment.location?.let { location ->
                    textViewTitle.text = location.name
                    textViewCaption.text = location.formattedAddress

                    textViewTitle.visibility = View.VISIBLE
                    textViewCaption.visibility = View.VISIBLE
                } ?: run {
                    textViewTitle.visibility = View.GONE
                    textViewCaption.visibility = View.GONE
                }

                comment.location?.tags?.let { tags ->
                    if (tags.count() > 0) {
                        recyclerViewTags.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                        val tagAdapter = TagAdapter(context)
                        tagAdapter.setItems(tags)

                        recyclerViewTags.adapter = tagAdapter
                        recyclerViewTags.visibility = View.VISIBLE
                    } else {
                        recyclerViewTags.visibility = View.GONE
                    }
                } ?: run {
                    recyclerViewTags.visibility = View.GONE
                }

                if (isShownUser) {
                    comment.user?.let { user ->
                        picasso.load(comment.user.photo).into(imageViewUser)

                        textViewUserName.text = "@${user.username}"

                        cardViewUserImage.visibility = View.VISIBLE
                        imageViewUser.visibility = View.VISIBLE
                        textViewUserName.visibility = View.VISIBLE
                    } ?: run {
                        cardViewUserImage.visibility = View.GONE
                        imageViewUser.visibility = View.GONE
                        textViewUserName.visibility = View.GONE
                    }
                } else {
                    cardViewUserImage.visibility = View.GONE
                    imageViewUser.visibility = View.GONE
                    textViewUserName.visibility = View.GONE
                }

                textViewComment.text = comment.description

                val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val creationDate = dateTimeFormatter.parseDateTime(comment.creationDate)
                textViewDate.text = PrettyTime().format(creationDate.toDate())
            }
        }
    }
}