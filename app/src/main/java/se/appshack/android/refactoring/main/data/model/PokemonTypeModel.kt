package se.appshack.android.refactoring.main.data.model

import com.google.gson.annotations.SerializedName

class PokemonTypeModel {

    @SerializedName("slot")
    var slot: Int = 0

    @SerializedName("type")
    var type: NamedResponseModel? = null
}
