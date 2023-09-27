package pokemon.rthandi.multiplatformpokedex

import Pokemon
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.random.Random

class Greeting {
    private val platform: Platform = getPlatform()

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
            httpClient.get("http://0.0.0.0:8080/pokemon").body()
        add(if (Random.nextBoolean()) "Hi!" else "Hello!")
        add("Guess what it is! > ${platform.name.reversed()}!")
        add("\nThere are only ${daysUntilNewYear()} days left until New Year! 🎆")
        add("first pokemon ${pokemon[0]}")
    }
}