package com.zigerianos.jourtrip.presentation.scenes.contacts


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import com.zigerianos.jourtrip.utils.ContactAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.errorLayout
import kotlinx.android.synthetic.main.fragment_contacts.progressBar
import kotlinx.android.synthetic.main.fragment_contacts.toolbar
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject

class ContactsFragment : BaseFragment<IContactsPresenter.IContacts, IContactsPresenter>(),
    IContactsPresenter.IContacts {

    private val argFollowing: Boolean by lazy { ContactsFragmentArgs.fromBundle(arguments!!).following }
    private val argFollowers: Boolean by lazy { ContactsFragmentArgs.fromBundle(arguments!!).followers }

    private val mainPresenter by inject<IContactsPresenter>()
    private val contactAdapter by inject<ContactAdapter>()

    //private var mEndlessScrollListener = EndlessScrollListener {toast("Hola hola")}

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        presenter.setFollowing(argFollowing)
        presenter.setFollowers(argFollowers)
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
    }

    override fun stateLoading() {
        errorLayout.visibility = View.GONE
        recyclerViewContacts.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        errorLayout.visibility = View.GONE
        recyclerViewContacts.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateError() {
        recyclerViewContacts.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
    }

    override fun loadUsers(users: List<User>) {
        contactAdapter.setItems(users)
    }

    override fun navigateToUserProfile(user: User) {
        toast("Implementar navegar a perfil de usuario")
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun setupRecyclerView() {
        recyclerViewContacts.layoutManager = LinearLayoutManager(activity)
        recyclerViewContacts.adapter = contactAdapter

        //mEndlessScrollListener.shouldListenForMorePages(true)
        //recyclerViewContacts.addOnScrollListener(mEndlessScrollListener)
        //contactAdapter.setLoaderVisible(true)

        contactAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<User> {
            override fun onItemClick(item: User, position: Int, view: View) {
                presenter.userClicked(item)
            }
        })
    }

    override fun getLayoutResource(): Int = R.layout.fragment_contacts
}
