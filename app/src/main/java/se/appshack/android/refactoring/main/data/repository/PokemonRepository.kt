package se.appshack.android.refactoring.main.data.repository

import androidx.lifecycle.LiveData
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse

interface PokemonRepository {

    suspend fun requestPokemons(limit: Int): LiveData<Result<PokemonListResponse>>
    suspend fun requestPokemonSpecies(pokemonID: Int): LiveData<Result<PokemonSpeciesResponse>>
    suspend fun requestPokemonDetails(pokemonID: Int): LiveData<Result<PokemonDetailsResponse>>

}