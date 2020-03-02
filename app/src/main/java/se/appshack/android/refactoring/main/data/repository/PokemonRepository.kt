package se.appshack.android.refactoring.main.data.repository

import androidx.lifecycle.LiveData
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.main.data.model.PokemonListResponse
import se.appshack.android.refactoring.main.data.model.PokemonSpeciesResponse

interface PokemonRepository {

    suspend fun requestPokemons(limit: Int): LiveData<Result<PokemonListResponse>>
    suspend fun requestPokemonDetails(pokemonID: Int): LiveData<Result<PokemonSpeciesResponse>>

}