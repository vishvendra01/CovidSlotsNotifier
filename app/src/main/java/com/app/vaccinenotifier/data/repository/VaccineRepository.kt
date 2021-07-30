package com.app.vaccinenotifier.data.repository

import com.app.vaccinenotifier.data.model.District
import com.app.vaccinenotifier.response.DistrictResponse
import com.app.vaccinenotifier.response.StateResponse
import com.app.vaccinenotifier.response.VaccineCentersResponse
import io.reactivex.Flowable

interface VaccineRepository {

    fun getSavedCity(): District?

    fun saveCity(district: District)

    fun getStates(): Flowable<StateResponse>

    fun getCities(stateId: String): Flowable<DistrictResponse>

    fun getVaccineCenters(date: String, cityId: String): Flowable<VaccineCentersResponse>
}