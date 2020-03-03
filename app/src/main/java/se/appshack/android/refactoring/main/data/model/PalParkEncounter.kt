package se.appshack.android.refactoring.main.data.model


import com.google.gson.annotations.SerializedName

data class PalParkEncounter(
    @SerializedName("area")
    val area: Area,
    @SerializedName("base_score")
    val baseScore: Int,
    @SerializedName("rate")
    val rate: Int
)