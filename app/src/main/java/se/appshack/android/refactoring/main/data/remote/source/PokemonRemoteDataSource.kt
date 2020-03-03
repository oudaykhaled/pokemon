package se.appshack.android.refactoring.main.data.remote.source

import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse

interface PokemonRemoteDataSource {

    suspend fun requestPokemons(limit: Int): PokemonListResponse
    suspend fun requestPokemonSpecies(pokemonID: Int): PokemonSpeciesResponse
    suspend fun requestPokemonDetails(pokemonID: Int): PokemonDetailsResponse

}
