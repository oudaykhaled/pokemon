package se.appshack.android.refactoring.main.domain

import androidx.lifecycle.LiveData
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.data.model.PokemonSpeciesResponse

interface PokemonUseCase {

    suspend fun getAllPokemons(): LiveData<Result<List<NamedResponseModel>>>
    suspend fun getPokemonDetails(pokemonID: Int): LiveData<Result<PokemonSpeciesResponse>>

}