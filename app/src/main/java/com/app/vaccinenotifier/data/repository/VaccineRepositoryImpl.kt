package com.app.vaccinenotifier.data.repository

import android.content.SharedPreferences
import com.app.vaccinenotifier.data.model.District
import com.app.vaccinenotifier.data.model.State
import com.app.vaccinenotifier.data.source.network.NetworkSource
import com.app.vaccinenotifier.response.DistrictResponse
import com.app.vaccinenotifier.response.StateResponse
import com.app.vaccinenotifier.response.VaccineCentersResponse
import com.google.gson.Gson
import io.reactivex.Flowable
import javax.inject.Inject

class VaccineRepositoryImpl @Inject constructor(
    val networkSource: NetworkSource,
    val preferences: SharedPreferences
) :
    VaccineRepository {
    companion object {
        private const val CITY_PREF_KEY = "City_Pref"
    }

    private val gson = Gson()
    private val cachedStates = mutableListOf<State>()

    override fun getSavedCity(): District? {
        val savedValue = preferences.getString(CITY_PREF_KEY, null)
        if (!savedValue.isNullOrEmpty()) {
            return gson.fromJson(savedValue, District::class.java)
        }
        return null
    }

    override fun saveCity(district: District) {
        val valueToSave = gson.toJson(district)
        preferences.edit().putString(CITY_PREF_KEY, valueToSave).apply()
    }

    override fun getStates(): Flowable<StateResponse> {
        if (cachedStates.isNotEmpty()) {
            return Flowable.just(StateResponse(cachedStates))
        } else {
            return networkSource.getStatesList().map {
                cachedStates.addAll(it.states)
                it
            }
        }
    }

    override fun getCities(stateId : String): Flowable<DistrictResponse> {
        return networkSource.getCitiesList(stateId)
    }

    override fun getVaccineCenters(date: String, cityId : String): Flowable<VaccineCentersResponse> {
        return networkSource.getVaccineCentersList(date, cityId)
    }
}