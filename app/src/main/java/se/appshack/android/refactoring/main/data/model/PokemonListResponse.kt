package se.appshack.android.refactoring.main.data.model

import com.google.gson.annotations.SerializedName

class PokemonListResponse {

    @SerializedName("count")
    var count: Int = 0

    @SerializedName("next")
    var next: String? = null

    @SerializedName("previous")
    var previous: String? = null

    @SerializedName("results")
    var results: List<NamedResponseModel>? = null
}
