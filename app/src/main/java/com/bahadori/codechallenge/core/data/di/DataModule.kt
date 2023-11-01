package com.bahadori.codechallenge.core.data.di

import com.bahadori.codechallenge.core.data.repository.RateRepositoryImpl
import com.bahadori.codechallenge.core.domain.repository.RateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsRateRepository(repositoryImpl: RateRepositoryImpl): RateRepository
}