package com.example.ejemplointent2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*

class FifthActivity : AppCompatActivity() {
    // creating variables for
    // EditText and buttons.
    lateinit var codigoCliente:EditText
    lateinit var nombreCliente:EditText
    lateinit var telefonoCliente: EditText
    lateinit var direccionCliente: EditText

    lateinit var insertarDatos: Button
    lateinit var atras: Button

    // creating a variable for our
    // Firebase Database.
    lateinit var firebaseDatabase: FirebaseDatabase

    // creating a variable for our Database
    // Reference for Firebase.
    lateinit var databaseReference: DatabaseReference


    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        // initializing our edittext and button
        codigoCliente = findViewById<EditText>(R.id.edtCodigoCliente)
        nombreCliente = findViewById<EditText>(R.id.edtNombreCliente)
        telefonoCliente = findViewById<EditText>(R.id.edtTelefonoCliente)
        direccionCliente = findViewById<EditText>(R.id.edtDireccionCliente)

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance()

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase!!.getReference("MyDatabase")

        // initializing our object
        // class variable.

        insertarDatos = findViewById<Button>(R.id.btnEnviarCliente)
        atras = findViewById<Button>(R.id.btnAtrasFifth)

        // adding on click listener for our button.
        insertarDatos.setOnClickListener {
            // getting text from our edittext fields.
            var code: String = codigoCliente.text.toString()
            var name: String = nombreCliente.text.toString()
            var phone: String = telefonoCliente.text.toString()
            var address: String = direccionCliente.text.toString()

            // below line is for checking weather the
            // edittext fields are empty or not.
            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(address)) {
                // if the text fields are empty
                // then show the below message.
                Toast.makeText(this@FifthActivity, "Por favor, introduce los datos que quieres guardar.", Toast.LENGTH_SHORT).show()
            } else {
                // else call the method to add
                // data to our database.
                addDatatoFirebase(code, name, address, phone)
            }
        }

        atras.setOnClickListener{
            val toThird = Intent(this, ThirdActivity::class.java)
            startActivity(toThird)
        }
    }

    private fun addDatatoFirebase(code: String, name: String, address: String, phone: String) {
        // below 3 lines of code is used to set
        // data in our object class.

        val cliente = Cliente(code, name, address, phone)


        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child("Clientes").child(code).setValue(cliente)

                // after adding this data we are showing toast message.
                Toast.makeText(this@FifthActivity, "Datos guardados", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(this@FifthActivity, "No se pudieron guardar los datos $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}