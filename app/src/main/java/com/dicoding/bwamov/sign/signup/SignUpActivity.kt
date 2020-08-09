package com.dicoding.bwamov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.bwamov.R
import com.dicoding.bwamov.sign.signin.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var nama: String
    private lateinit var email: String

    private lateinit var firebaseInstance: FirebaseDatabase
    private lateinit var database: DatabaseReference
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseInstance = FirebaseDatabase.getInstance()
        database = FirebaseDatabase.getInstance().reference
        databaseReference = firebaseInstance.getReference("User")

        btn_lanjutkan.setOnClickListener {
            username = edt_username.text.toString()
            password = edt_password.text.toString()
            email = edt_email.text.toString()
            nama = edt_nama.text.toString()

            when {
                username == "" -> {
                    edt_username.error = "Silahkan isi username anda"
                    edt_username.requestFocus()
                }
                password == "" -> {
                    edt_password.error = "silahkan isi password anda"
                    edt_password.requestFocus()
                }
                nama == "" -> {
                    edt_nama.error = "silahkan isi nama anda"
                    edt_password.requestFocus()
                }
                email == "" -> {
                    edt_email.error = "silahkan isi email anda"
                    edt_email.requestFocus()
                }
                else -> {
                    saveUsername(username, nama, password, email)
                }
            }
        }
    }

    private fun saveUsername(username: String, nama: String, password: String, email: String) {
        var user = User()
        user.email = email
        user.username = username
        user.password = password
        user.nama = nama

        when {
            username != null -> {
                checkingUsername(username, user)
            }
        }
    }

    private fun checkingUsername(username: String, data: User) {
        databaseReference.child(username).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "" + error.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)
                if (user == null) {
                    databaseReference.child(username).setValue(data)

                    var goSignUpPhoto =
                        Intent(this@SignUpActivity, SignUpPhotoActivity::class.java).putExtra(
                            "nama", data.nama
                        )
                    startActivity(goSignUpPhoto)
                }
                else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "User sudah digunakan",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}