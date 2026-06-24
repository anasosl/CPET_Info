package lima.sofia.ana.catalogolivros

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun TelaAtendimento() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Canais de Atendimento", modifier = Modifier.padding(bottom = 32.dp))

        Button(
            onClick = {
                val numeroWhats = "5581999999999"
                val intentUri = Uri.parse("https://api.whatsapp.com/send?phone=$numeroWhats&text=Olá! Preciso de suporte no app de livros.")
                context.startActivity(Intent(Intent.ACTION_VIEW, intentUri))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Chamar no WhatsApp", color = Color.White)
        }

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("suporte.livros@email.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "Suporte - Catálogo de Livros")
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar E-mail")
        }
    }
}