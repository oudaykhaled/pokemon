package se.appshack.android.refactoring.main.presentation.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_pokemon_details.*

import se.appshack.android.refactoring.R
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.core.presentation.ViewModelFactory
import se.appshack.android.refactoring.main.data.extensions.getID
import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse
import se.appshack.android.refactoring.main.presentation.viewmodel.PokemonViewModel
import java.util.*
import javax.inject.Inject

class PokemonDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var viewModel: PokemonViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_pokemon_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(PokemonViewModel::class.java)
        activity?.title = viewModel?.selectedPokemon?.name?.toUpperCase().toString()

        viewModel?.selectedPokemon?.getID()?.let {
            viewModel?.getpokemonSpecies()?.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> dismissLoading()
                    Status.SUCCESS -> {
                        dismissLoading()

                        it.data?.let {pokemon -> populatePokemonSpecies(pokemon)}
                    }
                }
            })
        }

        viewModel?.selectedPokemon?.getID()?.let {
            viewModel?.getpokemonDetails()?.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> dismissLoading()
                    Status.SUCCESS -> {
                        dismissLoading()
                        it.data?.let {pokemon -> populatePokemonDetails(pokemon)}
                    }
                }
            })
        }

        viewModel?.selectedPokemon?.getID()?.let { viewModel?.requestPokemonSpecies(it) }
        viewModel?.selectedPokemon?.getID()?.let { viewModel?.requestPokemonDetails(it) }
    }

    private fun populatePokemonDetails(result: PokemonDetailsResponse) {
        Picasso.get().load(result.sprites?.frontDefault).into(imageFront)
        Picasso.get().load(result.sprites?.backDefault).into(imageBack)
        pokemonNumber.text = String.format("#%s", result.id)
        val formattedName = result.name?.substring(0, 1)?.toUpperCase() + result.name?.substring(1)
        pokemonName.text = formattedName
        Collections.sort(result.types) { pokemonTypeModel, t1 -> pokemonTypeModel.slot - t1.slot }
        var types = ""
        for (i in result.types?.indices!!) {
            val typeModel = result.types!![i]
            types += typeModel.type?.name?.substring(0, 1)?.toUpperCase() + typeModel.type?.name?.substring(1)
            if (i < result.types!!.size - 1)
                types += ", "
        }
        pokemonTypes.text = types
        pokemonHeight.text = String.format("%s decimetres", result.height)
        pokemonWeight.text = String.format("%s hectograms", result.weight)
    }

    private fun populatePokemonSpecies(pokemon: PokemonSpeciesResponse) {
        var genus = ""
        pokemon.genera?.let {
            for (genusModel in it) {
                if (genusModel.language?.name == "en") {
                    genus = genusModel.genus.toString()
                    break
                }
            }
        }
        pokemonSpecies.text = genus
    }

    private fun dismissLoading() {
        loader.visibility = View.GONE
    }

    private fun showLoading() {
        loader.visibility = View.VISIBLE
    }

}
