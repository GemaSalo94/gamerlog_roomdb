package pmdm.tarea3.gamerlog.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM videojuegos")
    fun getAllGames(): Flow<List<GameEntity>>

    // Búsqueda por título o género (Misión C)
    @Query("SELECT * FROM videojuegos WHERE titulo LIKE :query OR genero LIKE :query")
    fun searchGames(query: String): Flow<List<GameEntity>> //
    @Query("SELECT * FROM videojuegos WHERE id = :id")
    fun getGameById(id: Int): Flow<GameEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg games: GameEntity) // Para la precarga

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: GameEntity)

    @Delete
    suspend fun delete(game: GameEntity)
}