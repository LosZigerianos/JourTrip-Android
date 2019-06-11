package com.zigerianos.jourtrip.presentation.scenes.contacts


import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import com.zigerianos.jourtrip.utils.ContactAdapter
import com.zigerianos.jourtrip.utils.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.errorLayout
import kotlinx.android.synthetic.main.fragment_contacts.progressBar
import kotlinx.android.synthetic.main.fragment_contacts.toolbar
import kotlinx.android.synthetic.main.fragment_error_loading.view.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.koin.android.ext.android.inject

class ContactsFragment : BaseFragment<IContactsPresenter.IContacts, IContactsPresenter>(),
    IContactsPresenter.IContacts {

    private val argUserId: String? by lazy {
        arguments?.let {
            ContactsFragmentArgs.fromBundle(it).userId
        } ?: run {
            null
        }
    }
    private val argFollowing: Boolean by lazy { ContactsFragmentArgs.fromBundle(arguments!!).following }
    private val argFollowers: Boolean by lazy { ContactsFragmentArgs.fromBundle(arguments!!).followers }

    private val mainPresenter by inject<IContactsPresenter>()
    private val contactAdapter by inject<ContactAdapter>()

    private var mEndlessScrollListener = EndlessScrollListener { presenter.loadMoreData() }

    private val imm by lazy {
        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        presenter.setUserId(argUserId)
        presenter.setFollowing(argFollowing)
        presenter.setFollowers(argFollowers)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        activity?.bottomNavigationView?.visibility = View.VISIBLE
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupViews() {
        when {
            argFollowing -> toolbar.toolbarTitle.text = getString(R.string.following)
            argFollowers -> toolbar.toolbarTitle.text = getString(R.string.followers)
            else -> toolbar.toolbarTitle.text = getString(R.string.contacts)
        }

        toolbar.toolbarImage.visibility = View.GONE
        /*toolbar.toolbarImage.setOnClickListener {
            recyclerViewContacts.smoothScrollToPosition(0)
        }*/

        activity?.bottomNavigationView?.visibility = View.VISIBLE

        setupRecyclerView()
        setupError()

        editTextSearch.setOnFocusChangeListener { view, boolean ->
            activity?.bottomNavigationView?.visibility = if (boolean) View.GONE else View.VISIBLE
        }
    }

    override fun stateLoading() {
        errorLayout.visibility = View.GONE
        group_searching.visibility = if (!argFollowers && !argFollowing) View.VISIBLE else View.GONE
        recyclerViewContacts.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        errorLayout.visibility = View.GONE
        group_searching.visibility = if (!argFollowers && !argFollowing) View.VISIBLE else View.GONE
        recyclerViewContacts.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateError() {
        recyclerViewContacts.visibility = View.GONE
        group_searching.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
    }

    override fun clearItems() = contactAdapter.removeAllItems()

    override fun loadUsers(users: List<User>, forMorePages: Boolean) {
        if (users.isEmpty()) {
            contactAdapter.setLoaderVisible(false)
            mEndlessScrollListener.shouldListenForMorePages(false)
            return
        }

        contactAdapter.setLoaderVisible(forMorePages)
        mEndlessScrollListener.shouldListenForMorePages(forMorePages)
        contactAdapter.addItems(users)
    }

    override fun navigateToUserProfile(main: Boolean, user: User) {
        user.id?.let { userId ->
            val action = if (main) {
                ContactsFragmentDirections.actionGoToNavigationMainProfile(userId = userId)
            } else {
                ContactsFragmentDirections.actionGoToNavigationProfile(userId = userId)
            }

            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun setupRecyclerView() {
        recyclerViewContacts.layoutManager = LinearLayoutManager(activity)
        recyclerViewContacts.adapter = contactAdapter

        mEndlessScrollListener.shouldListenForMorePages(true)
        recyclerViewContacts.addOnScrollListener(mEndlessScrollListener)
        contactAdapter.setLoaderVisible(false)

        contactAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<User> {
            override fun onItemClick(item: User, position: Int, view: View) {
                presenter.userClicked(item)
            }
        })
    }

    private fun setupError() = errorLayout.buttonReload.setOnClickListener { presenter.reloadDataClicked() }

    override fun getLayoutResource(): Int = R.layout.fragment_contacts
}
