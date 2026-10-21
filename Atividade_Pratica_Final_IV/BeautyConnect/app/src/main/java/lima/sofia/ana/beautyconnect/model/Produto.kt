package lima.sofia.ana.beautyconnect.model

data class Produto(
    val id: Int = 0,
    val nome: String,
    val marca: String, // Natura, Avon ou O Boticário
    val quantidade: Int,
    val preco: Double
)