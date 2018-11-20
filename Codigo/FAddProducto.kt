package com.example.marco.bdmercado

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_fadd_producto.*
import kotlinx.android.synthetic.main.recycler_comprados.*
import java.io.IOException
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FAddProducto.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FAddProducto.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FAddProducto : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fadd_producto, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Badd.setOnClickListener {
            // your code to perform when the user clicks on the button
            addProducto()
        }
        Bdelete.setOnClickListener {
            // your code to perform when the user clicks on the button
            deleteProducto()
        }

        BClear.setOnClickListener {
            // your code to perform when the user clicks on the button
            limpiarLista()
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener") as Throwable
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FAddProducto.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FAddProducto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun addProducto(){
        try {
            var cod = 0
            var nom = "a"
            var pre = 0.0
            if (Tcodigo.text.toString() != "" && Tnombre.text.toString() != "" && Tprecio.text.toString() != "") {
                cod = Tcodigo.text.toString().toInt()
                nom = Tnombre.text.toString()
                pre = Tprecio.text.toString().toDouble()
                val txt = leerUrl("http://iesayala.ddns.net/marco/escribir_bd.php?codigo=$cod&nombre=$nom&precio=$pre")
                Toast.makeText(context, txt, Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){
            Toast.makeText(context,  "Error de conexión", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteProducto(){
        try {
            var cod = 0
            if (Tcodigo2.text.toString() != "") {
                cod = Tcodigo2.text.toString().toInt()
                val txt = leerUrl("http://iesayala.ddns.net/marco/borrar_prod.php?codigo=$cod")
                Toast.makeText(context, txt, Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){
            Toast.makeText(context,  "Error de conexión", Toast.LENGTH_SHORT).show()
        }
    }

    fun limpiarLista(){
        try {
            val txt = leerUrl("http://iesayala.ddns.net/marco/clear_list.php")
            Toast.makeText(context, txt, Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(context,  "Error de conexión", Toast.LENGTH_SHORT).show()
        }
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
            "Error con $(e.message)"
        }
        return respuesta
    }
}
