package se.appshack.android.refactoring.main.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonSpritesModel (

    @SerializedName("front_default")
    var urlFront: String? = null,

    @SerializedName("back_default")
    var urlBack: String? = null

): Parcelable

