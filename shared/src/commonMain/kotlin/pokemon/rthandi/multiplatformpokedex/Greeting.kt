package pokemon.rthandi.multiplatformpokedex

import Pokemon
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Greeting {
    private val platform: Platform = getPlatform()

    private val localhostUrl = if (platform.name.contains("Android")) "10.0.2.2" else "localhost"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    @Throws(Exception::class)
    suspend fun greet(): List<String> = buildList {
        val pokemon: List<Pokemon> =
            httpClient.request("http://$localhostUrl:8080/pokemon").body()
        pokemon.forEach { add(it.name) }
    }
}