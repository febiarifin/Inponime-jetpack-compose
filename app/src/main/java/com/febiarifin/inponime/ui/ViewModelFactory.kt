package com.febiarifin.inponime.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.febiarifin.inponime.data.AnimeRepository
import com.febiarifin.inponime.ui.screen.detail.DetailAnimeViewModel
import com.febiarifin.inponime.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: AnimeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailAnimeViewModel::class.java)) {
            return DetailAnimeViewModel(repository) as T
        }
//        else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
//            return CartViewModel(repository) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}