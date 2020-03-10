package se.appshack.android.refactoring.core.cache

import android.content.Context
import android.os.Parcelable
import com.google.gson.Gson
import se.appshack.android.refactoring.core.database.DatabaseClient
import java.lang.reflect.Type
import javax.inject.Inject

const val CACHE_EXPIRY_DATE = 1000 * 60 * 60

class CacheImpl @Inject constructor(private val context: Context) : Cache {

    private val gson = Gson()

    override fun <T: Parcelable> store(obj: T, vararg ids: String){
        val cacheRow = CacheRow(getUniqueId(*ids), obj.javaClass.name, gson.toJson(obj), System.currentTimeMillis())
        DatabaseClient.getInstance(context).appDatabase.cacheRowDao().insert(cacheRow)
    }

    override fun <T> find(clazz: Class<T>, vararg ids: String): T? {
        var row = DatabaseClient.getInstance(context).appDatabase.cacheRowDao().getTask(getUniqueId(*ids), clazz.name)
        if (row == null) return null
        if (System.currentTimeMillis()-row.creationTime > CACHE_EXPIRY_DATE) return null
        return gson.fromJson(row.data, clazz)
    }

    private fun getUniqueId(vararg ids: String): String {
        val uuidBuilder = StringBuilder()
        ids.forEach { uuidBuilder.append(it) }
        return uuidBuilder.toString()
    }

}