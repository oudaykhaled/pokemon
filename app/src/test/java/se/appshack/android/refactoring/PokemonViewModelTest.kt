package se.appshack.android.refactoring

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.domain.PokemonUseCase
import se.appshack.android.refactoring.main.presentation.viewmodel.PokemonViewModel
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse
import java.lang.Exception

class PokemonViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    lateinit var pokemonViewModel: PokemonViewModel

    lateinit var pokemonUseCase: PokemonUseCase

    val pokemonListResponse = PokemonListResponse()
    val pokemonNumber = 1

    val pokemonDetailsResponse = buildEmptyPokemonDetailsResponse()
    val pokemonSpeciesResponse = buildEmptyPokemonSpeciesResponse()

    @Before
    fun init(){
        pokemonUseCase = mock ()
    }

    @Test
    fun `test "getAllPokemons" should show loading`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getAllPokemons() } doReturn object : LiveData<Result<List<NamedResponseModel>>>() {
                init {
                    value = Result.loading()
                }
            }
        }

        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonsList()
        val result = pokemonViewModel.getAllPokemonLiveData()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }


    @Test
    fun `test "getAllPokemons" should return successful response`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getAllPokemons() } doReturn object : LiveData<Result<List<NamedResponseModel>>>() {
                init {
                    value = Result.success(pokemonListResponse.results)
                }
            }
        }
        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonsList()
        val result = pokemonViewModel.getAllPokemonLiveData()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS &&
                LiveDataTestUtil.getValue(result).data == pokemonListResponse.results)
    }

    @Test
    fun `test "getAllPokemons" should return failed response`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getAllPokemons() } doReturn object : LiveData<Result<List<NamedResponseModel>>>() {
                init {
                    value = Result.error(Exception("error"))
                }
            }
        }
        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonsList()
        val result = pokemonViewModel.getAllPokemonLiveData()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR)
    }


    @Test
    fun `test "getPokemonDetails" should show loading`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getPokemonDetails(151) } doReturn object : LiveData<Result<PokemonDetailsResponse>>() {
                init {
                    value = Result.loading()
                }
            }
        }

        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonDetails(151)
        val result = pokemonViewModel.getpokemonDetails()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }


    @Test
    fun `test "getPokemonDetails" should return successful response`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getPokemonDetails(pokemonNumber) } doReturn object : LiveData<Result<PokemonDetailsResponse>>() {
                init {
                    value = Result.success(pokemonDetailsResponse)
                }
            }
        }
        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonDetails(pokemonNumber)
        val result = pokemonViewModel.getpokemonDetails()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS &&
                LiveDataTestUtil.getValue(result).data == pokemonDetailsResponse)
    }

    @Test
    fun `test "getPokemonDetails" should return failed response`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getAllPokemons() } doReturn object : LiveData<Result<List<NamedResponseModel>>>() {
                init {
                    value = Result.error(Exception("error"))
                }
            }
        }
        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonsList()
        val result = pokemonViewModel.getAllPokemonLiveData()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR)
    }


    @Test
    fun `test "getPokemonSpecies" should show loading`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getPokemonSpecies(151) } doReturn object : LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.loading()
                }
            }
        }
        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonSpecies(151)
        val result = pokemonViewModel.getpokemonSpecies()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }


    @Test
    fun `test "getPokemonSpecies" should return successful response`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getPokemonSpecies(pokemonNumber) } doReturn object : LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.success(pokemonSpeciesResponse)
                }
            }
        }
        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonSpecies(pokemonNumber)
        val result = pokemonViewModel.getpokemonSpecies()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS &&
                LiveDataTestUtil.getValue(result).data == pokemonSpeciesResponse)
    }

    @Test
    fun `test "getPokemonSpecies" should return failed response`() = mainCoroutineRule.runBlockingTest {

        pokemonUseCase = mock {
            onBlocking { getPokemonSpecies(pokemonNumber) } doReturn object : LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.error(Exception("error"))
                }
            }
        }
        pokemonViewModel = PokemonViewModel(pokemonUseCase)
        pokemonViewModel.requestPokemonSpecies(pokemonNumber)
        val result = pokemonViewModel.getpokemonSpecies()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR)
    }


}