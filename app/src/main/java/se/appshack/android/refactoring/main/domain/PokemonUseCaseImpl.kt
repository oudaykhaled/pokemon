package se.appshack.android.refactoring.main.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.data.repository.PokemonRepository
import java.util.ArrayList
import javax.inject.Inject

class PokemonUseCaseImpl @Inject constructor(private val repository: PokemonRepository) :
    PokemonUseCase {


    override suspend fun getPokemonDetails(pokemonID: Int) = repository.requestPokemonDetails(pokemonID)

    override suspend fun getPokemonSpecies(pokemonID: Int) = repository.requestPokemonSpecies(pokemonID)

    override suspend fun getAllPokemons(): LiveData<Result<List<NamedResponseModel>>> {
        return Transformations.map(repository.requestPokemons(151)) {
            when(it.status){
                Status.SUCCESS -> Result.success(it.data?.results)
                Status.LOADING -> Result.loading()
                Status.ERROR -> Result.error(it.exception.toString())
            }
        }
    }

//    private fun manipulatePokemonList(result: PokemonListResponse?): List<NamedResponseModel> {
//        val pokemonModels = ArrayList<NamedResponseModel>()
//
//        val ids = ArrayList<Int>()
//        var i = 0
//        while (i < 20) {
//            val id = (Math.random() * 151 + 1).toInt()
//            if (ids.contains(id)) {
//                i--
//            } else {
//                ids.add(id)
//            }
//            i++
//        }
//
//        ids.sortWith(Comparator { integer, t1 -> integer - t1 })
//
//        for (i in ids) {
//            result?.results?.get(i - 1)?.let { pokemonModels.add(it) }
//        }
//
//        return pokemonModels
//    }

}
