package lima.sofia.ana.catalogolivros

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TelaSobre(
    onFechar: () -> Unit,
    onIrParaContatos: () -> Unit
) {
    val nomeDoAluno = "Ana Sofia de Oliveira Silva Lima"
    val cpfDoAluno = "086.294.744-83"
    val emailDesenvolvedor = "sofiaosl44@gmail.com"

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Sobre o BookCatalog", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            Text(text = "Versão: 1.0.0", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 24.dp))

            Text(text = "Sistema de Administração", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 16.dp))

            Text(text = "Desenvolvedor(a): $nomeDoAluno", style = MaterialTheme.typography.bodyLarge)
            Text(text = "CPF: $cpfDoAluno", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Contato: $emailDesenvolvedor", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Suporte/Vendas", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))
<<<<<<< HEAD
            
=======

            // Novo botão adicionado logo abaixo de Suporte/Vendas
>>>>>>> bfb4cdddbcd1784ff955a63958d0ae49377dffe6
            Button(
                onClick = onIrParaContatos,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir para Contatos / Suporte")
            }
        }

        Button(
            onClick = onFechar,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Fechar")
        }
    }
}