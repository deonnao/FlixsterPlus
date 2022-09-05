package com.deonnao.flixsterplus

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import org.json.JSONArray

@Parcelize
data class Person (
    val personId : Int,
    private val profilePath : String,
    val name : String,
) : Parcelable {
    @IgnoredOnParcel
    val profileImageUrl = "https://image.tmdb.org/t/p/w500/$profilePath"

    companion object {
        fun fromJsonArray(personJsonArray : JSONArray) : List<Person> {
            //list of people
            val people = mutableListOf<Person>()
            //populate the list of people
            for(i in 0 until personJsonArray.length()) {
                //grab the json object at the particular position
                val personJson = personJsonArray.getJSONObject(i)
                //add a person for each json object
                people.add(
                    //Person constructor
                    Person(
                        personJson.getInt("id"),
                        personJson.getString("profile_path"),
                        personJson.getString("name")
                    )
                )
            }
            return people
        }
    }
}

