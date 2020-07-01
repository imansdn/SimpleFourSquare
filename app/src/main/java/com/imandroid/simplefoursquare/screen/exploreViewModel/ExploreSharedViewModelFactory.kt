package com.imandroid.simplefoursquare.screen.exploreViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imandroid.simplefoursquare.data.ExploreRepository
import com.imandroid.simplefoursquare.util.ResourceProvider

class ExploreSharedViewModelFactory(private val repository: ExploreRepository, private val mResourceProvider: ResourceProvider) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExploreSharedViewModel::class.java)) {
            return ExploreSharedViewModel(
                repository,
                mResourceProvider
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}