package com.app.vaccinenotifier.di

import com.app.vaccinenotifier.MainActivity
import com.app.vaccinenotifier.view.choosecity.ChooseCityFragment
import com.app.vaccinenotifier.view.listing.VaccineCentersListingFragment
import dagger.Component

@Component(modules = [NetworkModule::class, RepoModule::class, ViewModelModule::class, ListingModule::class, SchedulerModule::class, PrefsModule::class])
interface ApplicationComponent {

    fun inject(fragment: VaccineCentersListingFragment)

    fun inject(fragment: ChooseCityFragment)

    fun inject(activity: MainActivity)
}