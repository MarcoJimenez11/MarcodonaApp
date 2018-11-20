package com.example.marco.bdmercado

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.recycler_comprados.view.*

class PCompAdapter(val items : ArrayList<ProdComprado>, val context: Context) : RecyclerView.Adapter<ViewHolder2>() {

    // Obtiene el número de datos
    override fun getItemCount(): Int {
        return items.size
    }

    //infla el layout activity_datos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(LayoutInflater.from(context).inflate(R.layout.recycler_comprados, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        val cod = items.get(position).codigo.toString()
        val nom = items.get(position).nombre
        val pre = String.format("%.2f", items.get(position).precio)
        val cant = items.get(position).cantidad.toString()
        holder?.tvDatosA?.text = cod
        holder?.tvDatosB?.text = nom
        holder?.tvDatosC?.text = "${pre} €"
        holder?.tvDatosD?.text = cant
        var txt = String.format("%.2f", items.get(position).precio * items.get(position).cantidad)
        holder?.itemView?.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "$nom: $txt €", Toast.LENGTH_SHORT).show()
        })
    }

}

class ViewHolder2 (view: View) : RecyclerView.ViewHolder(view) {
    // Mantiene el TextView
    val tvDatosA = view.tvComprado1
    val tvDatosB = view.tvComprado2
    val tvDatosC = view.tvComprado3
    val tvDatosD = view.tvComprado4
}
