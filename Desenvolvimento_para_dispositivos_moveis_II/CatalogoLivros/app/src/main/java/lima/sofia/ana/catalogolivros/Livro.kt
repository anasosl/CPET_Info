package lima.sofia.ana.catalogolivros

data class Livro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val tema: String,
    val preco: Double,
    val quantidadeDisponivel: Int
)