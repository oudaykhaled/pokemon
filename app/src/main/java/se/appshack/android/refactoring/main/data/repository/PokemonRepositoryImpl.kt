package se.appshack.android.refactoring.main.data.repository

import se.appshack.android.refactoring.core.Result
import androidx.lifecycle.liveData
import se.appshack.android.refactoring.main.data.remote.source.PokemonRemoteDataSource
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource) : PokemonRepository {

    override suspend fun requestPokemonDetails(pokemonID: Int) = liveData {
        emit(Result.loading())
        try {
            val pokemonDetails = remoteDataSource.requestPokemonDetails(pokemonID)
            emit(Result.success(pokemonDetails))
        } catch (exception: Exception) {
            emit(Result.error(exception?.message.toString()))
        }
    }

    override suspend fun requestPokemonSpecies(pokemonID: Int) = liveData {
        emit(Result.loading())
        try {
            val pokemonDetails = remoteDataSource.requestPokemonSpecies(pokemonID)
            emit(Result.success(pokemonDetails))
        } catch (exception: Exception) {
            emit(Result.error(exception?.message.toString()))
        }
    }

    override suspend fun requestPokemons(limit: Int) =  liveData {
        emit(Result.loading())
        try {
            val pokemons = remoteDataSource.requestPokemons(limit)
            emit(Result.success(pokemons))
        } catch (exception: Exception) {
            emit(Result.error(exception?.message.toString()))
        }
    }


}