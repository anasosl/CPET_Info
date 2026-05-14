package lima.sofia.ana.aniversario65anos

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil


class CalculoActivity : AppCompatActivity() {

    var orcamentoPrevisto = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculo)

        val etConvidados = findViewById<android.widget.EditText>(R.id.etConvidados)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)

        val tvOrcamento = findViewById<android.widget.TextView>(R.id.tvOrcamento)
        tvOrcamento.setText("Orçamento: R$ ${String.format("%.2f", GerenciadorFesta.orcamento)}")
        val tvOrcamentoPrevisto = findViewById<android.widget.TextView>(R.id.tvOrcamentoPrevisto)

        if (GerenciadorFesta.totalPessoas > 0) {
            etConvidados.setText(GerenciadorFesta.totalPessoas.toString())

            btnCalcular.performClick()
        }

        val tvResultado = findViewById<android.widget.TextView>(R.id.tvResultado)

        btnCalcular.setOnClickListener {
            val total = etConvidados.text.toString().toIntOrNull() ?: 0

            val salgados = total * 12
            val salgadosValor = salgados * 0.5

            val doces = total * 5
            val docesValor = doces * 1.5

            val boloGrama = total * 100
            val boloValor = boloGrama * 0.15

            val refrigerante = total * 400
            val refrigeranteValor = refrigerante * 0.004

            val suco = total * 300
            val sucoValor = suco * 0.002

            val agua = total * 200
            val aguaValor = agua * 0.001

            val pratinhos = total * 1
            val pratinhosValor = ceil(100%(pratinhos * 0.25))*10

            val copos = total * 3
            val coposValor = ceil(10%(copos * 0.05))*4

            val talheres = total * 2
            val talheresValor = ceil(50%(talheres * 0.01))*4

            orcamentoPrevisto = salgadosValor + docesValor + boloValor + refrigeranteValor + sucoValor + aguaValor + pratinhosValor + coposValor + talheresValor
            tvOrcamentoPrevisto.text = "Orçamento Previsto: R$ ${String.format("%.2f", orcamentoPrevisto)}"

            if (orcamentoPrevisto > GerenciadorFesta.orcamento) {
                tvOrcamento.setTextColor(android.graphics.Color.RED)
            } else {
                tvOrcamento.setTextColor(android.graphics.Color.GREEN)
            }

            tvResultado.text = """
                Resultado para $total pessoas:
                - $doces Docinhos - R$ ${String.format("%.2f", docesValor)}
                - $salgados Salgadinhos - R$ ${String.format("%.2f", salgadosValor)}
                - ${refrigerante / 1000.0}L de Refrtigerante - R$ ${String.format("%.2f", refrigeranteValor)}
                - ${suco / 1000.0}L de Suco - R$ ${String.format("%.2f", sucoValor)}
                - ${agua / 1000.0}L de Água - R$ ${String.format("%.2f", aguaValor)}
                - ${pratinhos.toInt()} Pratinhos - R$ ${String.format("%.2f", pratinhosValor)}
                - ${copos.toInt()} Copos - R$ ${String.format("%.2f", coposValor)}
                - ${talheres.toInt()} Talheres - R$ ${String.format("%.2f", talheresValor)}
                - ${boloGrama / 1000.0}kg de Bolo - R$ ${String.format("%.2f", boloValor)}
            """.trimIndent()
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}