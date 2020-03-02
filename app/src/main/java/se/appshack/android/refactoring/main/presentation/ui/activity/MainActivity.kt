package se.appshack.android.refactoring.main.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerAppCompatActivity
import se.appshack.android.refactoring.R
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.domain.PokemonUseCase
import se.appshack.android.refactoring.main.presentation.ui.adapter.PokemonListAdapter
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var useCase: PokemonUseCase

    private val pokemonLiveData = MediatorLiveData<Result<List<NamedResponseModel>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonLiveData.observe(this, Observer { lstPokemons ->
            when (lstPokemons.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> dismissLoading()
                Status.SUCCESS -> {
                    dismissLoading()
                    val pokemonAdapter =
                        lstPokemons.data?.let { it1 -> PokemonListAdapter(this@MainActivity, it1) }
                            ?.setOnPokemonClicked {pokemonModel -> openPokemonDetailsActivity(pokemonModel)}
                    val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
                    recyclerView.adapter = pokemonAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        })

        lifecycleScope.launchWhenCreated {
            pokemonLiveData.addSource( useCase.getAllPokemons()) {
                pokemonLiveData.value = it
            }
        }

    }

    private fun openPokemonDetailsActivity(pokemonModel: NamedResponseModel) {
        MainActivity@this.startActivity(PokemonDetailsActivity.newIntent(MainActivity@this, pokemonModel))
    }

    private fun dismissLoading() {
        findViewById<View>(R.id.loader).visibility = View.GONE
    }

    private fun showLoading() {
        findViewById<View>(R.id.loader).visibility = View.VISIBLE
    }

}
