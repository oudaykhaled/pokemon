package se.appshack.android.refactoring.main.presentation.ui.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import se.appshack.android.refactoring.R
import se.appshack.android.refactoring.core.Result
import se.appshack.android.refactoring.core.Status
import se.appshack.android.refactoring.main.data.extensions.getID
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.data.model.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.PokemonSpeciesResponse
import se.appshack.android.refactoring.main.domain.PokemonUseCase
import se.appshack.android.refactoring.main.presentation.ui.adapter.PokemonListAdapter
import java.io.IOException
import java.util.*
import javax.inject.Inject

class PokemonDetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var useCase: PokemonUseCase

    private val pokemonLiveData = MediatorLiveData<Result<PokemonSpeciesResponse>>()

    private var namedResponseModel: NamedResponseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent
        namedResponseModel = intent.extras?.getParcelable(TAG_NAMED_RESPONSE_MODEL)
        title = namedResponseModel?.name?.toUpperCase()

        val pokemonUrl = namedResponseModel?.url
        val detailsTask = GetPokemonDetailsTask()
        detailsTask.execute(pokemonUrl)

        pokemonLiveData.observe(this, androidx.lifecycle.Observer { pokemonDetails ->
            when (pokemonDetails.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> dismissLoading()
                Status.SUCCESS -> {
                    dismissLoading()
                    pokemonDetails.data?.let { showData(it) }
                }
            }
        })

        lifecycleScope.launchWhenCreated {
            namedResponseModel?.getID()?.let { useCase.getPokemonDetails(it) }?.let {
                pokemonLiveData.addSource(it) {
                    pokemonLiveData.value = it
                }
            }
        }

    }

    private fun showData(result: PokemonSpeciesResponse) {
        var genus = ""
        result.genera?.let {
            for (genusModel in it) {
                if (genusModel.language?.name == "en") {
                    genus = genusModel.genus.toString()
                    break
                }
            }
        }

        (findViewById<View>(R.id.pokemonSpecies) as TextView).text = genus

        findViewById<View>(R.id.loader).visibility = View.GONE
    }


    private fun dismissLoading() {
        findViewById<View>(R.id.loader).visibility = View.GONE
    }

    private fun showLoading() {
        findViewById<View>(R.id.loader).visibility = View.VISIBLE
    }


    companion object{

        private const val TAG_NAMED_RESPONSE_MODEL = "TAG_NAMED_RESPONSE_MODEL"

        fun newIntent(context: Context, namedResponseModel: NamedResponseModel): Intent {
            val intent = Intent(context, PokemonDetailsActivity::class.java)
            intent.putExtra(TAG_NAMED_RESPONSE_MODEL, namedResponseModel)
            return intent
        }

    }


    inner class GetPokemonDetailsTask : AsyncTask<String, Void, PokemonDetailsResponse>() {

        override fun doInBackground(vararg urls: String): PokemonDetailsResponse? {
            val client = OkHttpClient()

            val request = Request.Builder()
                    .url(urls[0])
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build()

            var response: PokemonDetailsResponse? = null
            try {
                val httpResponse = client.newCall(request).execute()
                val jsonBody = httpResponse.body?.string()
                response = Gson().fromJson(jsonBody, PokemonDetailsResponse::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return response
        }

        override fun onPostExecute(result: PokemonDetailsResponse) {
            super.onPostExecute(result)

            val imageFront = findViewById<ImageView>(R.id.imageFront)

            Picasso.get().load(result.sprites?.urlFront).into(imageFront)

            val imageBack = findViewById<ImageView>(R.id.imageBack)
            Picasso.get().load(result.sprites?.urlBack).into(imageBack)

            (findViewById<View>(R.id.pokemonNumber) as TextView).text = String.format("#%s", result.id)

            val formattedName = result.name?.substring(0, 1)?.toUpperCase() + result.name?.substring(1)
            (findViewById<View>(R.id.pokemonName) as TextView).text = formattedName

            Collections.sort(result.types) { pokemonTypeModel, t1 -> pokemonTypeModel.slot - t1.slot }

            var types = ""
            for (i in result.types?.indices!!) {
                val typeModel = result.types!![i]
                types += typeModel.type?.name?.substring(0, 1)?.toUpperCase() + typeModel.type?.name?.substring(1)
                if (i < result.types!!.size - 1)
                    types += ", "
            }

            (findViewById<View>(R.id.pokemonTypes) as TextView).text = types

            (findViewById<View>(R.id.pokemonHeight) as TextView).text = String.format("%s decimetres", result.height)

            (findViewById<View>(R.id.pokemonWeight) as TextView).text = String.format("%s hectograms", result.weight)

            GetPokemonSpeciesDetailsTask().execute(result.species?.url)
        }
    }

    inner class GetPokemonSpeciesDetailsTask : AsyncTask<String, Void, PokemonSpeciesResponse>() {

        override fun doInBackground(vararg urls: String): PokemonSpeciesResponse? {
            val client = OkHttpClient()

            val request = Request.Builder()
                    .url(urls[0])
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build()

            var response: PokemonSpeciesResponse? = null
            try {
                val httpResponse = client.newCall(request).execute()
                val jsonBody = httpResponse.body?.string()
                response = Gson().fromJson(jsonBody, PokemonSpeciesResponse::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return response
        }

        override fun onPostExecute(result: PokemonSpeciesResponse) {
            super.onPostExecute(result)

            var genus = ""
            for (genusModel in result.genera!!) {
                if (genusModel.language?.name == "en") {
                    genus = genusModel.genus!!
                    break
                }
            }

            (findViewById<View>(R.id.pokemonSpecies) as TextView).text = genus

            findViewById<View>(R.id.loader).visibility = View.GONE
        }
    }

}
