package com.zigerianos.jourtrip.presentation.scenes.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager

import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import com.zigerianos.jourtrip.presentation.scenes.home.DeadlineAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import android.view.KeyEvent.KEYCODE_BACK
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.R.id.home
import android.content.Intent


class SearchFragment : BaseFragment<ISearchPresenter.ISearchView, ISearchPresenter>(),
    ISearchPresenter.ISearchView {

    private val mainPresenter by inject<ISearchPresenter>()

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
        toolbar.toolbarTitle.text = "Searching"
        toolbar.toolbarImage.visibility = View.VISIBLE
        toolbar.toolbarImage.setOnClickListener {
            scrollViewSearching.fullScroll(ScrollView.FOCUS_UP)
        }

        activity?.bottomNavigationView?.visibility = View.VISIBLE

        textInputLayoutSearching.editText?.setOnFocusChangeListener { view, boolean ->
            if (!boolean && isVisible) {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(container.windowToken, 0)
                activity?.bottomNavigationView?.visibility = View.VISIBLE
            } else {
                activity?.bottomNavigationView?.visibility = View.GONE
            }
        }

        buttonSearching.setOnClickListener {
            textInputLayoutSearching.clearFocus()
        }

        /*view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    toast("HA PULSADO ATRAS")
                    return true
                }
                return false
            }
        })*/

        // TODO: IMPLEMENTAR
        setupRecyclerView()
    }

    override fun stateLoading() {
        //errorLayout.visibility = View.GONE
        scrollViewSearching.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        //errorLayout.visibility = View.GONE
        scrollViewSearching.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateError() {
        scrollViewSearching.visibility = View.GONE
        progressBar.visibility = View.GONE
        //errorLayout.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        recyclerViewSearching.layoutManager = GridLayoutManager(activity, 2)
        /*deadlineAdapter = DeadlineAdapter(activity!!)
        recyclerViewSearching.adapter = deadlineAdapter

        deadlineAdapter!!.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Location> {
            override fun onItemClick(item: Location, position: Int, view: View) {
                //presenter.locationSelected(position)
                toast("Location selected $position")
            }

        })*/
    }

    override fun getLayoutResource(): Int = R.layout.fragment_search
}
