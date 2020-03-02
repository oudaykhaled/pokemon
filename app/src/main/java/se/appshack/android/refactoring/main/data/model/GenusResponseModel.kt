package se.appshack.android.refactoring.main.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenusResponseModel (

    @SerializedName("genus")
    var genus: String? = null,

    @SerializedName("language")
    var language: NamedResponseModel? = null

): Parcelable
