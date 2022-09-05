package com.deonnao.flixsterplus

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val PERSON_EXTRA = "PERSON_EXTRA"
class PersonAdapter(private val context: Context, private val people: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int {
        return people.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val ivPerson = itemView.findViewById<ImageView>(R.id.ivPerson)

        init {
            itemView.setOnClickListener(this)
        }
        fun bind(person: Person) {
            tvName.text = person.name
            Glide.with(context).load(person.profileImageUrl).into(ivPerson)
        }

        override fun onClick(v: View?) {
            // 1. get notified of the particular movie that was clicked
            val person = people[adapterPosition]
            //2. use the intent system to navigate the new activity
            val intent = Intent(context, detail::class.java)
            intent.putExtra(PERSON_EXTRA, person)
            context.startActivity(intent)
        }
    }
}