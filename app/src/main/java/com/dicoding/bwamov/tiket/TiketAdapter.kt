package com.dicoding.bwamov.tiket

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bwamov.R
import com.dicoding.bwamov.model.Checkout
import java.text.NumberFormat
import java.util.*

class TiketAdapter(private var data: List<Checkout>, private var listener: (Checkout) -> Unit) :
    RecyclerView.Adapter<TiketAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvJudul: TextView = view.findViewById(R.id.tv_judul)

        @SuppressLint("SetTextI18n")
        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context: Context) {

            tvJudul.text = "Seat No. " +  data.judul

            itemView.setOnClickListener{
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter= parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout_white,parent,false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }
}
