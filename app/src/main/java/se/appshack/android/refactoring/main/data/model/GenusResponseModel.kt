package se.appshack.android.refactoring.main.data.model

import com.google.gson.annotations.SerializedName

class GenusResponseModel {

    @SerializedName("genus")
    var genus: String? = null

    @SerializedName("language")
    var language: NamedResponseModel? = null
}
