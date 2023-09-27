package pokemon.rthandi.multiplatformpokedex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform