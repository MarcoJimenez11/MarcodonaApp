package com.example.marco.bdmercado

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_fprod_comprados.*
import java.io.IOException
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FProdComprados.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FProdComprados.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FProdComprados : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var prodComprado: ProdCompradosArray = ProdCompradosArray()

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
        return inflater.inflate(R.layout.fragment_fprod_comprados, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addProdComprados()
        val prds = prodComprado.getProd()
        RVComprados.layoutManager = LinearLayoutManager(context)
        RVComprados.adapter = PCompAdapter(prds, context!!)
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
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
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
         * @return A new instance of fragment FProdComprados.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FProdComprados().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun addProdComprados (){
        try {
            val jsonTexto = leerUrl("http://iesayala.ddns.net/marco/bdComprados_to_json.php")
            val gson = Gson()
            val prods = gson.fromJson(jsonTexto, ProdCompradosArray::class.java)
            var total = 0.0
            if (!jsonTexto.contains("null")) {
                for (item in prods.prodComprado.iterator()) {
                    prodComprado.addProd(item.codigo, item.nombre, item.precio, item.cantidad)
                    total += item.precio * item.cantidad
                }
            }
            var totaltxt = String.format("%.2f", total)
            tvTotalDinero.text = "Total: $totaltxt €"
        }catch (e: Exception){
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
