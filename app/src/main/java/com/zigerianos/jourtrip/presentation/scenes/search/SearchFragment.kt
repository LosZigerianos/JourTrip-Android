package com.zigerianos.jourtrip.presentation.scenes.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
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


class SearchFragment : BaseFragment<ISearchPresenter.ISearchView, ISearchPresenter>(),
    ISearchPresenter.ISearchView {

    private val mainPresenter by inject<ISearchPresenter>()

    private val nearbyAdapter by inject<NearbyAdapter>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        activity?.let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
        }
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
                    // TODO:
                    requestLocationPermission()
                } else {
                    // permission denied
                }
                return
            }

            else -> { }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setupViews() {
        toolbar.toolbarTitle.text = getString(R.string.search)
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

            if (editTextSearch.text.toString().isNotEmpty()) {
                presenter.searchLocationByNameClicked(editTextSearch.text.toString())
            }

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

    override fun loadLocations(locations: List<Location>) {
        nearbyAdapter.setItems(locations)
    }

    private fun setupRecyclerView() {
        recyclerViewSearching.layoutManager = GridLayoutManager(activity, 2)
        recyclerViewSearching.adapter = nearbyAdapter

        nearbyAdapter.setOnItemClickListener(object : ItemClickAdapter.OnItemClickListener<Location> {
            override fun onItemClick(item: Location, position: Int, view: View) {
                //presenter.locationSelected(position)
                toast("Location selected $position")
            }
        })
    }

    private fun requestLocationPermission() {
        activity?.let { act ->
            if (CheckPermission.checkPermission(act, Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (CheckPermission.checkPermission(act, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    fusedLocationClient.lastLocation?.addOnSuccessListener(act) { location ->
                        location?.let {
                            toast("Latitude: ${location.latitude} / Longitude: ${location.longitude}")
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

    /*@SuppressLint("MissingPermission")
    private fun getLocation() {
        mLocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGps || hasNetwork) {
            if (hasGps) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000.toLong(), 0.toFloat(), object :
                    LocationListener {
                    override fun onLocationChanged(location: android.location.Location?) {
                        Log.d("PATATA", "LOCATION: ${location.toString()}")
                    }

                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

                    override fun onProviderEnabled(p0: String?) {}

                    override fun onProviderDisabled(p0: String?) {}
                })

                return
            }

            if (hasNetwork) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000.toLong(), 0.toFloat(), object :
                    LocationListener {
                    override fun onLocationChanged(location: android.location.Location?) {
                        Log.d("PATATA", "LOCATION: ${location.toString()}")
                    }

                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

                    override fun onProviderEnabled(p0: String?) {}

                    override fun onProviderDisabled(p0: String?) {}
                })

                return
            }
        }
    }*/

    override fun getLayoutResource(): Int = R.layout.fragment_search
}
