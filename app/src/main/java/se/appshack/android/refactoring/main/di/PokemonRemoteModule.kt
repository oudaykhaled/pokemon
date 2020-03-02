package se.appshack.android.refactoring.main.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import se.appshack.android.refactoring.main.data.remote.service.PokemonService
import se.appshack.android.refactoring.main.data.remote.source.PokemonRemoteDataSource
import se.appshack.android.refactoring.main.data.remote.source.PokemonRemoteDataSourceImpl

@Module(includes = [PokemonRemoteModule.Binders::class])
class PokemonRemoteModule {

    @Module
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: PokemonRemoteDataSourceImpl
        ): PokemonRemoteDataSource
    }

    @Provides
    fun providesService(retrofit: Retrofit): PokemonService =
        retrofit.create(PokemonService::class.java)


}
