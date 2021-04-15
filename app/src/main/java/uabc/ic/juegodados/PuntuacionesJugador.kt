package uabc.ic.juegodados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PuntuacionesJugador : AppCompatActivity() {
    private lateinit var layout : ConstraintLayout
    lateinit var adapter : RecyclerView.Adapter<AdaptadorPuntuaciones.ViewHolder>
    lateinit var puntuaciones : ArrayList<ElementoPuntuacion>
    lateinit var recycler : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntuaciones_jugador)
        layout = findViewById(R.id.layoutRecycler)
        puntuaciones = intent.getSerializableExtra("lista") as ArrayList<ElementoPuntuacion>
        val fondo : Int= intent.getIntExtra("fondo",0)
        recycler = findViewById(R.id.reciclador)
        adapter = AdaptadorPuntuaciones(puntuaciones)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter=adapter
        when(fondo) {
            0 -> {
                layout.setBackgroundResource(R.color.azulClaro)
                recycler.setBackgroundResource(R.color.azulClaro)
            }
            1 -> {
                layout.setBackgroundResource(R.color.rojo)
                recycler.setBackgroundResource(R.color.rojo)
            }
            2 -> {
                layout.setBackgroundResource(R.color.verde)
                recycler.setBackgroundResource(R.color.verde)
            }
        }
    }
}