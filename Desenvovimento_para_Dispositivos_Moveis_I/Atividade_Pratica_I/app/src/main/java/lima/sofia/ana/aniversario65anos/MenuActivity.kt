package lima.sofia.ana.aniversario65anos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.btnConvidados).setOnClickListener {
            startActivity(Intent(this, ConvidadosActivity::class.java))
        }
        findViewById<Button>(R.id.btnGastos).setOnClickListener {
            startActivity(Intent(this, GastosActivity::class.java))
        }
        findViewById<Button>(R.id.btnCalculo).setOnClickListener {
            startActivity(Intent(this, CalculoActivity::class.java))
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}