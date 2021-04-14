package uabc.ic.juegodados
class Dado(valor : Int =  1, estaSeleccionado : Boolean = false)  {
    var estaSeleccionado : Boolean = estaSeleccionado
    var valor : Int =  valor

    fun lanzarDado() {
        if(!estaSeleccionado) {
            valor = (1..6).random()
        }
    }
}