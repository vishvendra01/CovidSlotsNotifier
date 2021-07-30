package com.app.vaccinenotifier.data.model

import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("state_id") val id: Int,
    @SerializedName("state_name") val name: String
)