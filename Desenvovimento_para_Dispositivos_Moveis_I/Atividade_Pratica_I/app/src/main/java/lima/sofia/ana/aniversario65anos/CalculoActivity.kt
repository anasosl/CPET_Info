package lima.sofia.ana.aniversario65anos

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CalculoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculo)

        val etConvidados = findViewById<android.widget.EditText>(R.id.etConvidados)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvResultado = findViewById<android.widget.TextView>(R.id.tvResultado)

        btnCalcular.setOnClickListener {
            val total = etConvidados.text.toString().toIntOrNull() ?: 0
            val salgados = total * 12
            val doces = total * 5
            tvResultado.text = "Para $total pessoas:\n- $salgados salgados\n- $doces doces"
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}