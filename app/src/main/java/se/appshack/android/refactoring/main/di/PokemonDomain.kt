package se.appshack.android.refactoring.main.di

import dagger.Binds
import dagger.Module
import se.appshack.android.refactoring.main.data.repository.PokemonRepository
import se.appshack.android.refactoring.main.data.repository.PokemonRepositoryImpl
import se.appshack.android.refactoring.main.domain.PokemonUseCase
import se.appshack.android.refactoring.main.domain.PokemonUseCaseImpl

@Module
abstract class PokemonDomain {

    @Binds
    abstract fun bindPokemonUseCase(
        useCaseImpl: PokemonUseCaseImpl
    ): PokemonUseCase

    @Binds
    abstract fun bindRepo(
        repoImpl: PokemonRepositoryImpl
    ): PokemonRepository
}