package lima.sofia.ana.catalogolivros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppMainScreen()
            }
        }
    }
}

enum class Telas { CATALOGO, CARRINHO, ATENDIMENTO, SOBRE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMainScreen() {
    var telaAtual by remember { mutableStateOf(Telas.CATALOGO) }
    val carrinho = remember { mutableStateListOf<Livro>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Livraria Digital") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = telaAtual == Telas.CATALOGO,
                    onClick = { telaAtual = Telas.CATALOGO },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Catálogo") },
                    label = { Text("Catálogo") }
                )
                NavigationBarItem(
                    selected = telaAtual == Telas.CARRINHO,
                    onClick = { telaAtual = Telas.CARRINHO },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrinho") },
                    label = { Text("Carrinho (${carrinho.size})") }
                )
                NavigationBarItem(
                    selected = telaAtual == Telas.ATENDIMENTO,
                    onClick = { telaAtual = Telas.ATENDIMENTO },
                    icon = { Icon(Icons.Default.Phone, contentDescription = "Contato") },
                    label = { Text("Contato") }
                )
                NavigationBarItem(
                    selected = telaAtual == Telas.SOBRE,
                    onClick = { telaAtual = Telas.SOBRE },
                    icon = { Icon(Icons.Default.Info, contentDescription = "Sobre") },
                    label = { Text("Sobre") }
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (telaAtual) {
                Telas.CATALOGO -> TelaCatalogo(onAdicionarAoCarrinho = { livro -> carrinho.add(livro) })
                Telas.CARRINHO -> TelaCarrinho(itensCarrinho = carrinho, onRemover = { livro -> carrinho.remove(livro) })
                Telas.ATENDIMENTO -> TelaAtendimento()
                Telas.SOBRE -> TelaSobre(
                    onFechar = { telaAtual = Telas.CATALOGO },
                    onIrParaContatos = { telaAtual = Telas.ATENDIMENTO }
                )
            }
        }
    }
}