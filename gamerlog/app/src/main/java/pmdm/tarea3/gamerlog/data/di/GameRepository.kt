package pmdm.tarea3.gamerlog.data.di

import pmdm.tarea3.gamerlog.data.local.GameDao
import pmdm.tarea3.gamerlog.data.local.GameEntity
import pmdm.tarea3.gamerlog.ui.model.GameModel
import pmdm.tarea3.gamerlog.ui.model.toEntity
import pmdm.tarea3.gamerlog.ui.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(private val gameDao: GameDao) {

    fun getAllGames(): Flow<List<GameModel>> {
        return gameDao.getAllGames().map { list -> list.map { it.toModel() } }
    }
    fun getGameById(id: Int): Flow<GameModel> {
        return gameDao.getGameById(id).map { it.toModel() }
    }
    fun searchGames(query: String): Flow<List<GameModel>> {
        val dbQuery = "%$query%"
        return gameDao.searchGames(dbQuery).map { list -> list.map { it.toModel() } }
    }

    suspend fun insertGame(game: GameModel) {
        gameDao.insert(game.toEntity())
    }
}