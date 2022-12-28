package com.example.rickandmortyappwithpaging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmortyappwithpaging3.paging.RickAndMortyPagingAdapter
import com.example.rickandmortyappwithpaging3.databinding.ActivityMainBinding
import com.example.rickandmortyappwithpaging3.viewmodel.RickAndMortyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val characterListAdapter by lazy {
        RickAndMortyPagingAdapter()
    }
    private val viewModel: RickAndMortyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRv()
        collectPagingData()
    }

    private fun collectPagingData() {
        lifecycleScope.launch {
            viewModel.pagedList.collect { pagingData ->
                characterListAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupRv() {
        val binding = binding
        binding.rvCharacterList.apply {
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )

            adapter = characterListAdapter
            setHasFixedSize(true)
        }
    }
}