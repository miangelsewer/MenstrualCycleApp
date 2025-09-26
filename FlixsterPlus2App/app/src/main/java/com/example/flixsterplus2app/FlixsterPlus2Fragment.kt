package com.example.flixsterplus2app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class FlixsterPlus2Fragment : Fragment(), OnListFragmentInteractionListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragement_flixster_plus2_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.tvShows) as RecyclerView
        
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        Fuel.get("https://api.themoviedb.org/3/tv/popular", listOf("api_key" to API_KEY))
            .responseJson { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        progressBar.hide()
                        val responseJson = result.get()
                        val dataJSON = responseJson.obj().getJSONArray("results")
                        val movieRawJSON = dataJSON.toString()

                        val gson = Gson()
                        val arrayTvType = object : TypeToken<List<FlixsterPlus2>>() {}.type
                        val models: List<FlixsterPlus2> = gson.fromJson(movieRawJSON, arrayTvType)
                        
                        recyclerView.adapter = FlixsterPlus2RecyclerViewAdapter(models, this@FlixsterPlus2Fragment)
                        Log.d("FlixsterPlusFragment", "response successful")
                    }
                    is Result.Failure -> {
                        progressBar.hide()
                        val ex = result.getException()
                        Log.e("FlixsterPlus2Fragment", "API Call Failed: ${ex.message}")
                    }
                }
            }
    }

    override fun onItemClick(item: FlixsterPlus2) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EXTRA_TV_SHOW", item)
        startActivity(intent)
    }
}
