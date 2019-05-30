package com.zigerianos.jourtrip.utils

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class EndlessScrollListener(private val refreshList: () -> Unit) : RecyclerView.OnScrollListener() {

    private var isLoading: Boolean = false
    private var isRefreshing: Boolean = false
    private var pastVisibleItems: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val manager = recyclerView.layoutManager

        val visibleItemCount = manager!!.childCount
        val totalItemCount = manager.itemCount
        val firstVisibleItems = if (manager is StaggeredGridLayoutManager)
            manager.findFirstVisibleItemPositions(null)
        else
            intArrayOf((manager as LinearLayoutManager).findFirstVisibleItemPosition())
        if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
            pastVisibleItems = firstVisibleItems[0]
        }

        if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
            isLoading = true
            if (!isRefreshing) {
                isRefreshing = true
                Handler().postDelayed({ refreshList.invoke() }, 200)
            }
        } else {
            isLoading = false
        }
    }

    fun shouldListenForMorePages(listenForMorePages: Boolean) {
        isRefreshing = !listenForMorePages
    }
}