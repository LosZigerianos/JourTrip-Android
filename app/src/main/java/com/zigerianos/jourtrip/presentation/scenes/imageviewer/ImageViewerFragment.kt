package com.zigerianos.jourtrip.presentation.scenes.imageviewer


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_image_viewer.*
import org.koin.android.ext.android.inject


class ImageViewerFragment : BaseFragment<IImageViewerPresenter.IImageViewerView, IImageViewerPresenter>(), IImageViewerPresenter.IImageViewerView  {

    private val argImages: Array<String> by lazy { ImageViewerFragmentArgs.fromBundle(arguments!!).images }

    private val mainPresenter by inject<IImageViewerPresenter>()
    private val picasso by inject<Picasso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        presenter.setImages(argImages.toList())
    }

    override fun onDestroyView() {
        activity?.bottomNavigationView?.visibility = View.VISIBLE

        super.onDestroyView()
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    override fun setupViews() {
        toolbar.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))

        activity?.bottomNavigationView?.visibility = View.GONE
    }

    override fun loadImages(images: List<String>) {
        picasso
            .load(images.first())
            .into(imageViewDetail)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_image_viewer
}
