package com.dicoding.bwamov.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bwamov.DetailActivity
import com.dicoding.bwamov.R
import com.dicoding.bwamov.adapter.ComingSoonAdapter
import com.dicoding.bwamov.adapter.NowPlayingAdapter
import com.dicoding.bwamov.model.Film
import com.dicoding.bwamov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var databaseReference: DatabaseReference

    private var dataList = ArrayList<Film>()

    private fun currency(harga: Double, textView: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.text = format.format(harga)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        databaseReference = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.text = preferences.getValues("nama")

        if (preferences.getValues("saldo") == "") {
            preferences.getValues("saldo")?.toDouble()?.let {
                currency(it, tv_saldo)
            }
        } else {
            preferences.getValues("saldo")?.toDouble().let {
                if (it != null) {
                    currency(it, tv_saldo)
                }
            }
        }

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapshot in snapshot.children) {
                    val film = getDataSnapshot.getValue(Film::class.java)
                    film?.let {
                        dataList.add(it)
                    }
                }

                rv_now_playing.adapter = NowPlayingAdapter(dataList) {
                    val intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

                rv_coming_soon.adapter = ComingSoonAdapter(dataList) {
                    val intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
            }
        })
    }
}
