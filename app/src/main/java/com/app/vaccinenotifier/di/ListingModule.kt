package com.app.vaccinenotifier.di

import androidx.lifecycle.ViewModel
import com.app.vaccinenotifier.view.choosecity.ChooseCityViewModel
import com.app.vaccinenotifier.view.listing.VaccineCentersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ListingModule {
    @Binds
    @IntoMap
    @ViewModelKey(VaccineCentersViewModel::class)
    fun bindVaccineCenterViewModel(viewModel: VaccineCentersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseCityViewModel::class)
    fun bindChooseCityViewModel(viewModel: ChooseCityViewModel): ViewModel
}