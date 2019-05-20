package com.zigerianos.jourtrip.presentation.scenes.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.koin.android.ext.android.inject

class ProfileFragment : BaseFragment<IProfilePresenter.IProfileView, IProfilePresenter>(),
    IProfilePresenter.IProfileView {

    private val mainPresenter by inject<IProfilePresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setupViews() {
        // TODO: VERIFICAR TITULO FINAL
        toolbar.toolbarTitle.text = "Profile"

        activity?.bottomNavigationView?.visibility = View.VISIBLE

        // TODO: IMPLEMENTAR
    }

    override fun stateLoading() {
        // TODO: IMPLEMENTAR
    }

    override fun stateData() {
        // TODO: IMPLEMENTAR
    }

    override fun stateError() {
        // TODO: IMPLEMENTAR
    }

    override fun getLayoutResource(): Int = R.layout.fragment_profile
}
