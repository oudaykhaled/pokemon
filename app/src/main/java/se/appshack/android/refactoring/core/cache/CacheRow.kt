package se.appshack.android.refactoring.core.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = arrayOf("key", "classType"))
data class CacheRow(

    @ColumnInfo(name = "key")
    val key: String,

    @ColumnInfo(name = "classType")
    val classType: String,

    @ColumnInfo(name = "data")
    val data: String,

    @ColumnInfo(name = "creationTime")
    val creationTime: Long
)