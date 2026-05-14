package lima.sofia.ana.aniversario65anos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GastosActivity : AppCompatActivity() {
    private var totalGasto = 0.0
    private var meuOrcamento = 0.0
    private var listaGastos = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos)

        val etOrcamento = findViewById<EditText>(R.id.etMeuOrcamento)
        val etDescricao = findViewById<EditText>(R.id.etDescricaoGasto)
        val etValor = findViewById<EditText>(R.id.etValorGasto)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarGasto)

        val tvTotalGasto = findViewById<TextView>(R.id.tvTotalGastos)
        val tvSaldo = findViewById<TextView>(R.id.tvSaldoRestante)
        val tvListaGastos = findViewById<TextView>(R.id.tvGastos)

        if (GerenciadorFesta.orcamento > 0) {
            etOrcamento.setText(GerenciadorFesta.orcamento.toString())
        }

        btnRegistrar.setOnClickListener {
            meuOrcamento = etOrcamento.text.toString().toDoubleOrNull() ?: 0.0
            val valorDigitado = etValor.text.toString().toDoubleOrNull() ?: 0.0

            totalGasto += valorDigitado
            val saldo = meuOrcamento - totalGasto

            tvTotalGasto.text = "Valor Gasto: R$ ${String.format("%.2f", totalGasto)}"
            tvSaldo.text = "Saldo Restante: R$ ${String.format("%.2f", saldo)}"

            val descricao = etDescricao.text.toString()
            listaGastos +=
                "• $descricao - R$ ${String.format("%.2f", valorDigitado)}\n"

            tvListaGastos.text = listaGastos

            GerenciadorFesta.orcamento = meuOrcamento
            etDescricao.text.clear()
            etValor.text.clear()
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}