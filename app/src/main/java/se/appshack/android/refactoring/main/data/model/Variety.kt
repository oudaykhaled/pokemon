package se.appshack.android.refactoring.main.data.model


import com.google.gson.annotations.SerializedName

data class Variety(
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("pokemon")
    val pokemon: Pokemon
)