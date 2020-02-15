package se.appshack.android.refactoring.main.data.model

import com.google.gson.annotations.SerializedName

class PokemonSpeciesResponse {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("genera")
    var genera: List<GenusResponseModel>? = null
}
