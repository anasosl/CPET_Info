package lima.sofia.ana.aniversario65anos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConvidadosActivity : AppCompatActivity() {
    private var listaNomes = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convidados)

        val etNome = findViewById<EditText>(R.id.etNomeConvidado)
        val btnAdd = findViewById<Button>(R.id.btnAdicionar)
        val tvLista = findViewById<TextView>(R.id.tvListaConvidados)

        btnAdd.setOnClickListener {
            val nome = etNome.text.toString()
            if (nome.isNotEmpty()) {
                listaNomes += "• $nome\n"
                tvLista.text = listaNomes
                etNome.text.clear()
            }
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}