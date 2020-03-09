package se.appshack.android.refactoring

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import se.appshack.android.refactoring.main.data.model.response.PokemonListResponse
import se.appshack.android.refactoring.main.data.remote.service.PokemonService
import se.appshack.android.refactoring.main.data.remote.source.PokemonRemoteDataSource
import se.appshack.android.refactoring.main.data.remote.source.PokemonRemoteDataSourceImpl

class PokemonRemoteDataSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var remoteDataSource: PokemonRemoteDataSource

    lateinit var service: PokemonService

    val pokemonListResponse = PokemonListResponse()
    val firstPokemonNumber = 1
    val secondPokemonNumber = 2

    val firstPokemonDetailsResponse = buildEmptyPokemonDetailsResponse()
    val secondPokemonDetailsResponse = buildEmptyPokemonDetailsResponse()

    val firstPokemonSpeciesResponse = buildEmptyPokemonSpeciesResponse()
    val secondPokemonSpeciesResponse = buildEmptyPokemonSpeciesResponse()

    @Before
    fun init() {
        service = mock {

            onBlocking {
                requestPokemons(151)
            } doReturn GlobalScope.async {
                Response.success(pokemonListResponse)
            }

            onBlocking {
                requestPokemonDetails(firstPokemonNumber)
            } doReturn GlobalScope.async {
                Response.success(firstPokemonDetailsResponse)
            }

            onBlocking {
                requestPokemonDetails(secondPokemonNumber)
            } doReturn GlobalScope.async {
                Response.success(secondPokemonDetailsResponse)
            }

            onBlocking {
                requestPokemonSpecies(firstPokemonNumber)
            } doReturn GlobalScope.async {
                Response.success(firstPokemonSpeciesResponse)
            }

            onBlocking {
                requestPokemonSpecies(secondPokemonNumber)
            } doReturn GlobalScope.async {
                Response.success(secondPokemonSpeciesResponse)
            }

        }
        remoteDataSource = PokemonRemoteDataSourceImpl(service, mainCoroutineRule.coroutineContext)
    }

    @Test
    fun `test "requestPokemons" should return successful response`() = runBlocking {
        val result = remoteDataSource.requestPokemons(151)
        assert(result == pokemonListResponse)
    }

    @Test
    fun `test "requestPokemonDetails" should return first pokemon details response`() = runBlocking {
        val result = remoteDataSource.requestPokemonDetails(firstPokemonNumber)
        assert(result == firstPokemonDetailsResponse)
    }

    @Test
    fun `test "requestPokemonDetails" should return second pokemon details response`() = runBlocking {
        val result = remoteDataSource.requestPokemonDetails(secondPokemonNumber)
        assert(result == secondPokemonDetailsResponse)
    }


    @Test
    fun `test "requestPokemonSpecies" should return first pokemon species response`() = runBlocking {
        val result = remoteDataSource.requestPokemonSpecies(firstPokemonNumber)
        assert(result == firstPokemonSpeciesResponse)
    }

    @Test
    fun `test "requestPokemonSpecies" should return second pokemon species response`() = runBlocking {
        val result = remoteDataSource.requestPokemonSpecies(secondPokemonNumber)
        assert(result == secondPokemonSpeciesResponse)
    }

}