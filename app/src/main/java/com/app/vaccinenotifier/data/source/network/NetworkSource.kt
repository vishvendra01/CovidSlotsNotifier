package com.app.vaccinenotifier.data.source.network

import com.app.vaccinenotifier.response.DistrictResponse
import com.app.vaccinenotifier.response.StateResponse
import com.app.vaccinenotifier.response.VaccineCentersResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkSource {

    @GET("api/v2/admin/location/states")
    @Headers(
        "accept: application/json",
        "Accept-Language: hi_IN",
        """user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"""
    )
    fun getStatesList(): Flowable<StateResponse>

    @GET("api/v2/admin/location/districts/{stateId}")
    @Headers(
        "accept: application/json",
        "Accept-Language: hi_IN",
        """user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"""
    )
    fun getCitiesList(@Path("stateId") stateId: String): Flowable<DistrictResponse>

    @GET("api/v2/appointment/sessions/public/calendarByDistrict")
    @Headers(
        "accept: application/json",
        "Accept-Language: hi_IN",
        """user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"""
    )
    fun getVaccineCentersList(
        @Query("date") date: String,
        @Query("district_id") cityId: String
    ): Flowable<VaccineCentersResponse>
}