package se.appshack.android.refactoring.main.data.remote.service

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import se.appshack.android.refactoring.BuildConfig.pokeapiUrl
import se.appshack.android.refactoring.main.data.model.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.PokemonListResponse
import se.appshack.android.refactoring.main.data.model.PokemonSpeciesResponse

interface PokemonService {

    @GET("${pokeapiUrl}pokemon")
    fun requestPokemons(@Query("limit") limit :Int): Deferred<Response<PokemonListResponse>>

    @GET("${pokeapiUrl}pokemon/{pokemonID}")
    fun requestPokemonDetails(@Path("pokemonID") pokemonID :Int): Deferred<Response<PokemonSpeciesResponse>>

}