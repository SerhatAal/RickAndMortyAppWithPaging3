package com.example.rickandmortyappwithpaging3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmortyappwithpaging3.api.ApiService
import com.example.rickandmortyappwithpaging3.paging.RickAndMortyPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val PAGER_PAGE_SIZE = 20

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val pagedList = Pager(PagingConfig(pageSize = PAGER_PAGE_SIZE)) {
        RickAndMortyPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)
}