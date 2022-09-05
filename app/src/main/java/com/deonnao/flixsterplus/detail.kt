package com.deonnao.flixsterplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

const val TAG2 = "detail"
class detail : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val ivKnownPerson = findViewById<ImageView>(R.id.ivKnownPerson)
        val personName = findViewById<TextView>(R.id.tvPersonName)
        val bio = findViewById<TextView>(R.id.tvBio)
        val birthday = findViewById<TextView>(R.id.tvDateOfBirth)
        val birthPlace = findViewById<TextView>(R.id.tvPlaceOfBirth)

        val person = intent.getParcelableExtra<Person>(PERSON_EXTRA) as Person
        val id = person.personId
        personName.text = person.name
        Glide.with(this).load(person.profileImageUrl).into(ivKnownPerson)

        val Person_details = "https://api.themoviedb.org/3/person/$id?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
        //create http client using asyncHttpClient library
        val client = AsyncHttpClient()
        val params = RequestParams()
        //make a get request to the url
        client [Person_details, params, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG2, "OnFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG2, "OnSuccess: JSON data $json")
                try {
                    //hold the data that we request
                    val bioString = json.jsonObject.getString("biography")
                    val birthdayString = json.jsonObject.getString("birthday")
                    val birthPlaceString = json.jsonObject.getString("place_of_birth")

                    bio.text = bioString
                    birthday.text = birthdayString
                    birthPlace.text = birthPlaceString

                } catch(e: JSONException) {
                    Log.e(TAG2, "Encountered exception $e")
                }
            }
        }]
    }
}
