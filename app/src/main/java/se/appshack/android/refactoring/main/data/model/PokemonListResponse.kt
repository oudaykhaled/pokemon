package se.appshack.android.refactoring.main.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonListResponse (

    @SerializedName("count")
    var count: Int = 0,

    @SerializedName("next")
    var next: String? = null,

    @SerializedName("previous")
    var previous: String? = null,

    @SerializedName("results")
    var results: List<NamedResponseModel>? = null

): Parcelable

