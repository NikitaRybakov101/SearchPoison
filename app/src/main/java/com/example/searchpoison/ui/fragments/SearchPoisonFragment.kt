package com.example.searchpoison.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchpoison.R
import com.example.searchpoison.databinding.SearchPoisonFragmentBinding
import com.example.searchpoison.di.RETROFIT_IMPL
import com.example.searchpoison.di.VIEW_MODEL
import com.example.searchpoison.repository.RetrofitImpl
import com.example.searchpoison.repository.dataSourse.ResponsePoison
import com.example.searchpoison.ui.baseView.BaseViewBindingFragment
import com.example.searchpoison.ui.fragments.recycler.CallbackItem
import com.example.searchpoison.ui.fragments.recycler.RecyclerPoison
import com.example.searchpoison.ui.viewModel.dataSourse.RequestParameters
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import com.example.searchpoison.ui.viewModel.ViewModelFragmentSearchPoison
import com.example.searchpoison.utils.hide
import com.example.searchpoison.utils.show
import com.example.searchpoison.utils.showToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchPoisonFragment : BaseViewBindingFragment<SearchPoisonFragmentBinding>(SearchPoisonFragmentBinding::inflate) , CallbackItem {

    companion object {
        private const val SPAN_COUNT = 2
    }

    private var recyclerView = RecyclerPoison(ArrayList(), this)
    private val sendDataFirs = RequestParameters.sendParameterNewsApple

    private val retrofit : RetrofitImpl by inject(named(RETROFIT_IMPL))
    private val viewModel : ViewModelFragmentSearchPoison by viewModel(named(VIEW_MODEL))  {
        parametersOf(retrofit)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initListNews()

        viewModel.getListNews(sendDataFirs)
    }

    private fun initViewModel() { viewModel.getLiveData().observe(viewLifecycleOwner) { render(it) } }

    private fun render(stateData: StateData) {
        when(stateData) {
            is StateData.Loading -> {
                setEmptyRecycler()
                binding.loadingProgressbar.show()
            }
            is StateData.Success -> {
                binding.loadingProgressbar.hide()

                setListDrags(stateData.listPoisons)
            }
            is StateData.Error -> {
                binding.loadingProgressbar.hide()

                context?.showToast(getString(R.string.error_network_mess))
            }
        }
    }

    private fun initListNews() = with(binding) {
        recycler.layoutManager = GridLayoutManager(requireContext(),SPAN_COUNT, RecyclerView.VERTICAL,false)
        recycler.adapter = recyclerView
    }

    private fun setListDrags(listDrag : List<ResponsePoison>) {
        recyclerView.updateList(listDrag as ArrayList<ResponsePoison>)
    }

    private fun setEmptyRecycler() {
        recyclerView.clearListNews()
    }

    override fun readMoreUrl(url: String) {

    }

}