package lima.sofia.ana.aniversario65anos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConvidadosActivity : AppCompatActivity() {
    private var listaDetalhada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convidados)

        val etNome = findViewById<EditText>(R.id.etNomeConvidado)
        val etTelefone = findViewById<EditText>(R.id.etTelefone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etAcompanhantes = findViewById<EditText>(R.id.etAcompanhantes)
        val btnAdd = findViewById<Button>(R.id.btnAdicionar)
        val tvLista = findViewById<TextView>(R.id.tvListaConvidados)

        btnAdd.setOnClickListener {
            val nome = etNome.text.toString()
            val acompanhantes = etAcompanhantes.text.toString().toIntOrNull() ?: 0

            if (nome.isNotEmpty()) {
                listaDetalhada += "• $nome ($acompanhantes acompanhantes) - Tel: ${etTelefone.text} - Email: ${etEmail.text}\n"
                tvLista.text = listaDetalhada
                GerenciadorFesta.totalPessoas += 1 + acompanhantes
                findViewById<TextView>(R.id.tvTotalPessoas).text = "Total de Pessoas: ${GerenciadorFesta.totalPessoas}"

                etNome.text.clear()
                etTelefone.text.clear()
                etEmail.text.clear()
                etAcompanhantes.text.clear()
            }
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}