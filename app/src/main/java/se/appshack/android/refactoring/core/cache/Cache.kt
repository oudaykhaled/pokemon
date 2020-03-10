package se.appshack.android.refactoring.core.cache

import android.os.Parcelable

interface Cache {
    fun <T: Parcelable> store(obj: T, vararg ids: String)
    fun <T> find(clazz: Class<T>, vararg ids: String): T?
}