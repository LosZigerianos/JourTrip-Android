package com.zigerianos.jourtrip.presentation.scenes.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItems
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.UserProfile
import com.zigerianos.jourtrip.di.ModulesNames
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import com.zigerianos.jourtrip.utils.CommentAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_error_loading.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.koin.android.ext.android.inject


class ProfileFragment : BaseFragment<IProfilePresenter.IProfileView, IProfilePresenter>(),
    IProfilePresenter.IProfileView {

    private val argUserId: String? by lazy {
        arguments?.let {
            ProfileFragmentArgs.fromBundle(it).userId
        } ?: run {
            null
        }
    }

    private val mainPresenter by inject<IProfilePresenter>()
    private val picasso by inject<Picasso>()
    private val commentAdapter by inject<CommentAdapter>(name = ModulesNames.ADAPTER_PROFILE)

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        presenter.setUserId(argUserId)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        if (presenter.isPersonal()) {
            inflater.inflate(R.menu.menu_profile, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_settings -> {
                presenter.settingsClicked()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)

        if (!presenter.isPersonal()) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun setupViews() {
        toolbar.toolbarTitle.text = getString(R.string.profile)
        toolbar.toolbarImage.visibility = View.VISIBLE
        toolbar.toolbarImage.setOnClickListener {
            scrollViewProfile.fullScroll(ScrollView.FOCUS_UP)
        }

        activity?.bottomNavigationView?.visibility = View.VISIBLE

        if (presenter.isPersonal()) {
            buttonFollow.visibility = View.GONE
        } else {
            buttonFollow.setOnClickListener {
                if (presenter.isFollowingUser()) {
                    MaterialDialog(context!!).show {
                        title(R.string.unfollow_user_title)
                        message(R.string.unfollow_user_message)
                        positiveButton(R.string.unfollow) { _ ->
                            presenter.followUserClicked()
                        }
                        negativeButton(R.string.cancel)
                    }
                } else {
                    presenter.followUserClicked()
                }
            }
        }

        scrollViewProfile.setOnBottomReachedListener {
            presenter.loadMoreData()
        }

        setupRecyclerView()
        setupError()
    }

    override fun stateLoading() {
        errorLayout.visibility = View.GONE
        scrollViewProfile.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        errorLayout.visibility = View.GONE
        scrollViewProfile.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateError() {
        scrollViewProfile.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
    }

    override fun loadUser(profile: UserProfile) {

        profile.fullname?.let { fullname ->
            textViewFullname.text = profile.fullname
        } ?: run {
            textViewFullname.visibility = View.GONE
        }

        profile.username?.let { username ->
            textViewUsername.text = "@${username}"
        } ?: run {
            textViewUsername.visibility = View.GONE
        }

        buttonFollow.text = if (presenter.isFollowingUser()) getString(R.string.unfollow) else getString(R.string.follow)

        textViewFollowingQuantity.text = profile.following?.toString()
        textViewFollowersQuantity.text = profile.followers?.toString()
        textViewPostsQuantity.text = profile.commentsCount.toString()

        picasso
            .load(profile.photo)
            .placeholder(R.drawable.ic_user_profile)
            .error(R.drawable.ic_user_profile)
            .into(imageViewUser)

        profile.following?.let { followingQuantity ->
            if (followingQuantity > 0) {
                textViewFollowingQuantity.setOnClickListener {
                    presenter.followingClicked()
                }
            }
        }

        profile.followers?.let { followersQuantity ->
            if (followersQuantity > 0) {
                textViewFollowersQuantity.setOnClickListener {
                    presenter.followersClicked()
                }
            }
        }
    }

    override fun loadComments(comments: List<Comment>, forMorePages: Boolean) {
        if (comments.isEmpty()) {
            commentAdapter.setLoaderVisible(false)
            scrollViewProfile.onBottomReachedListener = null
            return
        }

        commentAdapter.setLoaderVisible(forMorePages)
        if (!forMorePages) scrollViewProfile.onBottomReachedListener = null
        commentAdapter.addItems(comments)
    }

    override fun showErrorMessage() {
        view?.let { viewFrag ->
            Snackbar.make(viewFrag, getString(R.string.error_request), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun followUserChanged(followersQuantity: String) {
        buttonFollow.text =
            if (presenter.isFollowingUser()) getString(R.string.unfollow) else getString(R.string.follow)

        textViewFollowersQuantity.text = followersQuantity
    }

    override fun removeComment(comment: Comment) {
        val newPostQuantity = textViewPostsQuantity.text.toString()
        textViewPostsQuantity.text = (newPostQuantity.toInt() - 1).toString()

        val index = commentAdapter.getItems().indexOfFirst { it.id == comment.id }
        commentAdapter.removeItem(index)
    }

    override fun navigateToUserData() {
        val action = ProfileFragmentDirections.actionGoToUserDataFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun navigateToContacts(userId: String, myFollowings: Boolean, myFollowers: Boolean) {
        val action = ProfileFragmentDirections.actionGoToContactsFragment(userId, myFollowings, myFollowers)
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun navigateToLocationDetail(location: Location) {
        val action = ProfileFragmentDirections.actionGoToLocationDetailFragment(location)
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setupRecyclerView() {
        recyclerViewComments.layoutManager = LinearLayoutManager(activity)
        recyclerViewComments.adapter = commentAdapter

        commentAdapter.setLoaderVisible(true)

        commentAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Comment> {
            override fun onItemClick(item: Comment, position: Int, view: View) {
                if (presenter.isPersonal()) {
                    MaterialDialog(context!!, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                        cornerRadius(16f)

                        listItems(
                            items = listOf(
                                getString(R.string.watch),
                                getString(R.string.remove)
                            )
                        ) { _, index, text ->
                            when (index) {
                                0 -> {
                                    item.location?.let { location ->
                                        presenter.locationClicked(location)
                                    }
                                }
                                1 -> {
                                    presenter.removeClicked(item)
                                }
                            }
                        }
                    }
                } else {
                    item.location?.let { location ->
                        presenter.locationClicked(location)
                    }
                }
            }

        })
    }

    private fun setupError() {
        errorLayout.buttonReload.setOnClickListener {
            commentAdapter.removeAllItems()
            presenter.reloadDataClicked()
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_profile
}
