package com.example.searchpoison.repository.pager

import android.accounts.NetworkErrorException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchpoison.repository.dataSourse.NOT_FOUND
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.repository.repositoryImpl.InterfaceRepository
import com.example.searchpoison.ui.fragments.recycler.PagingAdapterRecyclerPoison.Companion.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PageSourcePoison(
    private val repository : InterfaceRepository,
    private val searchQuery : String

) : PagingSource<Int, Poison>() {

    override fun getRefreshKey(state: PagingState<Int, Poison>): Int? {

        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Poison> {
        if (searchQuery.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page : Int = params.key ?: INITIAL_PAGE_NUMBER
        val pageSize : Int = params.loadSize.coerceAtMost(PAGE_SIZE)
        val offset = page * pageSize

        runCatching {
            withContext(Dispatchers.IO) {
                repository.getListPoison(searchQuery,offset,pageSize)
            }
        }.onSuccess { listPoison ->

            return if (listPoison != null) {

                val nextKey = if(listPoison.size < pageSize) null else page + 1
                val prevKey = if(page == 0) null else page - 1

                LoadResult.Page(listPoison,prevKey,nextKey)
            } else {
                LoadResult.Error(NetworkErrorException(NOT_FOUND))
            }
        }.onFailure {
            return LoadResult.Error(NetworkErrorException(it.message))
        }
        return LoadResult.Error(NetworkErrorException())
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 0
    }
}