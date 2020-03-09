package se.appshack.android.refactoring

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse
import se.appshack.android.refactoring.main.data.remote.source.PokemonRemoteDataSource
import se.appshack.android.refactoring.main.data.repository.PokemonRepository
import se.appshack.android.refactoring.main.domain.PokemonUseCase
import se.appshack.android.refactoring.main.domain.PokemonUseCaseImpl

@ExperimentalCoroutinesApi
class PokemonUseCaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var pokemonRemoteDataSource: PokemonRemoteDataSource

    lateinit var pokemonUseCase: PokemonUseCase

    lateinit var pokemonRepository: PokemonRepository

    val pokemonListResponse = PokemonListResponse()
    val firstPokemonNumber = 1
    val secondPokemonNumber = 2

    val firstPokemonDetailsResponse = buildEmptyPokemonDetailsResponse()
    val secondPokemonDetailsResponse = buildEmptyPokemonDetailsResponse()

    val firstPokemonSpeciesResponse = buildEmptyPokemonSpeciesResponse()
    val secondPokemonSpeciesResponse = buildEmptyPokemonSpeciesResponse()

    @Test
    fun `test "getAllPokemons" in loading state`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemons(151) } doReturn object : LiveData<Result<PokemonListResponse>>() {
                init {
                    value = Result.loading()
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getAllPokemons()
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun `test "getAllPokemons" in success state`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemons(151) } doReturn object : LiveData<Result<PokemonListResponse>>() {
                init {
                    value = Result.success(pokemonListResponse)
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getAllPokemons()
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS && LiveDataTestUtil.getValue(result).data == pokemonListResponse.results)
    }

    @Test
    fun `test "getAllPokemons" in error state`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemons(151) } doReturn object : LiveData<Result<PokemonListResponse>>() {
                init {
                    value = Result.error(Exception("error"))
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getAllPokemons()
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR  && LiveDataTestUtil.getValue(result).message == "error")
    }

    @Test
    fun `test "requestPokemonDetails" where pokemonID=1 should show loading`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonDetails(firstPokemonNumber) } doReturn object :LiveData<Result<PokemonDetailsResponse>>() {
                init {
                    value = Result.loading()
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonDetails(firstPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun `test "requestPokemonDetails" where pokemonID=1 should show success`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonDetails(firstPokemonNumber) } doReturn object :LiveData<Result<PokemonDetailsResponse>>() {
                init {
                    value = Result.success(firstPokemonDetailsResponse)
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonDetails(firstPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS && LiveDataTestUtil.getValue(result).data == firstPokemonDetailsResponse)
    }

    @Test
    fun `test "requestPokemonDetails" where pokemonID=1 should show error`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonDetails(firstPokemonNumber) } doReturn object :LiveData<Result<PokemonDetailsResponse>>() {
                init {
                    value = Result.error(Exception("error"))
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonDetails(firstPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR)
    }

    @Test
    fun `test "requestPokemonDetails" where pokemonID=2 should show loading`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonDetails(secondPokemonNumber) } doReturn object :LiveData<Result<PokemonDetailsResponse>>() {
                init {
                    value = Result.loading()
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonDetails(secondPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun `test "requestPokemonDetails" where pokemonID=2 should show success`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonDetails(secondPokemonNumber) } doReturn object :LiveData<Result<PokemonDetailsResponse>>() {
                init {
                    value = Result.success(secondPokemonDetailsResponse)
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonDetails(secondPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS && LiveDataTestUtil.getValue(result).data == secondPokemonDetailsResponse)
    }

    @Test
    fun `test "requestPokemonDetails" where pokemonID=2 should show error`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonDetails(secondPokemonNumber) } doReturn object :LiveData<Result<PokemonDetailsResponse>>() {
                init {
                    value = Result.error(Exception("error"))
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonDetails(secondPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR)
    }

    @Test
    fun `test "requestPokemonSpecies" where pokemonID=1 should show loading`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonSpecies(firstPokemonNumber) } doReturn object :LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.loading()
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonSpecies(firstPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun `test "requestPokemonSpecies" where pokemonID=1 should show success`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonSpecies(firstPokemonNumber) } doReturn object :LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.success(firstPokemonSpeciesResponse)
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonSpecies(firstPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS && LiveDataTestUtil.getValue(result).data == firstPokemonSpeciesResponse)
    }

    @Test
    fun `test "requestPokemonSpecies" where pokemonID=1 should show error`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonSpecies(firstPokemonNumber) } doReturn object :LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.error(Exception("error"))
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonSpecies(firstPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR)
    }


    @Test
    fun `test "requestPokemonSpecies" where pokemonID=2 should show loading`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonSpecies(secondPokemonNumber) } doReturn object :LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.loading()
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonSpecies(secondPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun `test "requestPokemonSpecies" where pokemonID=2 should show success`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonSpecies(secondPokemonNumber) } doReturn object :LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.success(secondPokemonSpeciesResponse)
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonSpecies(secondPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS && LiveDataTestUtil.getValue(result).data == secondPokemonSpeciesResponse)
    }

    @Test
    fun `test "requestPokemonSpecies" where pokemonID=2 should show error`()=mainCoroutineRule.runBlockingTest{
        pokemonRepository = mock {
            onBlocking { requestPokemonSpecies(secondPokemonNumber) } doReturn object :LiveData<Result<PokemonSpeciesResponse>>() {
                init {
                    value = Result.error(Exception("error"))
                }
            }
        }
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
        val result = pokemonUseCase.getPokemonSpecies(secondPokemonNumber)
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR)
    }


}