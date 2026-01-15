package lima.sofia.ana.aniversario65anos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webViewMapa)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    return false
                }


                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view?.context?.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return true
            }
        }

        val enderecoFesta = "https://www.google.com/maps/search/?api=1&query=Avenida+Paulista+1000+Sao+Paulo"
        webView.loadUrl(enderecoFesta)

        val btnFotos = findViewById<Button>(R.id.btnVerFotos)
        btnFotos.setOnClickListener {
            val intent = Intent(this, FotosActivity::class.java)
            startActivity(intent)
        }

        val btnEntrar = findViewById<Button>(R.id.btnEntrarMenu)
        btnEntrar.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}