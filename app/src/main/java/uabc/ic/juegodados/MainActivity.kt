package uabc.ic.juegodados

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    private lateinit var layout : ConstraintLayout
    // Arreglo con los drawables de los dados.
    private var nombreDados : IntArray = intArrayOf(
        R.drawable.dadocara1,
        R.drawable.dadocara2,
        R.drawable.dadocara3,
        R.drawable.dadocara4,
        R.drawable.dadocara5,
        R.drawable.dadocara6
    )
    // Arreglo de id de los Image View que luego se mapeara a un arreglo de ImageView.
    private var idImagenes : IntArray = intArrayOf(
            R.id.dado1,
            R.id.dado2,
            R.id.dado3,
            R.id.dado4,
            R.id.dado5
    )
    private var idBotones = intArrayOf(
        R.id.btnEscogerFours,
        R.id.btnEscogerFives,
        R.id.btnEscogerSixes,
        R.id.btnEscogerStraight,
        R.id.btnEscogerFullHouse,
        R.id.btnEscogerChoice,
        R.id.btnEscogerBalut
    )
    private var idtextosPuntos = intArrayOf(
        R.id.puntuacionFours,
        R.id.puntuacionFives,
        R.id.puntuacionSixes,
        R.id.puntuacionStraight,
        R.id.puntuacionFullHouse,
        R.id.puntuacionChoice,
        R.id.puntuacionBalut
    )
    // Boton de tirar los dados.
    private lateinit var botonRoll : Button
    // TextViews que marcan las puntuaciones.
    private lateinit var textosPuntos : Array<TextView>
    // Botones para registrar las puntuaciones.
    private lateinit var botones : Array<Button>
    // Array de objetos de tipo dado.
    private var dados : Array<Dado> = Array(5) { Dado()}
    // Arreglo de ImageView.
    private lateinit var imagenes : Array<ImageView>
    // TextView que cuenta los rolls restantes.
    private lateinit var txtRollsRestantes : TextView
    // variable que cuenta los rolls restantes.
    private var rolls = 3
    // TextView que cuenta los turnos restantes.
    private lateinit var txtTurnosRestantes : TextView
    // Variable que cuenta los turnos restantes.
    private var turnos = 28
    private lateinit var juegoBalut: JuegoBalut
    var registrosPuntos : ArrayList<ElementoPuntuacion> = ArrayList()
    var colorFondo = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Layout de la aplicación.
        layout = findViewById(R.id.miLayout)
        txtTurnosRestantes = findViewById(R.id.textoTurnosRestantes)
        txtRollsRestantes = findViewById(R.id.textoTirosRestantes)
        botones = Array(7) {index -> findViewById(idBotones[index])}
        textosPuntos = Array(7) {index -> findViewById(idtextosPuntos[index])}
        // Arreglo de imagenes de imageView.
        imagenes = Array(5) {index -> findViewById(idImagenes[index])}
        juegoBalut = JuegoBalut()
        botonRoll = findViewById(R.id.btnRoll)
        for ((index,imagen) in imagenes.withIndex()) {
            imagen.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v : View) {
                    // Si esta seleccionado lo deselecciona.
                    if(dados[index].estaSeleccionado) {
                            dados[index].estaSeleccionado = false
                            imagen.setBackground(null)
                    }
                    // Si esta deseleccionado lo selecciona.
                    else {
                        dados[index].estaSeleccionado = true
                        val highlight : Drawable? = ContextCompat.getDrawable(applicationContext, R.drawable.highlight)
                        imagen.setBackground(highlight)
                    }
                }
            })
        }
        for ((index,boton) in botones.withIndex()) {
            boton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v : View) {
                    val registrado = juegoBalut.registrarPuntuacion(index, textosPuntos[index].getText().toString().toInt())
                    if(!registrado) {
                        Toast.makeText(applicationContext, "No puede ser registrado, Esta categoria esta llena", Toast.LENGTH_SHORT).show()
                        return
                    }
                    turnos--
                    if(turnos == 0) {
                        println(juegoBalut.calcularPuntuacionesFinales())
                        registrarPuntuacion(juegoBalut.calcularPuntuacionesFinales())
                    }
                    txtTurnosRestantes.setText(turnos.toString())
                    bloquearBotones()
                    desbloquearRoll()
                    rolls = 3
                    txtRollsRestantes.setText(rolls.toString())
                    reiniciarDados()
                    registrosPuntos.add(ElementoPuntuacion(index, textosPuntos[index].getText().toString().toInt(), juegoBalut.valoresDados))
                }
            })
        }
        layout.setBackgroundResource(R.color.azulClaro)
        // Bloquear botones.
        bloquearBotones()
        // Poner los dados en el estado inicial de todos uno.
        reiniciarDados()
        reiniciarRolls()
        reiniciarTurnos()
        // Setear el background en azul.
        //layout.setBackgroundResource(R.color.azulClaro)
        // Registrar el layour para el menú flotante.
        registerForContextMenu(layout)
    }

    private fun bloquearBotones() {
        for(boton in botones) {
            boton.isEnabled = false
        }
    }

    private  fun desbloquearRoll() {
        botonRoll.isEnabled = true
    }

    private fun desbloquearBotones() {
        for(boton in botones) {
            boton.isEnabled = true
        }
    }

    private fun reiniciarTurnos() {
        turnos = 28
        txtTurnosRestantes.setText(turnos.toString())
    }

    private fun reiniciarRolls() {
        rolls = 3
        txtRollsRestantes.setText(rolls.toString())
    }

    /**
     * Función de lanzar los dadso cuando se presiona el botón.
     */
    fun tirarDados(v : View) {
        if(rolls == 3) {
            desbloquearBotones()
        }
        rolls--
        if(rolls == 0) {
            rolls = 3
            bloquearRoll()
        }
        txtRollsRestantes.setText(rolls.toString())
        for((index,dado) in dados.withIndex()) {
            dado.lanzarDado()
            actualizarDadoInterfaz(imagenes[index], dado.valor)
        }
        calcularCategorias()
    }

    private fun bloquearRoll() {
        botonRoll.isEnabled = false
    }

    private fun calcularCategorias() {
        val valores = IntArray(dados.size) {0}
        for((i ,dado) in dados.withIndex()) {
            valores[i] = dado.valor
        }
        juegoBalut.setearDados(valores)
        val resultados = juegoBalut.calcularTodo()
        for((i,texto) in textosPuntos.withIndex()) {
            texto.setText(resultados[i].toString())
        }
    }

    /**
     * Actualizar la interfaz con los dados.
     */
    fun actualizarDadoInterfaz(imagen : ImageView, numero : Int) {
        imagen.setImageDrawable(ContextCompat.getDrawable(applicationContext, nombreDados[numero - 1]))
    }
    /**
     * Crear menú flotante.
     */
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (menu != null) {
            menu.setHeaderTitle("menu")
        }
        menuInflater.inflate(R.menu.menuflotante, menu)

    }

    /**
     * Crear menú de opciones.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menuaction, menu)
        return true
    }

    /**
     * Función que maneja las acciones de presionar un elemento del menú.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reglas -> {
                Toast.makeText(this, "Reglas", Toast.LENGTH_SHORT).show()
                var dialogo = DialogoReglas()
                dialogo.show(supportFragmentManager, "Reglas")
                true
            }
            R.id.puntHumano -> {
                val intentPuntos = Intent(this, PuntuacionesJugador::class.java)
                intentPuntos.putExtra("lista", registrosPuntos)
                intentPuntos.putExtra("fondo", colorFondo)
                startActivity(intentPuntos)
                //Toast.makeText(this, "Puntos del humano", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.reiniciar -> {
                reiniciarDados()
                Toast.makeText(this, R.string.msjReinicio, Toast.LENGTH_SHORT).show()
                true
            }
            R.id.about -> {
                // Crear dialogo de información sobre la aplicación.
                val alertDialog: AlertDialog = this.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        val mensaje = StringBuilder(resources.getString(R.string.highScore))
                        mensaje.append(" ")
                        mensaje.append(leerArchivoPuntuaciones())
                        mensaje.append(resources.getString(R.string.MensajeAbout))
                        setMessage(mensaje)
                        setNegativeButton(R.string.ok, null)
                    }
                    // Crear el dialogo.
                    builder.create()
                }
                alertDialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    /**
     * Función que maneja las acciones de presionar un elemento del menú.
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Rojo -> {
                layout.setBackgroundResource(R.color.rojo)
                colorFondo = 1
                true
            }
            R.id.Azul -> {
                colorFondo = 0
                layout.setBackgroundResource(R.color.azulClaro)
                true
            }
            R.id.Verde -> {
                colorFondo = 2
                layout.setBackgroundResource(R.color.verde)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    /**
     * Reinicia los dados a su estado original.
     */
    fun reiniciarDados() {
        for ((index,imagen) in imagenes.withIndex()) {
            imagen.setBackground(null)
            imagen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dadocara1))
            dados[index].estaSeleccionado = false
        }
    }
    fun registrarPuntuacion(puntuacion : Int) {
        if(puntuacion < obtenerPuntuacionMasAlta()) {
            return
        }
        val file = File(applicationContext.filesDir, "puntuacionAlta")
        file.createNewFile() // Se crea un nuevo archivo si aun no existia.
        val registro: StringBuilder = StringBuilder()
        registro.append(puntuacion)
        registro.append("\n")
        val date: LocalDateTime = LocalDateTime.now()
        registro.append(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
        registro.append("\n")
        applicationContext.openFileOutput("puntuacionAlta", Context.MODE_PRIVATE).use { fileStream ->
            fileStream.write(registro.toString().toByteArray())
        }
    }
    fun obtenerPuntuacionMasAlta() : Int{
        val file = File(applicationContext.filesDir, "puntuacionAlta")
        if(file.exists()) {
            return applicationContext.openFileInput("puntuacionAlta").bufferedReader().readLine().toInt()
        }
        return -2
    }

    fun leerArchivoPuntuaciones() : String {
        val file = File(applicationContext.filesDir, "puntuacionAlta")
        if(!file.exists()) {
            registrarPuntuacion(-2)
        }
        val registro: StringBuilder = StringBuilder()
        applicationContext.openFileInput("puntuacionAlta").bufferedReader().forEachLine { linea ->
            registro.append(linea)
            registro.append("\n")
        }
        return registro.toString()
    }
}