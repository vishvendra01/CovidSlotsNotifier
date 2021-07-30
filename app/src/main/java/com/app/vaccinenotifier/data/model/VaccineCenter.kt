package com.app.vaccinenotifier.data.model

import com.google.gson.annotations.SerializedName

data class VaccineCenter(
    @SerializedName("center_id") val centerId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("state_name") val stateName: String,
    @SerializedName("district_name") val districtName: String,
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("fee_type") val feeType: String,
    @SerializedName("sessions") var sessions: List<VaccineSession>,
)