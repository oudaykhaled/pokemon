package se.appshack.android.refactoring.main.data.extensions

import se.appshack.android.refactoring.main.data.model.NamedResponseModel

fun NamedResponseModel.getID(): Int?{
    var arr = this.url?.split("/")
    return arr?.get(arr.size-1)?.toIntOrNull()
}