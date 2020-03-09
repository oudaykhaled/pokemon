package se.appshack.android.refactoring.core.cache

import androidx.room.*

@Dao
interface CacheRowDao {

    @Query("SELECT * FROM CacheRow WHERE `key` IN (:key) And classType IN (:classType)")
    fun getTask(key: String, classType: String): CacheRow

    @Insert
    fun insert(cacheRow: CacheRow)

    @Query("DELETE FROM CacheRow")
    fun clearCache()

}