package lima.sofia.ana.estoque

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform