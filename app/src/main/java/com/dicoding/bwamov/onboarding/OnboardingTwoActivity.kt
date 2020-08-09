package com.dicoding.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.bwamov.R
import com.dicoding.bwamov.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_two.*

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        btn_home.setOnClickListener {
            val intent = Intent(this@OnboardingTwoActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        btn_lanjut.setOnClickListener {
            val intentLanjut = Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java)
            startActivity(intentLanjut)
        }
    }
}