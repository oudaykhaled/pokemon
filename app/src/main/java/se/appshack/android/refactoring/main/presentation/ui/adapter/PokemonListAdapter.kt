package se.appshack.android.refactoring.main.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import kotlinx.android.synthetic.main.viewholder_pokemon_list.view.*

import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.R
import se.appshack.android.refactoring.main.data.extensions.getID
import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse
import se.appshack.android.refactoring.main.domain.PokemonUseCase

class PokemonListAdapter(
    private val activity: Activity,
    private val data: List<NamedResponseModel>
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private var onPokemonClicked: ((namedResponseModel: NamedResponseModel) -> Any )? = null

    private val pokemonDetailsCache = HashMap<String, PokemonDetailsResponse>()

    private var pokemonDetailsRequester: ( (pokemonId: String, position: Int) -> Any )? = null

    fun addPokemonDetailsCache(pokemonID: String, pokemonDetails: PokemonDetailsResponse, position: Int){
        pokemonDetailsCache[pokemonID] = pokemonDetails
        notifyItemChanged(position)
    }

    fun setOnPokemonClicked( onPokemonClicked: ((namedResponseModel: NamedResponseModel) -> Any ) ): PokemonListAdapter{
        this.onPokemonClicked = onPokemonClicked
        return this
    }

    fun setPokemonDetailsRequester(pokemonDetailsRequester: ( (pokemonId: String, position: Int) -> Unit )): PokemonListAdapter {
        this.pokemonDetailsRequester = pokemonDetailsRequester
        return this
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PokemonViewHolder {
        return PokemonViewHolder(LayoutInflater.from(activity).inflate(R.layout.viewholder_pokemon_list, null, false))
    }

    override fun onBindViewHolder(pokemonViewHolder: PokemonViewHolder, i: Int) {
        val responseModel = data[i]

        pokemonViewHolder.bind(responseModel, i)

        pokemonViewHolder.setOnClickListener(View.OnClickListener {
            onPokemonClicked?.invoke(responseModel)
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(pokemon: NamedResponseModel, position: Int) {
            val number = String.format("#%s", pokemon.url?.substring(pokemon.url!!.indexOf("pokemon/") + 8, pokemon.url!!.length - 1))
            (itemView.findViewById<View>(R.id.pokemon_number) as TextView).text = number

            val formattedName = pokemon.name?.substring(0, 1)?.toUpperCase() + pokemon.name?.substring(1)
            (itemView.findViewById<View>(R.id.pokemon_name) as TextView).text = formattedName

            if (pokemonDetailsCache.containsKey(pokemon.getID().toString())){
                itemView.imageFront.visibility = View.VISIBLE
                itemView.animCircle.visibility = View.GONE
                Picasso.get().load(pokemonDetailsCache[pokemon.getID().toString()]?.sprites?.frontDefault).into(itemView.imageFront)
            }else {
                itemView.imageFront.visibility = View.GONE
                itemView.animCircle.visibility = View.VISIBLE
                pokemonDetailsRequester?.invoke(pokemon.getID().toString(), position)
            }

        }

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }
    }

}

