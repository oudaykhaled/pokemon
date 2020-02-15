package se.appshack.android.refactoring.main.presentation.ui.activity

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.google.gson.Gson

import java.io.IOException
import java.util.ArrayList
import java.util.Comparator

import okhttp3.OkHttpClient
import okhttp3.Request

import se.appshack.android.refactoring.BuildConfig.pokeapiUrl
import se.appshack.android.refactoring.main.data.model.NamedResponseModel
import se.appshack.android.refactoring.main.data.model.PokemonListResponse
import se.appshack.android.refactoring.R
import se.appshack.android.refactoring.main.presentation.ui.adapter.PokemonListAdapter

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getPokemonListTask = GetPokemonListTask()
        getPokemonListTask.execute()
    }

    internal inner class GetPokemonListTask : AsyncTask<Void, Void, PokemonListResponse>() {

        override fun doInBackground(vararg ignore: Void): PokemonListResponse? {
            val client = OkHttpClient()

            val request = Request.Builder()
                    .url( "${pokeapiUrl}pokemon/?limit=151")
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build()

            var response: PokemonListResponse? = null
            try {
                val httpResponse = client.newCall(request).execute()
                response = Gson().fromJson(httpResponse.body!!.string(), PokemonListResponse::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return response
        }

        override fun onPostExecute(result: PokemonListResponse) {
            super.onPostExecute(result)

            val pokemonModels = ArrayList<NamedResponseModel>()

            val ids = ArrayList<Int>()
            var i = 0
            while (i < 20) {
                val id = (Math.random() * 151 + 1).toInt()
                if (ids.contains(id)) {
                    i--
                } else {
                    ids.add(id)
                }
                i++
            }

            ids.sortWith(Comparator { integer, t1 -> integer - t1 })

            for (i in ids) {
                pokemonModels.add(result.results[i - 1])
            }

            val pokemonAdapter = PokemonListAdapter(this@MainActivity, pokemonModels)
            val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
            recyclerView.adapter = pokemonAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            findViewById<View>(R.id.loader).visibility = View.GONE
        }
    }
}
