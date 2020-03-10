package se.appshack.android.refactoring.main.data.remote.source

import com.google.gson.reflect.TypeToken
import com.ouday.core.di.qualifier.CoroutinesIO
import kotlinx.coroutines.withContext
import retrofit2.Response
import se.appshack.android.refactoring.core.cache.Cache
import se.appshack.android.refactoring.core.cache.CacheImpl
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse
import se.appshack.android.refactoring.main.data.remote.service.PokemonService
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonService: PokemonService,
    @CoroutinesIO private val context: CoroutineContext,
    private val cache: Cache
) : PokemonRemoteDataSource {

    override suspend fun requestPokemonDetails(pokemonID: Int)= withContext(context) {
        val type = object : TypeToken<Response<PokemonListResponse>>() {}.rawType
        val local = cache.find(type as Class<Response<PokemonListResponse>>, pokemonID.toString())
        if (local!=null) local.body()
        val response = pokemonService.requestPokemonDetails(pokemonID).await()
        if (response.isSuccessful) {
            return@withContext response.body() ?: throw Exception("no Response")
        } else {
            throw Exception("invalid request with code ${response.code()}")
        }
    }


    override suspend fun requestPokemonSpecies(pokemonID: Int) = withContext(context) {
        val type = object : TypeToken<Response<PokemonSpeciesResponse>>() {}.rawType
        val local = cache.find(type as Class<Response<PokemonSpeciesResponse>>, pokemonID.toString())
        if (local!=null) local.body()
        val response = pokemonService.requestPokemonSpecies(pokemonID).await()
        if (response.isSuccessful) {
            response.body() ?: throw Exception("no Response")
        } else {
            throw Exception("invalid request with code ${response.code()}")
        }
    }


    override suspend fun requestPokemons(limit: Int) = withContext(context) {
        val type = object : TypeToken<Response<PokemonListResponse>>() {}.rawType
        val local = cache.find(type as Class<Response<PokemonListResponse>>, limit.toString())
        if (local!=null) local.body()
        val response = pokemonService.requestPokemons(limit).await()
        if (response.isSuccessful) {
            response.body() ?: throw Exception("no Response")
        } else {
            throw Exception("invalid request with code ${response.code()}")
        }
    }

}