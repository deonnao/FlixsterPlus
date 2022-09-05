package com.deonnao.flixsterplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

const val TAG = "MainActivity"
const val POPULAR_PEOPLE_URL = "https://api.themoviedb.org/3/person/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&append_to_response=known_for"

class MainActivity : AppCompatActivity() {
    //list to hold popular people
    private val people = mutableListOf<Person>()
    private lateinit var rvPeople : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPeople = findViewById(R.id.rvPeople)

        val personAdapter = PersonAdapter(this, people)
        rvPeople.adapter = personAdapter
        rvPeople.layoutManager = GridLayoutManager(this, 2)

        //create http client using asyncHttpClient library
        val client = AsyncHttpClient()
        val params = RequestParams()
        //make a get request to the url
        client [POPULAR_PEOPLE_URL, params, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "OnFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "OnSuccess: JSON data $json")
                try {
                    //hold the data that we request
                    val personJsonArray = json.jsonObject.getJSONArray("results")
                    //pass the json array that we just parsed and add the movies to the movies list
                    people.addAll(Person.fromJsonArray(personJsonArray))
                    personAdapter.notifyDataSetChanged()
                    Log.i(TAG, "People list $people")

                } catch(e: JSONException) {
                    Log.e(TAG, "Encountered exception $e")
                }
            }
        }]
    }
}