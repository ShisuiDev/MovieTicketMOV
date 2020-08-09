package com.dicoding.bwamov.adapter

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

class CheckoutAdapter(private var data: List<Checkout>, private var listener: (Checkout) -> Unit) :
    RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvJudul: TextView = view.findViewById(R.id.tv_judul)
        private val tvHarga: TextView = view.findViewById(R.id.tv_harga)

        @SuppressLint("SetTextI18n")
        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context: Context) {

            val localID = Locale("id","ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)
            tvHarga.text = formatRupiah.format(data.harga!!.toDouble())

            if (data.judul!!.startsWith("Total")) {
                tvJudul.text = data.judul
                tvJudul.setCompoundDrawables(null,null,null, null)
            } else {
                tvJudul.text = "Seat No." + data.judul
            }

            tvJudul.text = data.judul
            tvHarga.text = data.harga

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
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout,parent,false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }
}
