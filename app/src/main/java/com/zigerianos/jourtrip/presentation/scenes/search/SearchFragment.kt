package com.zigerianos.jourtrip.presentation.scenes.search

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.zigerianos.jourtrip.R

import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.presentation.base.ItemClickAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.utils.CheckPermission
import com.zigerianos.jourtrip.utils.NearbyAdapter
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar
import java.util.*


class SearchFragment : BaseFragment<ISearchPresenter.ISearchView, ISearchPresenter>(),
    ISearchPresenter.ISearchView {

    private val mainPresenter by inject<ISearchPresenter>()
    private val nearbyAdapter by inject<NearbyAdapter>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mUnregistrar: Unregistrar
    private val imm by lazy {
        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        activity?.let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
        }
    }

    override fun onDestroyView() {
        mUnregistrar.unregister()

        super.onDestroyView()

        activity?.bottomNavigationView?.visibility = View.VISIBLE
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()

        requestLocationPermission()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CheckPermission.TAG_PERMISSION_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    requestLocationPermission()
                } else {
                    // permission denied
                }
                return
            }

            else -> {
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setupViews() {
        toolbar.toolbarImage.visibility = View.VISIBLE
        toolbar.toolbarImage.setOnClickListener {
            scrollViewSearching.fullScroll(ScrollView.FOCUS_UP)
            activity?.bottomNavigationView?.visibility = View.VISIBLE
        }

        textInputLayoutSearching.editText?.setOnFocusChangeListener { view, boolean ->
            if (!boolean && isVisible) {
                imm.hideSoftInputFromWindow(container.windowToken, 0)
                activity?.bottomNavigationView?.visibility = View.VISIBLE
            } else {
                activity?.bottomNavigationView?.visibility = View.GONE
            }
        }

        setupSearch()
        //textInputLayoutSearching.clearFocus()

        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(activity!!) { isOpen ->
            //activity?.bottomNavigationView?.visibility = if (isOpen) View.GONE else View.VISIBLE
            if (!isOpen && isVisible) {
                activity?.bottomNavigationView?.visibility = View.VISIBLE
                // TODO: CONTROLAR QUE PETA AL BUSCAR!!
                editTextSearch.clearFocus()
            }
        }

        setupRecyclerView()

        scrollViewSearching.setOnScrolledDownListener {
            activity?.bottomNavigationView?.visibility = View.VISIBLE
            editTextSearch.clearFocus()
        }

        scrollViewSearching.setOnScrolledUpListener {
            activity?.bottomNavigationView?.visibility = View.VISIBLE
            editTextSearch.clearFocus()
        }

        scrollViewSearching.setOnBottomReachedListener {
            presenter.loadMoreData()
        }
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

    override fun loadLocations(locations: List<Location>, forMorePages: Boolean) {
        if (locations.isEmpty()) {
            nearbyAdapter.setLoaderVisible(false)
            scrollViewSearching.onBottomReachedListener = null
            return
        }

        nearbyAdapter.setLoaderVisible(forMorePages)
        if (!forMorePages) scrollViewSearching.onBottomReachedListener = null
        nearbyAdapter.addItems(locations)
    }

    override fun showMessageEmpty() {
        toast(getString(R.string.no_results_for_searching))
    }

    override fun navigateToLocationDetail(location: Location) {
        val action = SearchFragmentDirections.actionGoToLocationDetailFragment(location)
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setupRecyclerView() {
        //recyclerViewSearching.layoutManager = GridLayoutManager(activity, 2)
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = (object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (nearbyAdapter.getItemViewType(position)) {
                    nearbyAdapter.TYPE_ITEM -> 1
                    nearbyAdapter.TYPE_LOADER -> 2
                    else -> 0
                }
            }
        })
        recyclerViewSearching.layoutManager = gridLayoutManager
        recyclerViewSearching.adapter = nearbyAdapter

        nearbyAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Location> {
            override fun onItemClick(item: Location, position: Int, view: View) {
                activity?.bottomNavigationView?.visibility = View.VISIBLE
                presenter.locationClicked(item)
            }
        })
    }

    private fun setupSearch() {
        editTextSearch.addTextChangedListener( object : TextWatcher {
            var timer = Timer()

            override fun afterTextChanged(p0: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        activity?.runOnUiThread {
                            if (editTextSearch.text!!.trim().isNotEmpty() && (editTextSearch.text!!.length > 2)) {
                                nearbyAdapter.removeAllItems()
                                nearbyAdapter.setLoaderVisible(true)

                                presenter.searchLocationByName(editTextSearch.text.toString())
                            }
                        }
                    }
                }, 1000)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)  = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer.cancel()
            }
        })
    }

    private fun requestLocationPermission() {
        activity?.let { act ->
            if (CheckPermission.checkPermission(act, Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (CheckPermission.checkPermission(act, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    fusedLocationClient.lastLocation?.addOnSuccessListener(act) { location ->
                        location?.let {
                            //toast("Latitude: ${location.latitude} / Longitude: ${location.longitude}")
                            presenter.localizedUser(location.latitude.toString(), location.longitude.toString())
                        }
                    }
                }
            } else {
                CheckPermission.requestPermission(
                    this,
                    CheckPermission.TAG_PERMISSION_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    CheckPermission.TAG_MESSAGE_PERMISSION_LOCATION
                )
            }
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_search
}
