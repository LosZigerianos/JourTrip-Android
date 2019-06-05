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
import com.afollestad.materialdialogs.MaterialDialog
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
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
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

    private var LastLoadPage: Int = 1
    private var totalPages: Int? = null

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

        setupRecyclerView()
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

        buttonFollow.text =
            if (presenter.isFollowingUser()) getString(R.string.unfollow) else getString(R.string.follow)

        textViewFollowingQuantity.text = profile.following?.toString()
        textViewFollowersQuantity.text = profile.followers?.toString()
        textViewPostsQuantity.text = profile.commentsCount.toString()

        picasso
            .load(profile.photo)
            .placeholder(R.drawable.ic_profile_placeholder)
            .error(R.drawable.ic_profile_placeholder)
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

        scrollViewProfile.setOnBottomReachedListener {
            toast("setOnBottomReachedListener")
            // TODO: CARGAR MAS COMENTARIOS
            //loadMore()
        }

        profile.comments?.let { comments ->
            commentAdapter.setItems(comments)
        }
    }

    override fun showErrorMessage() {
        view?.let { viewFrag ->
            Snackbar.make(viewFrag, getString(R.string.error_request), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun followUserChanged() {
        buttonFollow.text =
            if (presenter.isFollowingUser()) getString(R.string.unfollow) else getString(R.string.follow)
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

        commentAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Comment> {
            override fun onItemClick(item: Comment, position: Int, view: View) {
                item.location?.let { location ->
                    presenter.locationClicked(location)
                }
            }

        })
    }

    /*private fun loadMore() {
        val locations = listOf(
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", "")
        )

        deadlineAdapter!!.addItems(locations)
    }*/

    override fun getLayoutResource(): Int = R.layout.fragment_profile
}
