package se.appshack.android.refactoring.main.data.remote.source

import se.appshack.android.refactoring.main.data.model.PokemonListResponse
import se.appshack.android.refactoring.main.data.model.PokemonSpeciesResponse

interface PokemonRemoteDataSource {

    suspend fun requestPokemons(limit: Int): PokemonListResponse
    suspend fun requestPokemonDetails(pokemonID: Int): PokemonSpeciesResponse

}
