package pmdm.tarea3.gamerlog.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videojuegos")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val genero: String,
    val plataforma: String,
    val estado: String // Ej: "Terminado", "Jugando"
)