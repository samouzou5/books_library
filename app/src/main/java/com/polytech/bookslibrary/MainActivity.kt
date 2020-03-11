package com.polytech.bookslibrary

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var username: EditText
    lateinit var password: EditText
    var firebaseInstance = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun authenticateUser(view: View) {
        username = findViewById(R.id.login)
        password = findViewById(R.id.password)
        val informationChecker =
            checkUsernameAndPassword(username.text.toString(), password.text.toString())

        if (informationChecker) {
            firebaseInstance.signInWithEmailAndPassword(
                username.text.toString(),
                password.text.toString()
            ).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                println(task.isSuccessful)
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Authent OK", Toast.LENGTH_SHORT).show()
                    redirectToMainBookList()
                } else if(!task.isSuccessful) {
                    Toast.makeText(applicationContext, "Authent échouée", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun redirectToMainBookList() {
        val intent = Intent(this, MainBooksList::class.java)
        intent.putExtra("id", firebaseInstance.currentUser)
        startActivity(intent)
    }

    fun checkUsernameAndPassword(username: String, password: String): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Information")
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
        }
        if (username.isEmpty() && password.isEmpty()) {
            builder.setMessage("Le username et le mot de passe sont requis")
            builder.show()
            return false
        }

        if (username.isEmpty()) {
            builder.setMessage("Le username est requis")
            builder.show()
            return false
        }

        if (password.isEmpty()) {
            builder.setMessage("Le mot de passe est requis")
            builder.show()
            return false
        }
        return true
    }
}
