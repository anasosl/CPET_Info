package lima.sofia.ana.estoque

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sofia.lima.estoque.model.Brand
import com.sofia.lima.estoque.viewmodel.InventoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val viewModel = remember { InventoryViewModel() }
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Estoque", "Nova Compra", "Vendedores", "Painel Financeiro")

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("StockManager Mobile - Natura/Avon/O Boticário", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE91E63))
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                        )
                    }
                }

                when (selectedTab) {
                    0 -> StockManagementScreen(viewModel)
                    1 -> PurchaseEntryScreen(viewModel)
                    2 -> BuyersScreen(viewModel)
                    3 -> AdvancedFinanceScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun StockManagementScreen(viewModel: InventoryViewModel) {
    var selectedBrandFilter by remember { mutableStateOf<Brand?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { selectedBrandFilter = null }) { Text("Todos") }
            Button(onClick = { selectedBrandFilter = Brand.NATURA }) { Text("Natura") }
            Button(onClick = { selectedBrandFilter = Brand.AVON }) { Text("Avon") }
            Button(onClick = { selectedBrandFilter = Brand.OBOTICARIO }) { Text("Boticário") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        val displayList = if (selectedBrandFilter == null) viewModel.products else viewModel.products.filter { it.brand == selectedBrandFilter }

        LazyColumn {
            items(displayList) { product ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Quantidade: ${product.stockQuantity} unidades", fontSize = 14.sp)
                        Text("Preço Custo: R$ ${formatMoney(product.costPrice)} | Venda (30%): R$ ${formatMoney(product.sellingPrice)}", color = Color(0xFF4CAF50))

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Venda direta rápida
                            Button(onClick = { viewModel.registerSellEntry(product, null, 1, "26/06/2026") }, enabled = product.stockQuantity > 0) {
                                Text("Venda Direta")
                            }
                            // Atribuir venda ao primeiro consultor disponível (se houver)
                            if (viewModel.buyers.isNotEmpty()) {
                                Button(
                                    onClick = { viewModel.registerSellEntry(product, viewModel.buyers.first(), 1, "26/06/2026") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                                    enabled = product.stockQuantity > 0
                                ) {
                                    Text("Via Consultor")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PurchaseEntryScreen(viewModel: InventoryViewModel) {
    var name by remember { mutableStateOf("") }
    var costPrice by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var selectedBrand by remember { mutableStateOf(Brand.NATURA) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Dar Entrada em Lote / Compra", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome do Produto") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = costPrice, onValueChange = { costPrice = it }, label = { Text("Preço de Custo (Distribuidor)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantidade Adquirida") }, modifier = Modifier.fillMaxWidth())

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = selectedBrand == Brand.NATURA, onClick = { selectedBrand = Brand.NATURA })
            Text("Natura")
            RadioButton(selected = selectedBrand == Brand.AVON, onClick = { selectedBrand = Brand.AVON })
            Text("Avon")
            RadioButton(selected = selectedBrand == Brand.OBOTICARIO, onClick = { selectedBrand = Brand.OBOTICARIO })
            Text("O Boticário")
        }

        Button(onClick = {
            if (name.isNotEmpty() && costPrice.toDoubleOrNull() != null && quantity.toIntOrNull() != null) {
                viewModel.registerPurchaseEntry(name, selectedBrand, costPrice.toDouble(), quantity.toInt(), "26/06/2026")
                name = ""; costPrice = ""; quantity = ""
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Adicionar ao Estoque")
        }
    }
}

@Composable
fun BuyersScreen(viewModel: InventoryViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Cadastro Administrativo de Consultores", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome do Vendedor") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("E-mail de Contato") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Celular / WhatsApp") }, modifier = Modifier.fillMaxWidth())

        Button(onClick = {
            if (name.isNotEmpty()) {
                viewModel.registerBuyer(name, email, phone)
                name = ""; email = ""; phone = ""
            }
        }, modifier = Modifier.fillMaxWidth()) { Text("Salvar Consultor") }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Vendedores Associados Ativos:", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

        LazyColumn {
            items(viewModel.buyers) { buyer ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        // SOLUÇÃO: Indicador visual em formato de Avatar usando o Compose nativo (Sem depender de Icons)
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFF673AB7), shape = androidx.compose.foundation.shape.CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = buyer.name.take(1).uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(buyer.name, fontWeight = FontWeight.Bold)
                            Text("WhatsApp: ${buyer.phone} | ${buyer.email}", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdvancedFinanceScreen(viewModel: InventoryViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Painel Gerencial de Resultados", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        FinanceMetricCard("Faturamento Bruto (Vendas)", viewModel.totalSalesValue.value, Color(0xFF2196F3))
        FinanceMetricCard("Capital Total Investido em Estoque", viewModel.totalInvestedValue.value, Color(0xFF9C27B0))
        FinanceMetricCard("Lucro Líquido Real das Vendas", viewModel.netProfit.value, Color(0xFF4CAF50))

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Divisão Interna Baseada em Vendas:", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

        FinanceMetricCard("Cota da Proprietária (Seu Lucro)", viewModel.ownerProfitShare.value, Color(0xFF009688))
        FinanceMetricCard("Cota Reservada para Comissões (50/50)", viewModel.sellerCommissionShare.value, Color(0xFFFF9800))
    }
}

@Composable
fun FinanceMetricCard(title: String, value: Double, color: Color) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f))) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, fontSize = 13.sp, color = Color.DarkGray)
            Text("R$ ${formatMoney(value)}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

fun formatMoney(value: Double): String {
    val centavosTotal = (value * 100).toLong()
    val inteiros = centavosTotal / 100
    val centavos = centavosTotal % 100
    return "$inteiros,${if (centavos < 10) "0$centavos" else centavos}"
}