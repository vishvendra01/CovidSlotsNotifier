package com.app.vaccinenotifier.data.model

import com.google.gson.annotations.SerializedName

data class District(
    @SerializedName("district_id") val id: Int,
    @SerializedName("district_name") val name: String
)