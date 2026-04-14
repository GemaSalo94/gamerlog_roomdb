package pmdm.tarea3.gamerlog.ui.model

import pmdm.tarea3.gamerlog.data.local.GameEntity

data class GameModel(
    val id: Int = 0,
    val titulo: String,
    val genero: String,
    val plataforma: String,
    val estado: String
)

// Funciones de extensión para Mappers
fun GameEntity.toModel() = GameModel(id, titulo, genero, plataforma, estado)
fun GameModel.toEntity() = GameEntity(id, titulo, genero, plataforma, estado)