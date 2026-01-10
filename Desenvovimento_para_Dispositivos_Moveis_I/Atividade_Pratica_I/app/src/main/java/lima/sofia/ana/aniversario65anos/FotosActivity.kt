package lima.sofia.ana.aniversario65anos

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FotosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Fotos da Festa"

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}