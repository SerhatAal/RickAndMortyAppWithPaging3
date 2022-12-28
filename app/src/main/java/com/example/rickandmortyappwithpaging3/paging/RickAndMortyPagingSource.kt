package com.example.rickandmortyappwithpaging3.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyappwithpaging3.api.ApiService
import com.example.rickandmortyappwithpaging3.model.CharacterItem
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1
private const val TO_PREV_OR_NEXT_PAGE_KEY = 1

class RickAndMortyPagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, CharacterItem>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(TO_PREV_OR_NEXT_PAGE_KEY)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(TO_PREV_OR_NEXT_PAGE_KEY)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val response = apiService.getAllCharacters(currentPage)
            val data = response.body()?.results ?: emptyList()
            val responseData = mutableListOf<CharacterItem>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage.minus(TO_PREV_OR_NEXT_PAGE_KEY),
                nextKey = currentPage.plus(TO_PREV_OR_NEXT_PAGE_KEY)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}