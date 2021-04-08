package uabc.ic.juegodados

import kotlin.random.Random

class Dado(valor : Int =  1, estaSeleccionado : Boolean = false)  {
    var estaSeleccionado : Boolean = estaSeleccionado
    var valor : Int =  valor

    public fun lanzarDado() {
        if(!estaSeleccionado) {
            valor = Random.nextInt(1,6)
        }
    }
}