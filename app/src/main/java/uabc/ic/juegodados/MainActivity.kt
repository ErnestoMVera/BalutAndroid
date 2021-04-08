package uabc.ic.juegodados

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat


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
            R.id.dado5,
            R.id.dado6
    )
    // Array de objetos de tipo dado.
    private var dados : Array<Dado> = Array(6) {index -> Dado()}
    // Arreglo de ImageView.
    private lateinit var imagenes : Array<ImageView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Layout de la aplicación.
        layout = findViewById(R.id.miLayout)
        // Arreglo de imagenes de imageView.
        imagenes = Array(6) {index -> findViewById(idImagenes[index])}
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
        // Poner los dados en el estado inicial de todos uno.
        reiniciarDados()
        // Setear el background en azul.
        layout.setBackgroundResource(R.color.azulClaro)
        // Registrar el layour para el menú flotante.
        registerForContextMenu(layout)
    }

    /**
     * Función de lanzar los dadso cuando se presiona el botón.
     */
    fun tirarDados(v : View) {
        for((index,dado) in dados.withIndex()) {
            dado.lanzarDado()
            actualizarDadoInterfaz(imagenes[index], dado.valor)
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
                true
            }
            R.id.puntHumano -> {
                Toast.makeText(this, "Puntos del humano", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.puntCPU -> {
                Toast.makeText(this, "Puntos del CPU", Toast.LENGTH_SHORT).show()
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
                        setMessage(R.string.MensajeAbout)
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
                true
            }
            R.id.Azul -> {
                layout.setBackgroundResource(R.color.azulClaro)
                true
            }
            R.id.Verde -> {
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
}