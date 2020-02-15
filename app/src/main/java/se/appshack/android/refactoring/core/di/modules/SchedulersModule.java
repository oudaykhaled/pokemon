package se.appshack.android.refactoring.core.di.modules;

import com.ouday.core.di.qualifier.CoroutinesIO;
import com.ouday.core.di.qualifier.CoroutinesMainThread;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Dispatchers;
import se.appshack.android.refactoring.core.di.qualifier.IO;
import se.appshack.android.refactoring.core.di.qualifier.MainThread;

@Module
public class SchedulersModule {

    @Provides
    @IO
    public Scheduler bindIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @MainThread
    public Scheduler bindMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }



    @CoroutinesIO
    @Provides
    public CoroutineContext providesIoDispatcher() {
      return  Dispatchers.getIO();
    }

    @CoroutinesMainThread
    @Provides
    public CoroutineContext providesMainThreadDispatcher(){
        return Dispatchers.getMain();
    }

}
