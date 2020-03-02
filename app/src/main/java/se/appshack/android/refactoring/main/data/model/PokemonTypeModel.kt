package se.appshack.android.refactoring.main.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonTypeModel (

    @SerializedName("slot")
    var slot: Int = 0,

    @SerializedName("type")
    var type: NamedResponseModel? = null

): Parcelable

