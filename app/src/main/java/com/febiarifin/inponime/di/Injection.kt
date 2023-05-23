package com.febiarifin.inponime.di

import com.febiarifin.inponime.data.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}