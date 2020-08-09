package com.dicoding.bwamov.checkout

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.bwamov.R
import com.dicoding.bwamov.model.Checkout
import com.dicoding.bwamov.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

@Suppress("NAME_SHADOWING")
class PilihBangkuActivity : AppCompatActivity() {

    companion object {
        var statusA1: Boolean = false
        var statusA2: Boolean = false
        var total: Int = 0
    }

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data = intent.getParcelableExtra<Film>("data")
        tv_judul.text = data?.judul

        A1.setOnClickListener {
            when {
                statusA1 -> {
                    A1.setImageResource(R.drawable.shape_line_white)
                    statusA1 = false
                    total -= 1
                    beliTiket(total)
                }
                else -> {
                A1.setImageResource(R.drawable.ic_rectangle_green)
                    statusA1 = true
                    total += 1
                    beliTiket(total)

                    val data = Checkout("A1", "70000")
                    dataList.add(data)
                }
            }
        }

        A2.setOnClickListener {
            when {
                statusA2 -> {
                    A2.setImageResource(R.drawable.shape_line_white)
                    statusA2 = false
                    total -= 1
                    beliTiket(total)
                }
                else -> {
                    A2.setImageResource(R.drawable.ic_rectangle_green)
                    statusA2 = true
                    total += 1
                    beliTiket(total)

                    val data = Checkout("A1", "70000")
                    dataList.add(data)
                }
            }
        }

        btn_home.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java).putExtra("data", dataList)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun beliTiket(total: Int) {
        if (total == 0) {
            btn_home.text = "Beli Tiket"
            btn_home.visibility = View.INVISIBLE
        } else {
            btn_home.text = "Beli Tiket ($total)"
            btn_home.visibility = View.VISIBLE
        }
    }
}