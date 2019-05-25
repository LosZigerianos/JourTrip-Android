package com.zigerianos.jourtrip.presentation.scenes.userdata


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_data.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.koin.android.ext.android.inject


class UserDataFragment  : BaseFragment<IUserDataPresenter.IUserDataView, IUserDataPresenter>(), IUserDataPresenter.IUserDataView  {

    private val mainPresenter by inject<IUserDataPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    override fun setupViews() {
        // TODO: VERIFICAR TITULO FINAL
        toolbar.toolbarTitle.text = "User data"

        activity?.bottomNavigationView?.visibility = View.GONE

        // TODO: IMPLEMENTAR

    }

    override fun stateLoading() {
        groupUserData.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        groupUserData.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun getLayoutResource(): Int = R.layout.fragment_user_data
}
