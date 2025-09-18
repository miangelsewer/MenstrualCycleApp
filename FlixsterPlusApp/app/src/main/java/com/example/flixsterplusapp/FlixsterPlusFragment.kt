package com.example.flixsterplusapp

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
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class FlixsterPlusFragment : Fragment(), OnListFragmentInteractionListener{
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    val view = inflater.inflate(R.layout.fragment_flixster_plus_list, container, false)
    val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
    val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
    val context = view.context

    recyclerView.layoutManager = LinearLayoutManager(context)

    updateAdapter(progressBar, recyclerView)
    return view
}

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client[
            "https://api.themoviedb.org/3/movie/now_playing",
            params, object: JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    progressBar.hide()
                    val dataJSON = json.jsonObject.getJSONArray("results")
                    val movieRawJSON = dataJSON.toString()
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<FlixsterPlus>>() {}.type
                    val models: List<FlixsterPlus> = gson.fromJson(movieRawJSON, arrayMovieType)
                    recyclerView.adapter = FlixsterPlusRecyclerViewAdapter(models, this@FlixsterPlusFragment)
                    Log.d("FlixsterPlusFragment", "response successful")
                }override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ){
                    progressBar.hide()
                    Log.e("FlixsterPlusFragment", "API Call Failed. Status code: $statusCode")
                    response?.let {
                        Log.e("FlixsterPlusFragment", "Error response: $it")
                    }
                    throwable?.let {
                        Log.e("FlixsterPlusFragment", "Throwable: ${it.message}", it) // Log the throwable itself for a full stack trace
                    }
                }
            }
        ]
    }

    override fun onItemClick(item: FlixsterPlus) {
        Toast.makeText(context, "Movie Name: " + item.name, Toast.LENGTH_LONG).show()
    }

}