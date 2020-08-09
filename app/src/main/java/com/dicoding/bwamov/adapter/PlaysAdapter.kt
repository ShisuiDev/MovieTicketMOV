package com.dicoding.bwamov.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bwamov.R
import com.dicoding.bwamov.model.Plays

class PlaysAdapter(private var data: List<Plays>, private var listener: (Plays) -> Unit) :
    RecyclerView.Adapter<PlaysAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvJudul: TextView = view.findViewById(R.id.tv_judul)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: Plays, listener: (Plays) -> Unit, context: Context) {
            tvJudul.text = data.nama

            Glide.with(context)
                .load(data.url)
                .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_plays, parent, false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }
}
