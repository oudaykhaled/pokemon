package se.appshack.android.refactoring.core.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import se.appshack.android.refactoring.core.cache.Cache;
import se.appshack.android.refactoring.core.cache.CacheImpl;

@Module
public class CacheProvider {

    @Provides
    @Singleton
    public Cache provideCache(Context context) {
        return new CacheImpl(context);
    }


}
