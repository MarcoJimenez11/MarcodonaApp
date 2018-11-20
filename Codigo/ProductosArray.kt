package com.example.marco.bdmercado

class ProductosArray {
    var productos: ArrayList<Producto> = ArrayList<Producto>()

    fun addProd(cod:Int, nom:String, pre:Double){
        val p = Producto(cod,nom,pre)
        productos?.add(p)
    }

    fun getProd():ArrayList<Producto>{
        return productos
    }
}