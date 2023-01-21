package com.example.ejemplointent2

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
    private var nombreCliente: EditText? = null
    private var telefonoCliente: EditText? = null
    private var direccionCliente: EditText? = null
    private var btnEnviar: Button? = null

    // creating a variable for our
    // Firebase Database.
    var firebaseDatabase: FirebaseDatabase? = null

    // creating a variable for our Database
    // Reference for Firebase.
    var databaseReference: DatabaseReference? = null

    // creating a variable for
    // our object class
    var cli: Cliente? = null


    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        // initializing our edittext and button
        nombreCliente = findViewById<EditText>(R.id.edtNombreCliente)
        telefonoCliente = findViewById<EditText>(R.id.edtTelefonoCliente)
        direccionCliente = findViewById<EditText>(R.id.edtDireccionCliente)

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance()

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase!!.getReference("Clientes")

        // initializing our object
        // class variable.
        cli = Cliente()
        btnEnviar = findViewById<Button>(R.id.btnEnviar)

        // adding on click listener for our button.
        btnEnviar!!.setOnClickListener {
            // getting text from our edittext fields.
            var name: String = nombreCliente.text.toString()
            var phone: String = telefonoCliente.text.toString()
            var address: String = direccionCliente.text.toString()

            // below line is for checking weather the
            // edittext fields are empty or not.
            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(address)) {
                // if the text fields are empty
                // then show the below message.
                Toast.makeText(this@FifthActivity, "Please add some data.", Toast.LENGTH_SHORT).show()
            } else {
                // else call the method to add
                // data to our database.
                addDatatoFirebase(name, address, phone)
            }
        }
    }

    private fun addDatatoFirebase(name: String, address: String, phone: String) {
        // below 3 lines of code is used to set
        // data in our object class.
        cli.nombre = name
        cli.direccion = address
        cli.telefono = phone

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(cli)

                // after adding this data we are showing toast message.
                Toast.makeText(this@FifthActivity, "data added", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(this@FifthActivity, "Fail to add data $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}