package pmdm.tarea3.gamerlog.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pmdm.tarea3.gamerlog.data.local.GameDao
import pmdm.tarea3.gamerlog.data.local.GameDatabase
import pmdm.tarea3.gamerlog.data.local.GameEntity
import javax.inject.Provider // ¡IMPORTANTE! Realiza la precarga de los juegos
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideGameDatabase(
        @ApplicationContext context: Context,
        provider: Provider<GameDao> // Inyectamos el Provider, no el Dao directo [cite: 1000]
    ): GameDatabase {
        return Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            "gamerlog_db"
        ).addCallback(object : RoomDatabase.Callback() {
            // TRUCO PRO: Pre-carga de datos
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    provider.get().insertAll( // Obtenemos el DAO aquí
                        GameEntity(titulo = "The Last of Us", genero = "survival horror", plataforma = "PS5", estado = "Terminado"),
                        GameEntity(titulo = "Yakuza: Like a Dragon", genero = "RPG", plataforma = "PS5", estado = "Terminado"),
                        GameEntity(titulo = "Expedition 33", genero = "RPG", plataforma = "PS5", estado = "Pendiente"),
                        GameEntity(titulo = "Ghost of Yotei", genero = "Aventura", plataforma = "PS5", estado = "Jugando"),
                        GameEntity(
                            titulo = "Kingdom Come Deliverance 2",
                            genero = "RPG",
                            plataforma = "PS5",
                            estado = "Pendiente"
                        )
                    )
                }
            }
        }).build()
    }

    @Provides
    @Singleton
    fun provideGameDao(database: GameDatabase): GameDao {
        return database.gameDao()
    }
}