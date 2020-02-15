package se.appshack.android.refactoring.main.data.model

import com.google.gson.annotations.SerializedName

class PokemonSpritesModel {

    @SerializedName("front_default")
    var urlFront: String? = null

    @SerializedName("back_default")
    var urlBack: String? = null
}
