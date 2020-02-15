package se.appshack.android.refactoring.main.presentation.ui.activity

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import se.appshack.android.refactoring.R
import se.appshack.android.refactoring.main.data.model.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.PokemonSpeciesResponse
import java.io.IOException
import java.util.*

class PokemonDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent
        val pokemonName = intent.extras?.getString("POKEMON_NAME")
        title = pokemonName?.toUpperCase()

        val pokemonUrl = intent.extras?.getString("POKEMON_URL")
        val detailsTask = GetPokemonDetailsTask()
        detailsTask.execute(pokemonUrl)
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
