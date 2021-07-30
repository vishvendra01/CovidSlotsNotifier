package com.app.vaccinenotifier.di

import com.app.vaccinenotifier.data.repository.VaccineRepository
import com.app.vaccinenotifier.data.repository.VaccineRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {

    @Binds
    fun bindsRepository(vaccineRepository: VaccineRepositoryImpl): VaccineRepository
}