package se.appshack.android.refactoring.main.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse
import se.appshack.android.refactoring.main.domain.PokemonUseCase
import javax.inject.Inject

class PokemonViewModel @Inject constructor(
    private val useCase: PokemonUseCase
): ViewModel() {

    var selectedPokemon: NamedResponseModel? = null

    private val pokemonLiveData = MediatorLiveData<Result<List<NamedResponseModel>>>()
    private val pokemonSpecies = MediatorLiveData<Result<PokemonSpeciesResponse>>()
    private val pokemonDetails = MediatorLiveData<Result<PokemonDetailsResponse>>()

    fun getpokemonLiveData() = pokemonLiveData
    fun getpokemonSpecies() = pokemonSpecies
    fun getpokemonDetails() = pokemonDetails

    fun requestPokemonsList(){
        viewModelScope.launch {
            pokemonLiveData.addSource(useCase.getAllPokemons()) {
                pokemonLiveData.value = it
            }
        }
    }

    fun requestPokemonSpecies(pokemonID: Int){
        viewModelScope.launch {
            pokemonSpecies.addSource(useCase.getPokemonSpecies(pokemonID)) {
                pokemonSpecies.value = it
            }
        }
    }

    fun requestPokemonDetails(pokemonID: Int){
        viewModelScope.launch {
            pokemonDetails.addSource(useCase.getPokemonDetails(pokemonID)) {
                pokemonDetails.value = it
            }
        }
    }

}