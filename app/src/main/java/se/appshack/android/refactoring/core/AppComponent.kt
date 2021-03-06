import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import se.appshack.android.refactoring.App
import se.appshack.android.refactoring.core.builder.ActivityBuilder
import se.appshack.android.refactoring.core.builder.FragmentBuilder
import se.appshack.android.refactoring.core.di.modules.*
import se.appshack.android.refactoring.main.di.PokemonDomain
import se.appshack.android.refactoring.main.di.PokemonPresentationModule
import se.appshack.android.refactoring.main.di.PokemonRemoteModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        NetworkModule::class, ContextModule::class,
        CacheProvider::class,
        ActivityBuilder::class,
        SchedulersModule::class,
        CoroutinesThreadsProvider::class,
        FragmentBuilder::class,
        PokemonRemoteModule::class,
        PokemonDomain::class,
        PokemonPresentationModule::class
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