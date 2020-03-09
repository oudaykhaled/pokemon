package se.appshack.android.refactoring

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.data.remote.source.PokemonRemoteDataSource
import se.appshack.android.refactoring.main.data.repository.PokemonRepository
import se.appshack.android.refactoring.main.data.repository.PokemonRepositoryImpl
import java.io.IOException
import java.io.UncheckedIOException
import java.lang.Exception
import java.util.logging.Handler

class PokemonRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    lateinit var repository: PokemonRepository

    @Mock
    lateinit var remoteDataSource: PokemonRemoteDataSource

    val pokemonListResponse = PokemonListResponse()
    val firstPokemonNumber = 1
    val secondPokemonNumber = 2

    val firstPokemonDetailsResponse = buildEmptyPokemonDetailsResponse()
    val secondPokemonDetailsResponse = buildEmptyPokemonDetailsResponse()

    val firstPokemonSpeciesResponse = buildEmptyPokemonSpeciesResponse()
    val secondPokemonSpeciesResponse = buildEmptyPokemonSpeciesResponse()


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = PokemonRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `test "requestPokemons" should return successful response`() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(remoteDataSource.requestPokemons(151)).thenReturn(pokemonListResponse)
        val result = repository.requestPokemons(151)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data == pokemonListResponse)
    }

    @Test
    fun `test "requestPokemons" should return failed response`() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(remoteDataSource.requestPokemons(-1)).thenThrow(UncheckedIOException(IOException()))
        val result = repository.requestPokemons(-1)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR)
    }


    @Test
    fun `test "requestPokemonDetails" should return first pokemon details response`() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(remoteDataSource.requestPokemonDetails(firstPokemonNumber)).thenReturn(firstPokemonDetailsResponse)
        val result = repository.requestPokemonDetails(firstPokemonNumber)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data == firstPokemonDetailsResponse)
    }


    @Test
    fun `test "requestPokemonDetails" should return second pokemon details response`() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(remoteDataSource.requestPokemonDetails(secondPokemonNumber)).thenReturn(secondPokemonDetailsResponse)
        val result = repository.requestPokemonDetails(secondPokemonNumber)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data == secondPokemonDetailsResponse)
    }



}
