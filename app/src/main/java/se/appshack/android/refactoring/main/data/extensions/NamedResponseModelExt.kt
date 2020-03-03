package se.appshack.android.refactoring.main.data.extensions

import se.appshack.android.refactoring.main.data.model.NamedResponseModel

fun NamedResponseModel.getID(): Int?{
    var url = this.url?.trim().toString()
    url = if (url[url.length-1] == '/') url.substring(0, url.length-1) else url
    var arr = url?.split("/")
    return arr?.get(arr.size-1)?.toIntOrNull()
}