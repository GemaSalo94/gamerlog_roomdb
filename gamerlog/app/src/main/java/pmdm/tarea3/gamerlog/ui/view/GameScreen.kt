package pmdm.tarea3.gamerlog.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Coronavirus
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pmdm.tarea3.gamerlog.ui.model.GameModel
import pmdm.tarea3.gamerlog.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit, // Navegación
    onNavigateToAdd: () -> Unit
) {
    val games by viewModel.uiState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchQuery.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)
            .fillMaxSize() // Obliga a ocupar toda la pantalla
            .background(Color(0xFF0F172A))) {

            // Buscador
            OutlinedTextField(
                value = searchText,
                onValueChange = { texto -> viewModel.onSearchChange(texto) },
                label = { Text("Buscar juego...") }, // El color de esto se controla abajo en 'colors'
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null, // Faltaba esta coma y el nombre del parámetro
                        tint = Color.White
                    )
                },
                // Cambiar color a la cajita de busqueda
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,       // Texto al escribir
                    unfocusedTextColor = Color.White,     // Texto ya escrito
                    cursorColor = Color.White,            // La barrita de escribir
                    focusedBorderColor = Color.White,     // Borde al tocar
                    unfocusedBorderColor = Color.Gray,    // Borde sin tocar
                    focusedLabelColor = Color.White,      // Etiqueta "Buscar juego..." al tocar
                    unfocusedLabelColor = Color.Gray      // Etiqueta "Buscar juego..." sin tocar
                )
            )

            // Grid Obligatorio
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(games) { game ->
                    GameItem(game, onClick = { onNavigateToDetail(game.id) })
                }
            }
        }
    }
}

@Composable
fun GameItem(game: GameModel, onClick: () -> Unit) {

    // Color de fondo de las tarjetas de los juegos
    val colorFondo = when (game.genero.lowercase()) {
        "rpg" -> Color(0xFFD1C4E9)       // Morado suave
        "deportes" -> Color(0xFFC8E6C9)  // Verde suave
        "survival horror" -> Color(0xFFFFCDD2)   // Rojo suave
        "aventura" -> Color(0xFFFFE0B2)  // Naranja suave
        else -> Color(0xFFF5F5F5)        // Gris claro por defecto
    }
    // Icon de los juegos
    val icon = when (game.genero.lowercase()) {
        "rpg" -> Icons.Default.Castle
        "deportes" -> Icons.Default.SportsSoccer
        "survival horror" -> Icons.Default.Coronavirus
        "aventura" -> Icons.Default.Backpack
        else -> Icons.Default.Gamepad
    }

    // creamos la Card usando ese color
    Card(
        modifier = Modifier
            .fillMaxWidth() // Ocupa toda la pantalla
            .height(160.dp) // Misma altura para todas las tarjetas
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = colorFondo
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(), // La columna ocupa toda la tarjeta
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centra el texto y el icono
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp).padding(bottom = 8.dp)
            )

            Text(
                text = game.titulo,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center, // Centra el texto alineado
                maxLines = 2, // Limita a 2 líneas para que no rompa el diseño
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis // Pone "..." si es muy largo
            )
        }
    }
}