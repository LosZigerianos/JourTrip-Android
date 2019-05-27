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
import com.squareup.picasso.Picasso

import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.UserProfile
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import com.zigerianos.jourtrip.presentation.scenes.home.DeadlineAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject


class ProfileFragment : BaseFragment<IProfilePresenter.IProfileView, IProfilePresenter>(),
    IProfilePresenter.IProfileView {

    private val mainPresenter by inject<IProfilePresenter>()
    private val picasso by inject<Picasso>()

    private var LastLoadPage: Int = 1
    private var totalPages: Int? = null

    private var deadlineAdapter: DeadlineAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
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
    }

    override fun setupViews() {
        // TODO: VERIFICAR TITULO FINAL
        toolbar.toolbarTitle.text = "Profile"
        toolbar.toolbarImage.visibility = View.VISIBLE
        toolbar.toolbarImage.setOnClickListener {
            scrollViewProfile.fullScroll(ScrollView.FOCUS_UP)
        }

        activity?.bottomNavigationView?.visibility = View.VISIBLE

        // TODO: IMPLEMENTAR
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


        textViewFollowingQuantity.text = profile.following?.toString()
        textViewFollowersQuantity.text = profile.followers?.toString()
        textViewPostsQuantity.text = profile.comments?.count().toString()

        picasso
            .load(profile.photo)
            .placeholder(R.drawable.ic_profile_placeholder)
            .error(R.drawable.ic_profile_placeholder)
            .into(imageViewUser)


        scrollViewProfile.setOnBottomReachedListener {
            toast("setOnBottomReachedListener")
            loadMore()
        }


        // TODO: REEMPLAZAR POR COMENTARIOS
        val locations = listOf(
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", "")
        )
        deadlineAdapter!!.setItems(locations)
    }

    override fun navigateToUserData() {
        val action = ProfileFragmentDirections.actionGoToUserDataFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setupRecyclerView() {
        recyclerViewComments.layoutManager = LinearLayoutManager(activity)
        deadlineAdapter = DeadlineAdapter(activity!!)
        recyclerViewComments.adapter = deadlineAdapter

        deadlineAdapter!!.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Location> {
            override fun onItemClick(item: Location, position: Int, view: View) {
                //presenter.locationSelected(position)
                toast("Location selected $position")
            }

        })
    }

    private fun loadMore() {
        val locations = listOf(
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", "")
        )

        deadlineAdapter!!.addItems(locations)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_profile
}
