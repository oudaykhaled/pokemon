package se.appshack.android.refactoring.main.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import se.appshack.android.refactoring.R
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.core.presentation.ViewModelFactory
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.presentation.ui.adapter.PokemonListAdapter
import se.appshack.android.refactoring.main.presentation.viewmodel.PokemonViewModel
import javax.inject.Inject


class PokemonListFragment : DaggerFragment() {

    private var pokemonAdapter: PokemonListAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var viewModel: PokemonViewModel? = null

    private val pokemonPosition = HashMap<String, Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.pokemon_list)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(PokemonViewModel::class.java)
        viewModel?.getAllPokemonLiveData()?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> dismissLoading()
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let {lstPokemons -> populatePokemonsList(lstPokemons)}

                }
            }
        })

        viewModel?.getpokemonDetails()?.observe(viewLifecycleOwner, Observer {
            if (it.status != Status.SUCCESS) return@Observer
            val pokemonID = it.data?.id.toString()
            pokemonPosition[pokemonID]?.let { it1 ->
                it.data?.let { it2 ->
                    pokemonAdapter?.addPokemonDetailsCache(pokemonID, it2,
                        it1
                    )
                }
            }
        })

        viewModel?.requestPokemonsList()
    }

    private fun populatePokemonsList(lstPokemons: List<NamedResponseModel>) {
        recyclerview.layoutManager = GridLayoutManager(activity, 2)
        pokemonAdapter = activity?.let { PokemonListAdapter(it, lstPokemons) }
            ?.setOnPokemonClicked { navigateToPokemonDetails(it) }
            ?.setPokemonDetailsRequester { pokemonID, position ->
                pokemonPosition.put(pokemonID, position)
                viewModel?.requestPokemonDetails(pokemonID.toInt())
            }
        recyclerview.adapter = pokemonAdapter
    }

    private fun navigateToPokemonDetails(pokemon: NamedResponseModel) {
        viewModel?.selectedPokemon = pokemon
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_pokemonListFragment_to_pokemonDetailsFragment)
        }
    }

    private fun dismissLoading() {
        loader.visibility = View.GONE
    }

    private fun showLoading() {
        loader.visibility = View.VISIBLE
    }

}
