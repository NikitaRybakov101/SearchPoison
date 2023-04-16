package com.example.searchpoison.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.searchpoison.R
import com.example.searchpoison.databinding.DetailsPoisonFragmentBinding
import com.example.searchpoison.di.VIEW_MODEL_DETAILS
import com.example.searchpoison.repository.dataSourse.BASE_URL_API
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.ui.baseView.BaseViewBindingFragment
import com.example.searchpoison.ui.viewModel.ViewModelFragmentDetailsPoison
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import com.example.searchpoison.utils.hide
import com.example.searchpoison.utils.show
import com.example.searchpoison.utils.showToast
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DetailsPoisonFragment : BaseViewBindingFragment<DetailsPoisonFragmentBinding>(DetailsPoisonFragmentBinding::inflate) {

    companion object {
        const val KEY_ID = "KEY"
    }

    private val viewModel : ViewModelFragmentDetailsPoison by viewModel(named(VIEW_MODEL_DETAILS))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(KEY_ID)?.let { poisonCollect(it) }
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

        imageItem.load(BASE_URL_API + poison.imageUri) {
            placeholder(ColorDrawable(Color.TRANSPARENT))
        }

        iconDetailsPoison.load(BASE_URL_API + poison.categories.iconUrl) {
            placeholder(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun arrowBack() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bookmark() = with(binding) {

        var bookmarkIsActive = false
        bookmark.setOnClickListener {

            if (bookmarkIsActive) {
                bookmarkIsActive = false
                bookmark.load(R.drawable.baseline_star_border_24)
            } else {
                bookmarkIsActive = true
                bookmark.load(R.drawable.baseline_star_border_24_active)
            }
        }
    }

}