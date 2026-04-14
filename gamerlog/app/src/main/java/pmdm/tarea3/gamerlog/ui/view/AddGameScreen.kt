package pmdm.tarea3.gamerlog.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pmdm.tarea3.gamerlog.ui.model.GameModel
import pmdm.tarea3.gamerlog.ui.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // ESTADOS PARA TODOS LOS CAMPOS
    var titulo by remember { mutableStateOf("") }
    var plataforma by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    // Listas de opciones
    val plataformasOptions = listOf("PC", "PS5", "Xbox", "Switch")
    val generosOptions = listOf("RPG", "Aventura", "Shooter", "Deportes", "Survival Horror")
    val estadosOptions = listOf("Pendiente", "Jugando", "Terminado")

    // ESTILO PARA LOS CAMPOS EN MODO OSCURO (Texto blanco)
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        cursorColor = Color.White,
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.LightGray,
        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.LightGray,
    )

    Scaffold(
        containerColor = Color(0xFF0F172A), // Fondo Oscuro
        topBar = {
            TopAppBar(
                title = { Text("Añadir Juego", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F172A))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF0F172A)) // Aseguramos el fondo oscuro
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // CAMPO TÍTULO
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )

            // DESPLEGABLES ,DarkDropdown evita repetir codigo
            DarkDropdown("Plataforma", plataformasOptions, plataforma, { plataforma = it }, textFieldColors)
            DarkDropdown("Género", generosOptions, genero, { genero = it }, textFieldColors)
            DarkDropdown("Estado", estadosOptions, estado, { estado = it }, textFieldColors)

            Spacer(modifier = Modifier.weight(1f))

            // BOTÓN GUARDAR
            Button(
                onClick = {
                    // Solo guardamos si todos los campos tienen datos
                    if (titulo.isNotEmpty() && plataforma.isNotEmpty() && genero.isNotEmpty() && estado.isNotEmpty()) {
                        viewModel.addGame(
                            GameModel(
                                titulo = titulo,
                                plataforma = plataforma,
                                genero = genero,
                                estado = estado
                            )
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("GUARDAR")
            }
        }
    }
}

// FUNCIÓN AUXILIAR PARA LOS MENÚS DESPLEGABLES
// Esto hace que el código de arriba sea mucho más limpio y fácil de leer
@Composable
fun DarkDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    colors: TextFieldColors
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true, // Para que no se pueda escribir, solo seleccionar
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            colors = colors
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1E293B)) // Fondo gris oscuro para el menú
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = Color.White) }, // Texto blanco
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}