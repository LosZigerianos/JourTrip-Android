package com.zigerianos.jourtrip.presentation.scenes.locationdetail


import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.input.input
import com.squareup.picasso.Picasso
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import com.zigerianos.jourtrip.utils.EndlessScrollListener
import com.zigerianos.jourtrip.utils.UserAdapter
import kotlinx.android.synthetic.main.fragment_location_detail.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import kotlinx.android.synthetic.main.fragment_error_loading.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject

class LocationDetailFragment :
    BaseFragment<ILocationDetailPresenter.ILocationDetailView, ILocationDetailPresenter>(),
    ILocationDetailPresenter.ILocationDetailView {

    private val argLocation: Location by lazy { LocationDetailFragmentArgs.fromBundle(arguments!!).location }

    private val mainPresenter by inject<ILocationDetailPresenter>()
    private val picasso by inject<Picasso>()
    private val userCommentAdapter by inject<UserAdapter>()

    private var mEndlessScrollListener = EndlessScrollListener { presenter.loadMoreData() }

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        presenter.setLocation(argLocation)
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupViews() {
        toolbar.toolbarImage.visibility = View.VISIBLE
        toolbar.toolbarImage.setOnClickListener {
            scrollViewLocation.fullScroll(ScrollView.FOCUS_UP)
        }

        picasso
            .load(presenter.getPhoto())
            .into(imageViewLocation)

        textViewName.text = presenter.getName()
        textViewAddress.text = presenter.getAddress()

        buttonAddComment.setOnClickListener {
            MaterialDialog(context!!, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                cornerRadius(16f)

                input(
                    hint = getString(R.string.your_comment),
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                ) { _, text ->
                    presenter.addCommentToLocation(text.toString())
                }

                positiveButton(R.string.comment)
                negativeButton(R.string.cancel)
            }
        }

        //activity?.bottomNavigationView?.visibility = View.GONE

        setupRecyclerView()
        setupError()
    }

    override fun stateLoading() {
        errorLayout.visibility = View.GONE
        scrollViewLocation.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        errorLayout.visibility = View.GONE
        scrollViewLocation.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateError() {
        scrollViewLocation.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
    }

    override fun loadComments(comments: List<Comment>, forMorePages: Boolean) {
        if (comments.isEmpty()) {
            userCommentAdapter.setLoaderVisible(false)
            mEndlessScrollListener.shouldListenForMorePages(false)
            return
        }

        userCommentAdapter.setLoaderVisible(forMorePages)
        mEndlessScrollListener.shouldListenForMorePages(forMorePages)
        userCommentAdapter.addItems(comments)
    }

    override fun loadComment(comment: Comment) {
        userCommentAdapter.addItemZero(comment)
    }

    override fun showErrorMessage() {
        toast(R.string.error_request_message)
    }

    override fun navigateToUserProfile(main: Boolean, user: User) {
        user.id?.let { userId ->
            val action = if (main) {
                LocationDetailFragmentDirections.actionGoToNavigationMainProfile(userId = userId)
            } else {
                LocationDetailFragmentDirections.actionGoToNavigationProfile(userId = userId)
            }

            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun setupRecyclerView() {
        recyclerViewComments.layoutManager = LinearLayoutManager(activity)
        recyclerViewComments.adapter = userCommentAdapter

        mEndlessScrollListener.shouldListenForMorePages(true)
        recyclerViewComments.addOnScrollListener(mEndlessScrollListener)
        userCommentAdapter.setLoaderVisible(true)

        userCommentAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Comment> {
            override fun onItemClick(item: Comment, position: Int, view: View) {
                item.user?.let { user ->
                    presenter.userClicked(user)
                }
            }
        })
    }

    private fun setupError() {
        errorLayout.buttonReload.setOnClickListener { presenter.reloadDataClicked() }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_location_detail
}
