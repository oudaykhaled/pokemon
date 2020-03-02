package se.appshack.android.refactoring.main.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonSpeciesResponse (

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("genera")
    var genera: List<GenusResponseModel>? = null

): Parcelable

