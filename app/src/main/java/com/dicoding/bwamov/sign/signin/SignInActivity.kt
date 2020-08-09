package com.dicoding.bwamov.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bwamov.home.HomeActivity
import com.dicoding.bwamov.R
import com.dicoding.bwamov.sign.signup.SignUpActivity
import com.dicoding.bwamov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private lateinit var iUsername: String
    private lateinit var iPassword: String

    private lateinit var mDatabase: DatabaseReference
    private lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preference = Preferences(this)

        preference.setValues("onboarding", "1")
        when {
            preference.getValues("status").equals("1") -> {
                finishAffinity()
                val goHome = Intent(this@SignInActivity, HomeActivity::class.java)
                startActivity(goHome)
            }
        }

        btn_home.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            when {
                iUsername == "" -> {
                    et_username.error = "Silahkan tulis username anda"
                    et_username.requestFocus()
                }
                iPassword == "" -> {
                    et_password.error = "Silahkan tulis password anda"
                    et_password.requestFocus()
                }
                else -> {
                    pushLogin(iUsername, iPassword)
                }
            }
        }

        btn_daftar.setOnClickListener {
            val goSignUpActivity = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(goSignUpActivity)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG)
                    .show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                when (user) {
                    null -> {
                        Toast.makeText(
                            this@SignInActivity,
                            "Username tidak ditemukan",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        if (user.password == iPassword) {

                            preference.setValues("nama", user.nama.toString())
                            preference.setValues("user", user.username.toString())
                            preference.setValues("url", user.url.toString())
                            preference.setValues("email", user.email.toString())
                            preference.setValues("saldo", user.saldo.toString())
                            preference.setValues("status", "1")

                            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@SignInActivity,
                                "Password anda salah",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        })
    }
}