package com.example.ejemplointent2

class Proveedor {

    var nombre: String? = null
        get() {
            //Llamada al getter de nombre
            return field
        }
        set(nuevoNombre) {
            //Llamada al setter de nombre
            field = nuevoNombre
        }


    var direccion: String? = null

        get() {
            //Llamada al getter de direccion
            return field
        }
        set(nuevaDireccion) {
            //Llamada al setter de direccion
            field = nuevaDireccion
        }


    var telefono: String? = null

        get() {
            //Llamada al getter de telefono
            return field
        }
        set(nuevoTelefono) {
            //Llamada al setter de telefono
            field = nuevoTelefono
        }

}