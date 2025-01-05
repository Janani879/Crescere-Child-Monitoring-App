package com.example.childmonitoringel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AppCompatActivity

class input : AppCompatActivity() {
    // Firebase Database reference
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().getReference("Users")

        // Find views
        val nameField = findViewById<TextInputEditText>(R.id.inputName)
        val ageField = findViewById<TextInputEditText>(R.id.inputAge)
        val weightField = findViewById<TextInputEditText>(R.id.inputWeight)
        val btnintent = findViewById<Button>(R.id.submitbut)

        btnintent.setOnClickListener {
            // Collect user input data
            val name = nameField.text.toString().trim()
            val age = ageField.text.toString().trim()
            val weight = weightField.text.toString().trim()

            // Validate user inputs
            if (name.isEmpty() || age.isEmpty() || weight.isEmpty()) {
                if (name.isEmpty()) nameField.error = "Please enter your name"
                if (age.isEmpty()) ageField.error = "Please enter your age"
                if (weight.isEmpty()) weightField.error = "Please enter your weight"
                return@setOnClickListener
            }

            // Save data to Firebase
           Globalvar.weight= weight
            Globalvar.age=age
            var mail=Globalvar.mailId.toString()
            var password=Globalvar.password.toString()
            val user = User(name,mail, password,weight,age)

            // Write the data to Firebase
            database = FirebaseDatabase.getInstance().getReference("Users")


            database.child(mail).setValue(user)
                    // Navigate to Camera activity
                    val intent = Intent(applicationContext, Camera::class.java)
                    startActivity(intent)

                }
            }
        }


// Data class to store user input
/*data class UserInput(
    val name: String,
    val age: String,
    val weight: String
)*/
