package lima.sofia.ana.catalogolivros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TelaCatalogo(onAdicionarAoCarrinho: (Livro) -> Unit) {
    val listaLivros = remember {
        listOf(
            Livro(1, "O Senhor dos Anéis", "J.R.R. Tolkien", "Fantasia", 59.90, 12),
            Livro(2, "Duna", "Frank Herbert", "Ficção Científica", 49.90, 5),
            Livro(3, "Código Limpo", "Robert C. Martin", "Tecnologia", 89.00, 8),
            Livro(4, "Hábitos Atômicos", "James Clear", "Desenvolvimento Pessoal", 42.50, 20),
            Livro(5, "O Hobbit", "J.R.R. Tolkien", "Fantasia", 39.90, 0)
        )
    }

    var buscaTema by remember { mutableStateOf("") }

    val livrosFiltrados = listaLivros.filter {
        it.tema.contains(buscaTema, ignoreCase = true) || it.titulo.contains(buscaTema, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = buscaTema,
            onValueChange = { buscaTema = it },
            label = { Text("Buscar por título ou assunto") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // O segredo está aqui: adicionamos o .weight(1f) para a lista preencher
        // o espaço restante e ativar a rolagem de forma independente!
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(livrosFiltrados) { livro ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = livro.titulo, style = MaterialTheme.typography.titleLarge)
                        Text(text = "Autor: ${livro.autor}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Assunto: ${livro.tema}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Valor: R$ ${String.format("%.2f", livro.preco)}", style = MaterialTheme.typography.bodyLarge)

                        val disponivel = livro.quantidadeDisponivel > 0
                        Text(
                            text = if (disponivel) "Disponível: ${livro.quantidadeDisponivel} un." else "Esgotado",
                            color = if (disponivel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { onAdicionarAoCarrinho(livro) },
                            enabled = disponivel,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Adicionar ao Carrinho")
                        }
                    }
                }
            }
        }
    }
}