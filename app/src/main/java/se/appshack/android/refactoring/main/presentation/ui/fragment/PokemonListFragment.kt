package se.appshack.android.refactoring.main.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import se.appshack.android.refactoring.R
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.core.presentation.ViewModelFactory
import se.appshack.android.refactoring.main.data.extensions.getID
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.presentation.ui.adapter.PokemonListAdapter
import se.appshack.android.refactoring.main.presentation.viewmodel.PokemonViewModel
import javax.inject.Inject


class PokemonListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var viewModel: PokemonViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(PokemonViewModel::class.java)
        viewModel?.getpokemonLiveData()?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> dismissLoading()
                Status.SUCCESS -> {
                    dismissLoading()
                    it.data?.let {lstPokemons -> populatePokemonsList(lstPokemons)}

                }
            }
        })
        viewModel?.requestPokemonsList()
    }

    private fun populatePokemonsList(lstPokemons: List<NamedResponseModel>) {
        recyclerview.layoutManager = LinearLayoutManager(activity)
        val pokemonAdapter = activity?.let { PokemonListAdapter(it, lstPokemons) }
            ?.setOnPokemonClicked { navigateToPokemonDetails(it) }
        recyclerview.adapter = pokemonAdapter
    }

    private fun navigateToPokemonDetails(pokemon: NamedResponseModel) {
        viewModel?.selectedPokemon = pokemon
        Log.d("TESTING", "navigateToPokemonDetails ${pokemon.getID()}")
        Log.d("TESTING", "navigateToPokemonDetails ${pokemon.name}")
        Log.d("TESTING", "navigateToPokemonDetails ${pokemon.url}")
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
