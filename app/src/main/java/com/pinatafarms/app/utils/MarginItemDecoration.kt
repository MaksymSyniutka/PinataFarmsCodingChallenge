package com.pinatafarms.app.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
        private val horizontalMargins: Int,
        private val verticalMargins: Int,
        private val applyToFirstItem: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (!applyToFirstItem) {
            parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION && it != 0 }?.let { applyMargins(outRect) }
        } else {
            applyMargins(outRect)
        }
    }

    private fun applyMargins(outRect: Rect) {
        outRect.apply {
            top = verticalMargins
            left = horizontalMargins
            right = horizontalMargins
        }
    }
}