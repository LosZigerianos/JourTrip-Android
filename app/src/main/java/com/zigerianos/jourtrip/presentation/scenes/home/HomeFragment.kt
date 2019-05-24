package com.zigerianos.jourtrip.presentation.scenes.home


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<IHomePresenter.IHomeView, IHomePresenter>(), IHomePresenter.IHomeView {

    private val mainPresenter by inject<IHomePresenter>()

    private val deadlineAdapter by inject<DeadlineAdapter>()

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
        toolbar.toolbarTitle.text = "Home"

        activity?.bottomNavigationView?.visibility = View.VISIBLE

        recyclerView()
    }

    override fun stateLoading() {
        // TODO: IMPLEMENTAR
    }

    override fun stateData() {
        // TODO: IMPLEMENTAR

        val locations = listOf<Location>(
            Location("", "", "", "", "", "", ""),
            Location("", "", "", "", "", "", "")
        )
        deadlineAdapter.setItems(locations)
    }

    override fun stateError() {
        // TODO: IMPLEMENTAR
    }

    private fun recyclerView() {
        recyclerViewDeadline.layoutManager = LinearLayoutManager(activity)
        recyclerViewDeadline.adapter = deadlineAdapter

        deadlineAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Location> {
            override fun onItemClick(item: Location, position: Int, view: View) {
                //presenter.locationSelected(position)
                toast("Location selected")
            }

        })
    }

    override fun getLayoutResource(): Int = R.layout.fragment_home
}
