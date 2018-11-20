package com.example.marco.bdmercado

import android.content.Context
import android.os.StrictMode
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.recyclerdatos.view.*
import java.io.IOException
import java.net.URL

class DatosAdapter(val items : ArrayList<Producto>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Obtiene el número de datos
    override fun getItemCount(): Int {
        return items.size
    }

    //infla el layout activity_datos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerdatos, parent, false))
    }

    // carga datos del ArrayList aL TEXTVIEW view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cod = items.get(position).codigo.toString()
        val nom = items.get(position).nombre
        val pre = String.format("%.2f", items.get(position).precio)
        holder?.tvDatosA?.text = cod
        holder?.tvDatosB?.text = nom
        holder?.tvDatosC?.text = "${pre} €"
        holder?.itemView?.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,  items.get(position).nombre, Toast.LENGTH_SHORT).show()
            leerUrl("http://iesayala.ddns.net/marco/comprar_prod.php?codigo=$cod&nombre=$nom&precio=${pre.replace(',','.')}")
        })
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Mantiene el TextView
    val tvDatosA = view.textViewDatos
    val tvDatosB = view.textViewDatos2
    val tvDatosC = view.textViewDatos3
}

private fun leerUrl (direccion:String) : String {
    val respuesta = try{
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        URL(direccion)
            .openStream()
            .bufferedReader()
            .use {it.readText()}
    } catch (e: IOException){
        "Error al leer Url"
    }
    return respuesta
}