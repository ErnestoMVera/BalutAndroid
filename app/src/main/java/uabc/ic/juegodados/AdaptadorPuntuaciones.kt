package uabc.ic.juegodados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AdaptadorPuntuaciones(var lista : ArrayList<ElementoPuntuacion>) : RecyclerView.Adapter<AdaptadorPuntuaciones.ViewHolder>(), View.OnClickListener {
    private var nombreDados : IntArray = intArrayOf(
        R.drawable.dadocara1,
        R.drawable.dadocara2,
        R.drawable.dadocara3,
        R.drawable.dadocara4,
        R.drawable.dadocara5,
        R.drawable.dadocara6
    )
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val txtCategoria : TextView
        val txtPuntos : TextView
        val idImagenes = intArrayOf(R.id.dadoMuestra1, R.id.dadoMuestra2, R.id.dadoMuestra3, R.id.dadoMuestra4, R.id.dadoMuestra5)
        var imagenesDados : Array<ImageView>
        init {
            txtCategoria = view.findViewById(R.id.txtCategoria)
            txtPuntos = view.findViewById(R.id.txtPuntos)
            imagenesDados = Array<ImageView>(idImagenes.size) {index -> view.findViewById(idImagenes[index])}
        }
        override fun onClick(v: View?) {
            Toast.makeText(v?.context, "NO ME SIRVE", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var categoria = ""
        when(lista.get(position).categoria) {
            JuegoBalut.FOURS -> categoria = "Fours"
            JuegoBalut.FIVES -> categoria = "Fives"
            JuegoBalut.SIXES -> categoria = "Sixes"
            JuegoBalut.STRAIGHT -> categoria = "Straight"
            JuegoBalut.FULLHOUSE -> categoria = "Full House"
            JuegoBalut.CHOICE -> categoria = "Choice"
            JuegoBalut.BALUT -> categoria = "Balut"
        }
        holder.txtCategoria.setText(categoria)
        holder.txtPuntos.setText(lista.get(position).puntuacion.toString())
        for((i,imagen) in holder.imagenesDados.withIndex()) {
            val indice = lista.get(position).dadosRegistrados[i] - 1
            imagen.setImageResource(nombreDados[indice])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_puntuacion, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onClick(v: View?) {
        Toast.makeText(v?.context, "ME SIRVE", Toast.LENGTH_SHORT).show()
    }
}