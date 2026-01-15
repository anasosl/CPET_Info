package lima.sofia.ana.aniversario65anos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GastosActivity : AppCompatActivity() {
    private var totalAcumulado = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos)

        val etDescricao = findViewById<EditText>(R.id.etDescricaoGasto)
        val etValor = findViewById<EditText>(R.id.etValorGasto)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarGasto)
        val tvTotal = findViewById<TextView>(R.id.tvTotalGastos)
        val tvGastos = findViewById<TextView>(R.id.tvGastos)

        btnRegistrar.setOnClickListener {
            val valorDigitado = etValor.text.toString().toDoubleOrNull() ?: 0.0
            totalAcumulado += valorDigitado
            tvTotal.text = "Total: R$ ${String.format("%.2f", totalAcumulado)}"

            val descricao = etDescricao.text.toString()
            val valorFormatado = String.format("%.2f", valorDigitado)
            tvGastos.text = "• $descricao - R$ $valorFormatado\n" + tvGastos.text

            etDescricao.text.clear()
            etValor.text.clear()
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}