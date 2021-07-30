package com.app.vaccinenotifier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.vaccinenotifier.data.repository.VaccineRepository
import com.app.vaccinenotifier.di.DaggerApplicationComponent
import com.app.vaccinenotifier.di.PrefsModule
import com.app.vaccinenotifier.view.choosecity.ChooseCityFragment
import com.app.vaccinenotifier.view.listing.ListingContainerFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    public lateinit var repository: VaccineRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerApplicationComponent
            .builder()
            .prefsModule(PrefsModule(this))
            .build()
            .inject(this)
        displayListingIfCityIsSelected()
    }

    private fun displayListingIfCityIsSelected() {
        val savedCity = repository.getSavedCity();
        if (savedCity != null) {
            displayListingScreen()
        } else {
            displayCitySelectScreen()
        }
    }

    private fun displayListingScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ListingContainerFragment()).commit()
    }

    private fun displayCitySelectScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ChooseCityFragment()).commit()
    }
}