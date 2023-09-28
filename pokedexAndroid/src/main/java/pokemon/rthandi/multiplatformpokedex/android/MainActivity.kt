package pokemon.rthandi.multiplatformpokedex.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import pokemon.rthandi.multiplatformpokedex.Greeting

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var phrases by remember { mutableStateOf(listOf("Loading")) }
                    LaunchedEffect(true) {
                        phrases = try {
                            Greeting().fetchPokemon()
                        } catch (e: Exception) {
                            listOf(e.localizedMessage ?: "error")
                        }
                    }
                    GreetingView(phrases)
                    CreatePokemonView()
                }
            }
        }
    }
}


@Composable
fun GreetingView(phrases: List<String>) {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(phrases) { phrase ->
            Text(phrase)
            Divider()
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CreatePokemonView() {
    val pokemonName = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Row {
        TextField(value = pokemonName.value, onValueChange = {pokemonName.value = it})
        Button(onClick = { GlobalScope.launch { Greeting().createPokemon(pokemonName.value.text, "fire") } }, content = { Text(
            text = "Create"
        )})
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView(listOf("Hello, Android!"))
    }
}
