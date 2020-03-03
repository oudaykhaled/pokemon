package se.appshack.android.refactoring.main.data.model


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("language")
    val language: LanguageXX,
    @SerializedName("name")
    val name: String
)