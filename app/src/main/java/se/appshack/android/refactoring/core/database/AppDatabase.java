package se.appshack.android.refactoring.core.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import se.appshack.android.refactoring.core.cache.CacheRow;
import se.appshack.android.refactoring.core.cache.CacheRowDao;

@Database(entities = {CacheRow.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CacheRowDao cacheRowDao();
}