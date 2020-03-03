package se.appshack.android.refactoring.main.data.remote.service

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import se.appshack.android.refactoring.BuildConfig.pokeapiUrl
import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse

interface PokemonService {

    @GET("${pokeapiUrl}pokemon")
    @Headers("Cache-control: no-cache")
    fun requestPokemons(@Query("limit") limit :Int): Deferred<Response<PokemonListResponse>>

    @GET("${pokeapiUrl}pokemon-species/{pokemonID}")
    @Headers("Cache-control: no-cache")
    fun requestPokemonSpecies(@Path("pokemonID") pokemonID :Int): Deferred<Response<PokemonSpeciesResponse>>

    @GET("${pokeapiUrl}pokemon/{pokemonID}")
    @Headers("Cache-control: no-cache")
    fun requestPokemonDetails(@Path("pokemonID") pokemonID :Int): Deferred<Response<PokemonDetailsResponse>>

}
