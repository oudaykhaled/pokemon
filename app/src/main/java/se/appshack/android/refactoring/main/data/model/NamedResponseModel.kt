package se.appshack.android.refactoring.main.data.model

import com.google.gson.annotations.SerializedName

class NamedResponseModel {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("url")
    var url: String? = null
}
