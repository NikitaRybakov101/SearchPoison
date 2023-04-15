package com.example.searchpoison.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.searchpoison.R
import com.example.searchpoison.databinding.DetailsPoisonFragmentBinding
import com.example.searchpoison.di.REPOSITORY
import com.example.searchpoison.di.VIEW_MODEL_DETAILS
import com.example.searchpoison.repository.dataSourse.BASE_URL_API
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.repository.repositoryImpl.RepositoryImpl
import com.example.searchpoison.ui.baseView.BaseViewBindingFragment
import com.example.searchpoison.ui.viewModel.ViewModelFragmentDetailsPoison
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import com.example.searchpoison.utils.hide
import com.example.searchpoison.utils.show
import com.example.searchpoison.utils.showToast
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class DetailsPoisonFragment : BaseViewBindingFragment<DetailsPoisonFragmentBinding>(DetailsPoisonFragmentBinding::inflate) {

    companion object {
        private const val KEY_ARGUMENTS = "KEY"

        fun getFragmentDetailsPoison(idPoison : String) : DetailsPoisonFragment{

            val fragment = DetailsPoisonFragment()
            val bundle = Bundle()

            bundle.putString(KEY_ARGUMENTS,idPoison)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val repository : RepositoryImpl by inject(named(REPOSITORY))
    private val viewModel : ViewModelFragmentDetailsPoison by viewModel(named(VIEW_MODEL_DETAILS))  {
        parametersOf(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(KEY_ARGUMENTS)?.let { poisonCollect(it) }
        arrowBack()
        bookmark()
    }

    private fun poisonCollect(idPoison : String) {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.getPoisonFlow(idPoison)
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { data ->
                    render(data)
                }
        }
    }

    private fun render(stateData: StateData) = with(binding) {
        when(stateData) {
            is StateData.Loading -> {
                loadingProgressbar.show()
            }
            is StateData.SuccessItemPoison -> {
                loadingProgressbar.hide()

                setPoison(stateData.poison)
            }
            is StateData.Error -> {
                loadingProgressbar.hide()

                context?.showToast(stateData.error.message)
            }
            else -> {}
        }
    }

    private fun setPoison(poison: Poison) {
        setDescription(poison)
        setImage(poison)
    }

    private fun setDescription(poison: Poison) = with(binding) {
        header.text = poison.name
        descriptionItem.text = poison.description
    }

    private fun setImage(poison: Poison) = with(binding) {
        Glide.with(imageItem.context)
            .load(BASE_URL_API + poison.imageUri)
            .into(imageItem)

        Glide.with(iconDetailsPoison.context)
            .load(BASE_URL_API + poison.categories.iconUrl)
            .into(iconDetailsPoison)
    }

    private fun arrowBack() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun bookmark() = with(binding) {

        var bookmarkIsActive = false
        bookmark.setOnClickListener {

            if (bookmarkIsActive) {
                bookmarkIsActive = false
                bookmark.setImageResource(R.drawable.baseline_star_border_24)
            } else {
                bookmarkIsActive = true
                bookmark.setImageResource(R.drawable.baseline_star_24)
            }
        }
    }

}