package com.pinatafarms.app.persondetails

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.contains
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.pinatafarms.app.R
import com.pinatafarms.app.databinding.FragmentPersonDetailsBinding
import com.pinatafarms.data.utils.centerPointF
import com.pinatafarms.data.source.image.PersonBitmapLruCacheDataSource
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PersonDetailsFragment : Fragment(), KoinComponent {
    private lateinit var binding: FragmentPersonDetailsBinding

    private val arguments: PersonDetailsFragmentArgs by navArgs()
    private val bitmapLruCacheDataSource: PersonBitmapLruCacheDataSource by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            renderFaceDetectionResults()
        }
    }

    private suspend fun renderFaceDetectionResults() {
        val personDetails = arguments.personDetails
        bitmapLruCacheDataSource.saveOrGetBitmapFromCache(personDetails.imagePath)?.let { bitmap ->
            val faceBounds = personDetails.faceBounds
            if (personDetails.hasFaceBeenDetected && faceBounds != null) {
                if (!bitmap.contains(faceBounds.centerPointF)) {
                    binding.personViewData = PersonDetailsViewData(
                        thumbnail = bitmap,
                        showErrorMessage = true,
                        errorMessage = requireContext().getString(R.string.detected_face_out_of_bounds_error_message)
                    )
                } else {
                    val paint = Paint().apply {
                        strokeWidth = 5f
                        color = Color.RED
                        style = Paint.Style.STROKE
                    }
                    val mutableBitmap = bitmap.copy(
                        Bitmap.Config.RGB_565, true
                    ).applyCanvas { drawRect(faceBounds, paint) }
                    binding.personViewData = PersonDetailsViewData(
                        thumbnail = mutableBitmap,
                        showErrorMessage = false
                    )
                }
            } else {
                binding.personViewData = PersonDetailsViewData(
                    thumbnail = bitmap,
                    showErrorMessage = true,
                    errorMessage = requireContext().getString(R.string.face_not_detected_error_message)
                )
            }
        }
    }
}