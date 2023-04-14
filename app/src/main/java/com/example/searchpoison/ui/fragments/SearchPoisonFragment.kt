package com.example.searchpoison.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchpoison.databinding.SearchPoisonFragmentBinding
import com.example.searchpoison.di.REPOSITORY
import com.example.searchpoison.di.VIEW_MODEL
import com.example.searchpoison.repository.repositoryImpl.RepositoryImpl
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.ui.baseView.BaseViewBindingFragment
import com.example.searchpoison.ui.fragments.recycler.CallbackItem
import com.example.searchpoison.ui.fragments.recycler.RecyclerPoison
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import com.example.searchpoison.ui.viewModel.ViewModelFragmentSearchPoison
import com.example.searchpoison.utils.hide
import com.example.searchpoison.utils.show
import com.example.searchpoison.utils.showToast
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchPoisonFragment : BaseViewBindingFragment<SearchPoisonFragmentBinding>(SearchPoisonFragmentBinding::inflate) , CallbackItem {

    companion object {
        private const val SPAN_COUNT = 2
    }

    private val recyclerPoison = RecyclerPoison(listOf(),this)

    private val repository : RepositoryImpl by inject(named(REPOSITORY))
    private val viewModel : ViewModelFragmentSearchPoison by viewModel(named(VIEW_MODEL))  {
        parametersOf(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListPoisonCollect("шанс")
        initRecyclerPoisons()
        searchPoison()
    }

    private fun getListPoisonCollect(search : String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getListPoisonFlow(search)
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
            is StateData.Success -> {
                loadingProgressbar.hide()

                setListPoisons(stateData.listPoisons)
            }
            is StateData.Error -> {
                loadingProgressbar.hide()

                context?.showToast(stateData.error.message)
            }
        }
    }

    private fun initRecyclerPoisons() = with(binding) {
        recycler.layoutManager = GridLayoutManager(requireContext(),SPAN_COUNT, RecyclerView.VERTICAL,false)
        recycler.adapter = recyclerPoison
    }

    private fun setListPoisons(listPoison : List<Poison>)  {
        recyclerPoison.setNewListPoison(listPoison)
    }

    override fun itemClicked(idItem : String) {

    }
    private fun searchPoison() {
        binding.searchViewPoison.isSubmitButtonEnabled = true
        binding.searchViewPoison.setOnQueryTextListener (object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    getListPoisonCollect(query.trim())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

}