import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import se.appshack.android.refactoring.App
import se.appshack.android.refactoring.core.builder.ActivityBuilder
import se.appshack.android.refactoring.core.di.modules.ContextModule
import se.appshack.android.refactoring.core.di.modules.CoroutinesThreadsProvider
import se.appshack.android.refactoring.core.di.modules.NetworkModule
import se.appshack.android.refactoring.core.di.modules.SchedulersModule
import se.appshack.android.refactoring.main.di.PokemonDomain
import se.appshack.android.refactoring.main.di.PokemonRemoteModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        NetworkModule::class, ContextModule::class,
        ActivityBuilder::class,
        SchedulersModule::class,
        CoroutinesThreadsProvider::class,
        PokemonRemoteModule::class,
        PokemonDomain::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}