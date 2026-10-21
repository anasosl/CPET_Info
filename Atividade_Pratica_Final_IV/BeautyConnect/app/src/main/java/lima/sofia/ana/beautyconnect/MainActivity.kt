package lima.sofia.ana.beautyconnect

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

// Estruturas de dados para o catálogo
data class ItemProduto(val marca: String, val nome: String, val preco: Double)
data class CategoriaCatalogo(val nome: String, val produtos: List<ItemProduto>)

class MainActivity : AppCompatActivity() {

    private lateinit var mainRootLayout: RelativeLayout
    private lateinit var secEmpresa: LinearLayout
    private lateinit var secProdutos: LinearLayout
    private lateinit var secGestao: LinearLayout
    private lateinit var secContato: LinearLayout
    private lateinit var secFaq: LinearLayout

    private lateinit var containerProdutos: LinearLayout

    private lateinit var editCustoComissao: EditText
    private lateinit var btnCalcular: Button
    private lateinit var txtResultadoComissao: TextView

    private lateinit var editNomeContato: EditText
    private lateinit var editEmailContato: EditText
    private lateinit var editMensagemContato: EditText
    private lateinit var btnEnviarContato: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        mainRootLayout = findViewById(R.id.mainRootLayout)

        ViewCompat.setOnApplyWindowInsetsListener(mainRootLayout) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                statusBarInsets.top,
                view.paddingRight,
                0
            )
            insets
        }

        secEmpresa = findViewById(R.id.secEmpresa)
        secProdutos = findViewById(R.id.secProdutos)
        secGestao = findViewById(R.id.secGestao)
        secContato = findViewById(R.id.secContato)
        secFaq = findViewById(R.id.secFaq)

        containerProdutos = findViewById(R.id.containerProdutos)

        editCustoComissao = findViewById(R.id.editCustoComissao)
        btnCalcular = findViewById(R.id.btnCalcular)
        txtResultadoComissao = findViewById(R.id.txtResultadoComissao)

        editNomeContato = findViewById(R.id.editNomeContato)
        editEmailContato = findViewById(R.id.editEmailContato)
        editMensagemContato = findViewById(R.id.editMensagemContato)
        btnEnviarContato = findViewById(R.id.btnEnviarContato)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            ocultarTodasSecoes()
            when (item.itemId) {
                R.id.nav_empresa -> secEmpresa.visibility = View.VISIBLE
                R.id.nav_produtos -> {
                    secProdutos.visibility = View.VISIBLE
                    carregarVitrineEmCards()
                }
                R.id.nav_gestao -> secGestao.visibility = View.VISIBLE
                R.id.nav_contato -> secContato.visibility = View.VISIBLE
                R.id.nav_faq -> secFaq.visibility = View.VISIBLE
            }
            true
        }

        btnCalcular.setOnClickListener { calcularComissao() }
        btnEnviarContato.setOnClickListener { enviarContato() }
    }

    private fun ocultarTodasSecoes() {
        secEmpresa.visibility = View.GONE
        secProdutos.visibility = View.GONE
        secGestao.visibility = View.GONE
        secContato.visibility = View.GONE
        secFaq.visibility = View.GONE
    }

    // Renderiza dinamicamente as categorias e os cards de produtos
    private fun carregarVitrineEmCards() {
        containerProdutos.removeAllViews()

        val listaCategorias = listOf(
            CategoriaCatalogo(
                nome = "Perfumaria",
                produtos = listOf(
                    ItemProduto("Natura", "Kaiak", 130.00),
                    ItemProduto("Boticário", "Malbec", 182.00),
                    ItemProduto("Natura", "Essencial", 195.00),
                    ItemProduto("Avon", "Luiza Brunet", 104.00)
                )
            ),
            CategoriaCatalogo(
                nome = "Cuidados com o Corpo",
                produtos = listOf(
                    ItemProduto("Natura", "Tododia", 52.00),
                    ItemProduto("Boticário", "Nativa SPA", 65.00),
                    ItemProduto("Natura", "Sève", 78.00),
                    ItemProduto("Avon", "Encanto", 39.00)
                )
            ),
            CategoriaCatalogo(
                nome = "Maquiagem",
                produtos = listOf(
                    ItemProduto("Natura", "Batom Una", 45.50),
                    ItemProduto("Avon", "Base Power Stay", 58.50),
                    ItemProduto("Boticário", "Make B", 71.50),
                    ItemProduto("Avon", "Color Trend", 32.50)
                )
            )
        )

        for (cat in listaCategorias) {
            // 1. Título da Categoria com barra rosa à esquerda
            val headerCat = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, 24, 0, 16)
            }

            val barraRosa = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(12, 50).apply {
                    setMargins(0, 0, 16, 0)
                }
                setBackgroundColor(Color.parseColor("#FF4081"))
            }

            val txtTituloCat = TextView(this).apply {
                text = cat.nome
                textSize = 20f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#333333"))
            }

            headerCat.addView(barraRosa)
            headerCat.addView(txtTituloCat)
            containerProdutos.addView(headerCat)

            // 2. Grid de produtos (2 colunas)
            val grid = GridLayout(this).apply {
                columnCount = 2
                alignmentMode = GridLayout.ALIGN_MARGINS
                isColumnOrderPreserved = false
            }

            for (prod in cat.produtos) {
                val card = MaterialCardView(this).apply {
                    radius = 16f
                    cardElevation = 2f
                    setCardBackgroundColor(Color.parseColor("#FAFAFA"))
                    strokeWidth = 1
                    strokeColor = Color.parseColor("#EEEEEE")

                    val params = GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        setMargins(8, 8, 8, 8)
                    }
                    layoutParams = params
                }

                val layoutInternoCard = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER
                    setPadding(16, 24, 16, 24)
                }

                val txtMarca = TextView(this).apply {
                    text = prod.marca
                    textSize = 12f
                    setTextColor(Color.parseColor("#777777"))
                    gravity = Gravity.CENTER
                }

                val txtNome = TextView(this).apply {
                    text = prod.nome
                    textSize = 16f
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(Color.parseColor("#222222"))
                    gravity = Gravity.CENTER
                    setPadding(0, 8, 0, 12)
                }

                val txtPreco = TextView(this).apply {
                    text = "R$ ${String.format("%.2f", prod.preco)}"
                    textSize = 15f
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(Color.parseColor("#333333"))
                    gravity = Gravity.CENTER
                }

                layoutInternoCard.addView(txtMarca)
                layoutInternoCard.addView(txtNome)
                layoutInternoCard.addView(txtPreco)

                card.addView(layoutInternoCard)
                grid.addView(card)
            }

            containerProdutos.addView(grid)
        }
    }

    private fun calcularComissao() {
        val custoStr = editCustoComissao.text.toString().trim()
        if (custoStr.isEmpty()) {
            Toast.makeText(this, "Informe o valor de custo.", Toast.LENGTH_SHORT).show()
            return
        }

        val custo = custoStr.toDouble()
        val revenda = custo * 1.30
        val lucro = revenda - custo
        val parte = lucro / 2.0

        txtResultadoComissao.text = "💵 Venda Estimada: R$ ${String.format("%.2f", revenda)}\n\n" +
                "👑 Sua Parte (Proprietário): R$ ${String.format("%.2f", parte)}\n" +
                "🤝 Parte do Vendedor: R$ ${String.format("%.2f", parte)}"
    }

    private fun enviarContato() {
        val nome = editNomeContato.text.toString().trim()
        val email = editEmailContato.text.toString().trim()
        val mensagem = editMensagemContato.text.toString().trim()

        if (nome.isEmpty() || email.isEmpty() || mensagem.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Mensagem enviada com sucesso!", Toast.LENGTH_LONG).show()
        editNomeContato.setText("")
        editEmailContato.setText("")
        editMensagemContato.setText("")
    }
}