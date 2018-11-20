package com.example.marco.bdmercado

class ProdCompradosArray {
    var prodComprado: ArrayList<ProdComprado> = ArrayList<ProdComprado>()

    fun addProd(cod:Int, nom:String, pre:Double, cant:Int){
        val p = ProdComprado(cod,nom,pre,cant)
        prodComprado?.add(p)
    }

    fun getProd():ArrayList<ProdComprado>{
        return prodComprado
    }
}