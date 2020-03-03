package se.appshack.android.refactoring.main.data.remote.source

import com.ouday.core.di.qualifier.CoroutinesIO
import kotlinx.coroutines.withContext
import se.appshack.android.refactoring.main.data.remote.service.PokemonService
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonService: PokemonService,
    @CoroutinesIO private val context: CoroutineContext
) : PokemonRemoteDataSource {

    override suspend fun requestPokemonDetails(pokemonID: Int)= withContext(context) {
        val response = pokemonService.requestPokemonDetails(pokemonID).await()
        if (response.isSuccessful) {
            response.body() ?: throw Exception("no Response")
        } else {
            throw Exception("invalid request with code ${response.code()}")
        }
    }


    override suspend fun requestPokemonSpecies(pokemonID: Int) = withContext(context) {
        val response = pokemonService.requestPokemonSpecies(pokemonID).await()
        if (response.isSuccessful) {
            response.body() ?: throw Exception("no Response")
        } else {
            throw Exception("invalid request with code ${response.code()}")
        }
    }


    override suspend fun requestPokemons(limit: Int) = withContext(context) {
        val response = pokemonService.requestPokemons(limit).await()

        if (response.isSuccessful) {
            response.body() ?: throw Exception("no Response")
        } else {
            throw Exception("invalid request with code ${response.code()}")
        }
    }

}