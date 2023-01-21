package com.example.ejemplointent2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*

class FourthActivity : AppCompatActivity() {

    // creating variables for
    // EditText and buttons.
    private var nombreProveedor: EditText? = null
    private var telefonoProveedor: EditText? = null
    private var direccionProveedor: EditText? = null
    private var btnEnviar: Button? = null

    // creating a variable for our
    // Firebase Database.
    var firebaseDatabase: FirebaseDatabase? = null

    // creating a variable for our Database
    // Reference for Firebase.
    var databaseReference: DatabaseReference? = null

    // creating a variable for
    // our object class
    var prov: Cliente? = null


    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        // initializing our edittext and button
        nombreProveedor = findViewById<EditText>(R.id.edtNombreProveedor)
        telefonoProveedor = findViewById<EditText>(R.id.edtTelefonoProveedor)
        direccionProveedor = findViewById<EditText>(R.id.edtDireccionProveedor)

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance()

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase!!.getReference("Proveedores")

        // initializing our object
        // class variable.
        prov = Cliente()
        btnEnviar = findViewById<Button>(R.id.btnEnviar)

        // adding on click listener for our button.
        btnEnviar!!.setOnClickListener {
            // getting text from our edittext fields.
            var name: String = nombreProveedor.text.toString()
            var phone: String = telefonoProveedor.text.toString()
            var address: String = direccionProveedor.text.toString()

            // below line is for checking weather the
            // edittext fields are empty or not.
            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(address)) {
                // if the text fields are empty
                // then show the below message.
                Toast.makeText(this@FourthActivity, "Please add some data.", Toast.LENGTH_SHORT).show()
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
        prov.nombre = name
        prov.direccion = address
        prov.telefono = phone

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(prov)

                // after adding this data we are showing toast message.
                Toast.makeText(this@FourthActivity, "data added", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(this@FourthActivity, "Fail to add data $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


