package com.pinatafarms.app.personlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.pinatafarms.app.databinding.FragmentPersonListBinding
import com.pinatafarms.app.utils.navigateSafely
import com.pinatafarms.data.AppDispatchers
import com.pinatafarms.domain.entities.Person
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PersonListFragment : Fragment(), KoinComponent {
    private lateinit var binding: FragmentPersonListBinding

    private val appDispatchers: AppDispatchers by inject()
    private val personListViewModel: PersonListViewModel by viewModel()
    private lateinit var personListRecyclerAdapter: PersonListRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPersonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewData = PersonListViewData(isLoading = true)

        personListRecyclerAdapter = PersonListRecyclerAdapter { handlePersonListClick(it) }
        binding.recyclerView.adapter = personListRecyclerAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                personListViewModel.personList.onEach { result ->
                    binding.viewData = PersonListViewData(isLoading = false)
                    result.onSuccess {
                        personListRecyclerAdapter.submit(it)
                    }.onFailure {
                        Toast.makeText(
                            requireContext(),
                            "An error occurred while running face detection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }.flowOn(appDispatchers.MainImmediate).launchIn(this)
            }
        }
    }

    private fun handlePersonListClick(person: Person) {
        findNavController().navigateSafely(
            PersonListFragmentDirections.redirectToPersonDetailsScreen(person)
        )
    }
}