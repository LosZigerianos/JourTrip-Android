package com.zigerianos.jourtrip.presentation.scenes.home


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.di.ModulesNames
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import com.zigerianos.jourtrip.utils.CommentAdapter
import com.zigerianos.jourtrip.utils.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<IHomePresenter.IHomeView, IHomePresenter>(), IHomePresenter.IHomeView {

    private val mainPresenter by inject<IHomePresenter>()
    private val timelineAdapter by inject<CommentAdapter>(name = ModulesNames.ADAPTER_TIMELINE)

    private var mEndlessScrollListener = EndlessScrollListener {toast("Hola hola")}

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setupViews() {
        toolbar.toolbarTitle.text = getString(R.string.home)
        toolbar.toolbarImage.visibility = View.VISIBLE
        toolbar.toolbarImage.setOnClickListener {
            recyclerViewTimeline.smoothScrollToPosition(0)
        }

        activity?.bottomNavigationView?.visibility = View.VISIBLE

        setupRecyclerView()
    }

    override fun stateLoading() {
        errorLayout.visibility = View.GONE
        recyclerViewTimeline.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        errorLayout.visibility = View.GONE
        recyclerViewTimeline.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateError() {
        recyclerViewTimeline.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
    }

    override fun loadComments(comments: List<Comment>) {
        val location =
            Location("", "", "", "", "", "", "")

        val user = User("", "", "", "", "", "", "", "", "")

        val dummy = listOf<Comment>(
            Comment("", user, location, "", ""),
            Comment("", user, location, "", "")
        )

        timelineAdapter.setItems(comments)
    }

    private fun setupRecyclerView() {
        recyclerViewTimeline.layoutManager = LinearLayoutManager(activity)
        recyclerViewTimeline.adapter = timelineAdapter

        mEndlessScrollListener.shouldListenForMorePages(true)
        recyclerViewTimeline.addOnScrollListener(mEndlessScrollListener)

        timelineAdapter.setLoaderVisible(true)

        timelineAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Comment> {
            override fun onItemClick(item: Comment, position: Int, view: View) {
                //presenter.locationSelected(position)
                toast("Comment selected $position")
            }

        })
    }

    override fun getLayoutResource(): Int = R.layout.fragment_home
}
