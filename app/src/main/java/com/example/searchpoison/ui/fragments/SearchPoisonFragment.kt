package com.example.searchpoison.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchpoison.R
import com.example.searchpoison.databinding.SearchPoisonFragmentBinding
import com.example.searchpoison.di.VIEW_MODEL_SEARCH
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.ui.baseView.BaseViewBindingFragment
import com.example.searchpoison.ui.fragments.recycler.CallbackItem
import com.example.searchpoison.ui.fragments.recycler.PagingAdapterRecyclerPoison
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import com.example.searchpoison.ui.viewModel.ViewModelFragmentSearchPoison
import com.example.searchpoison.utils.hide
import com.example.searchpoison.utils.show
import com.example.searchpoison.utils.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchPoisonFragment : BaseViewBindingFragment<SearchPoisonFragmentBinding>(SearchPoisonFragmentBinding::inflate) , CallbackItem {

    companion object {
        private const val SPAN_COUNT = 2
    }

    private val adapterPoison by lazy(LazyThreadSafetyMode.NONE) {
        PagingAdapterRecyclerPoison(listOf(),this)
    }

   // private val repository : RepositoryImpl by inject(named(REPOSITORY))
    private val viewModel : ViewModelFragmentSearchPoison by viewModel(named(VIEW_MODEL_SEARCH))  {
        parametersOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerPoisons()

        listPoisonCollect()
        searchPoison()
    }

    private fun listPoisonCollect() {
        viewModel.setQuery("Шанс")

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listPoisonFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest(adapterPoison::submitData)
        }
    }

    private fun initRecyclerPoisons() = with(binding) {
        recycler.layoutManager = GridLayoutManager(requireContext(),SPAN_COUNT, RecyclerView.VERTICAL,false)
        recycler.adapter = adapterPoison

        adapterPoison.addLoadStateListener { state ->
            recycler.isVisible = state.refresh != LoadState.Loading
            loadingProgressbar.isVisible = state.refresh == LoadState.Loading
        }
    }

    override fun itemClicked(idItem : String) {
        viewModel.setQuery("")

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer,DetailsPoisonFragment.getFragmentDetailsPoison(idItem))
            .addToBackStack("MainFragment")
            .commit()
    }
    private fun searchPoison() {
        binding.searchViewPoison.setOnQueryTextListener (object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null) {
                    viewModel.setQuery(newText.trim())
                }
                return true
            }
        })
    }

}