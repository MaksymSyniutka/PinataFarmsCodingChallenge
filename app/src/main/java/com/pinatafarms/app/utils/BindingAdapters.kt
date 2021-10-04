package com.pinatafarms.app.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlin.math.roundToInt

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.isVisible = visible
}

@BindingAdapter("loadBitmap")
fun loadImageBitmap(imageView: ImageView, bitmap: Bitmap?) {
    bitmap?.let { imageView.load(bitmap) }
}

@BindingAdapter(value = ["itemHorizontalMargin", "itemVerticalMargin", "applyMarginsToFirstItem"], requireAll = false)
fun setItemMargin(recyclerView: RecyclerView,
                  horizontalMargin: Float = 0f,
                  verticalMargin: Float = 0f,
                  applyMarginsToFirstItem: Boolean = false
) {
    recyclerView.addItemDecoration(
        MarginItemDecoration(
            horizontalMargin.roundToInt(), verticalMargin.roundToInt(), applyMarginsToFirstItem
        )
    )
}