package com.example.searchpoison.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchpoison.R
import com.example.searchpoison.databinding.SearchPoisonFragmentBinding
import com.example.searchpoison.di.VIEW_MODEL_SEARCH
import com.example.searchpoison.ui.baseView.BaseViewBindingFragment
import com.example.searchpoison.ui.fragments.DetailsPoisonFragment.Companion.KEY_ID
import com.example.searchpoison.ui.fragments.recycler.CallbackItem
import com.example.searchpoison.ui.fragments.recycler.PagingAdapterRecyclerPoison
import com.example.searchpoison.ui.viewModel.ViewModelFragmentSearchPoison
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SearchPoisonFragment : BaseViewBindingFragment<SearchPoisonFragmentBinding>(SearchPoisonFragmentBinding::inflate) , CallbackItem {

    companion object {
        private const val SPAN_COUNT = 2
    }

    private val adapterPoison by lazy(LazyThreadSafetyMode.NONE) {
        PagingAdapterRecyclerPoison(this)
    }

    private val viewModel : ViewModelFragmentSearchPoison by viewModel(named(VIEW_MODEL_SEARCH))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerPoisons()
        listPoisonCollect()

        setTextInfo()
        searchPoison()
    }

    private fun listPoisonCollect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPoisonsFlow()
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
        findNavController().navigate(R.id.action_mainSearchBinFragment_to_detailsPoisonFragment,bundleOf(KEY_ID to idItem),
            navOptions {

                anim {
                    enter = R.anim.enter
                    exit = R.anim.exit
                    popEnter = R.anim.pop_enter
                    popExit = R.anim.pop_exit
                }
            })
    }
    private fun searchPoison() {
        binding.searchViewPoison.setOnQueryTextListener (object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    viewModel.setQuery(query.trim())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null) {
                    viewModel.setQuery(newText.trim())
                    setTextInfo(newText.trim())
                } else {
                    setTextInfo("")
                }
                return true
            }
        })
    }

    private fun setTextInfo(query: String = "") = with(binding) {
        if(query.isEmpty()) {
            textInfo.text = getString(R.string.input_name_drag)
        } else {
            textInfo.text = getString(R.string.result_search,query)
        }
    }

}