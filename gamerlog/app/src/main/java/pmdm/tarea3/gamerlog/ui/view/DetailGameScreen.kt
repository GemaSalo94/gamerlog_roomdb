package pmdm.tarea3.gamerlog.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.* // Importa todos los iconos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pmdm.tarea3.gamerlog.ui.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailGameScreen(
    gameId: Int,
    viewModel: GameViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // Pedimos el juego al ViewModel
    val game by viewModel.getGame(gameId).collectAsStateWithLifecycle(initialValue = null)

    Scaffold(
        containerColor = Color(0xFF0F172A), // 1. FONDO OSCURO PRINCIPAL
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Juego", color = Color.White) }, // Título blanco
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White // Flecha blanca
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A) // Barra oscura igual que el fondo
                )
            )
        }
    ) { padding ->
        if (game != null) {

            //LÓGICA DE ICONOS (Igual que en la pantalla principal)
            val icon = when (game!!.genero.lowercase()) {
                "rpg" -> Icons.Default.Castle
                "deportes" -> Icons.Default.SportsSoccer
                "survival horror" -> Icons.Default.Coronavirus
                "aventura" -> Icons.Default.Backpack
                else -> Icons.Default.Gamepad
            }

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ICONO GRANDE (pantalla dle juego)
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp).padding(bottom = 16.dp),
                    tint = Color.White
                )

                // Título en Blanco
                Text(
                    text = game!!.titulo,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Tarjeta de Datos (Gris oscuro para contrastar)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E293B)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailRow("Plataforma", game!!.plataforma)
                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)
                        DetailRow("Género", game!!.genero)
                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)
                        DetailRow("Estado", game!!.estado)
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Etiqueta en gris claro
        Text(text = label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = Color.LightGray)
        // Valor en blanco
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }
}