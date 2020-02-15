package se.appshack.android.refactoring.main.data.model

import com.google.gson.annotations.SerializedName

class PokemonDetailsResponse {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("height")
    var height: Int = 0

    @SerializedName("weight")
    var weight: Int = 0

    @SerializedName("species")
    var species: NamedResponseModel? = null

    @SerializedName("types")
    var types: List<PokemonTypeModel>? = null

    @SerializedName("sprites")
    var sprites: PokemonSpritesModel? = null
}
