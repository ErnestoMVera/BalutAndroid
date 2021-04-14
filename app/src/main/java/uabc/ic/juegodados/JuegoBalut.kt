package uabc.ic.juegodados

class JuegoBalut {
    companion object {
        // Valores constantes para accesar a las diiferentes categorias en la tabla de puntuaciones.
        val FOURS = 0
        val FIVES = 1
        val SIXES = 2
        val STRAIGHT = 3
        val FULLHOUSE = 4
        val CHOICE = 5
        val BALUT = 6
    }
    // Valores de los dados del juego de balut inician en 1.
    var valoresDados : IntArray = IntArray(5) {1}
    // Tabla de puntuaciones para el Balut, esto va a llevar en cuenta.
    var tablaPuntuaciones : Array<IntArray> =  Array(7) { intArrayOf(0,0,0,0) }
    // Variable que cuenta la cantidad de turnos totales, cuando se llega a 28 el juego termina.
    /*
    * Contadores que llevan el registro de en donde se han repartido los puntos del jugador, estos se aseguran
    * que no haya más de 4 registros en una sola categoria.
    * */
    private var contadores : IntArray = IntArray(7) {0}
    fun setearDados(valoresDados : IntArray) {
        this.valoresDados = valoresDados
    }

    /**
     * Registra la puntuación en la tabla de puntuaciones del juego.
     * @param categoria La categoria donde se registraran esos puntos.
     * @param puntos Puntos que se registraran en esa categoria.
     * @return Retorna verdadero si fue posible registrarlo o falso si no,
     * falso significa que ya no es posible registrar puntuaciones en esa categoria.
     */
    fun registrarPuntuacion(categoria : Int, puntos : Int) : Boolean {
        if(contadores[categoria] <= 3) {
            val posicion = contadores[categoria]
            tablaPuntuaciones[categoria][posicion] = puntos
            contadores[categoria]++
            return true
        }
        return false
    }

    /**
     * Calcula la categoria choice del juego de Balut.
     * En esta categoria solo se utiliza la suma de los dados.
     * @return El cálculo de la categoria choice del juego de Balut.
     */
    fun calcularChoice() : Int {
        // En esta categoria solo se utiliza la suma de los dados.
        return valoresDados.sum()
    }

    /**
     * Calcula la categoria fours del juego de Balut.
     * Esta categoria toma en cuenta la cantidad de 4's que aparecen en los dados y las suma.
     * @return El cálculo de la categoria fours del juego de Balut.
     */
    fun calcularFours(): Int {
        // Obtener la suma del arreglo tal que si el número es 4 se sume.
        return valoresDados.sumOf { numero  ->
            if(numero == 4) numero
            else 0
        }
    }
    /**
     * Calcula la categoria fives del juego de Balut.
     * Esta categoria toma en cuenta la cantidad de 5's que aparecen en los dados y las suma.
     * @return El cálculo de la categoria fives del juego de Balut.
     */
    fun calcularFives(): Int {
        // Obtener la suma del arreglo tal que si el número es 5 se sume.
        return valoresDados.sumOf { numero ->
            if(numero == 5) numero
            else 0
        }
    }
    /**
     * Calcula la categoria sixes del juego de Balut.
     * Esta categoria toma en cuenta la cantidad de 6's que aparecen en los dados y las suma.
     * @return El cálculo de la categoria sixes del juego de Balut.
     */
    fun calcularSixes(): Int {
        // Obtener la suma del arreglo tal que si el número es 6 se sume.
        return valoresDados.sumOf { numero  ->
            if(numero == 6) numero
            else 0
        }
    }
    /**
     * Calcula la categoria straight del juego de Balut.
     * Esta categoria toma en cuenta si los resultados de los dados son una secuencia seguida de numeros.
     * @return El cálculo de la categoria straight del juego de Balut.
     */
    fun calcularStraight() : Int {
        // Revisar que no haya valores duplicados, si no no puede ser un straight.
        if(valoresDados.size != valoresDados.distinct().count()) {
            return 0
        }
        val suma = valoresDados.sum()
        // Las unicas sumas validas para la secuencia valida es 20 y 15.
        // Como ya descartamos el caso en el que esta
        if(suma == 20 || suma == 15) {
            return suma
        }
        return 0
    }
    /**
     * Calcula la categoria Full House del juego de Balut.
     * Esta categoria toma en cuena si hay 3 numeros de dados iguales y dos diferentes
     * @return El cálculo de la categoria Full House del juego de Balut.
     */
    fun calcularFullHouse() : Int {
        if(valoresDados.distinct().count() != 2)
            return 0
        // Si el primer dato que se repite se repite exactamente 2 o 3 veces y solo hay dos datos diferentes en el arreglo es un full house.
        val cuenta = valoresDados.count { it == valoresDados[0] } 
        if(cuenta == 2 || cuenta == 3) {
            return valoresDados.sum()
        }
        return 0
    }
    /**
     * Calcula la categoria Balut del juego de Balut.
     * Esta categoria verifica si los 5 dados son iguales.
     * @return El cálculo de la categoria Balut del juego de Balut.
     */
    fun calcularBalut(): Int {
        // Valor de referencia del dado.
        val inicial = valoresDados[0]
        // Si todos los numeros son iguales hay un balut.
        val esBalut = valoresDados.all { numero ->
             numero == inicial
        }
        if(esBalut) {
            return 20 + valoresDados.sum()
        }
        return 0
    }

    fun calcularTodo() : IntArray {
        val valores = intArrayOf(calcularFours(), calcularFives(), calcularSixes(), calcularStraight(), calcularFullHouse(), calcularChoice(), calcularBalut())
        return valores
    }
}