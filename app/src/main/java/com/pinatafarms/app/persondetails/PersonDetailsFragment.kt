package com.pinatafarms.app.persondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.pinatafarms.app.R
import com.pinatafarms.app.databinding.FragmentPersonDetailsBinding
import com.pinatafarms.data.AppDispatchers
import com.pinatafarms.domain.entities.PersonFaceVisualizationResults
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PersonDetailsFragment : Fragment(), KoinComponent {
    private lateinit var binding: FragmentPersonDetailsBinding

    private val arguments: PersonDetailsFragmentArgs by navArgs()

    private val appDispatchers: AppDispatchers by inject()
    private val personDetailsViewModel: PersonDetailsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personDetailsViewModel.getPersonFaceVisualizationResults(arguments.personDetails)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                personDetailsViewModel.faceVisualizationResults
                    .onEach { renderFaceDetectionResults(it) }
                    .flowOn(appDispatchers.MainImmediate)
                    .launchIn(this)
            }
        }
    }

    private fun renderFaceDetectionResults(personFaceVisualizationResults: PersonFaceVisualizationResults?) {
        when (personFaceVisualizationResults) {
            is PersonFaceVisualizationResults.SuccessfulPersonFaceVisualization -> {
                binding.personViewData = PersonDetailsViewData(
                    thumbnail = personFaceVisualizationResults.bitmap,
                    showErrorMessage = false
                )
            }
            is PersonFaceVisualizationResults.FaceNotDetectedError -> {
                binding.personViewData = PersonDetailsViewData(
                    thumbnail = personFaceVisualizationResults.bitmap,
                    showErrorMessage = true,
                    errorMessage = requireContext().getString(R.string.face_not_detected_error_message)
                )
            }
            is PersonFaceVisualizationResults.FaceOutOfBoundsError -> {
                binding.personViewData = PersonDetailsViewData(
                    thumbnail = personFaceVisualizationResults.bitmap,
                    showErrorMessage = true,
                    errorMessage = requireContext().getString(R.string.detected_face_out_of_bounds_error_message)
                )
            }
            null -> {
                Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_LONG).show()
            }
        }
    }
}