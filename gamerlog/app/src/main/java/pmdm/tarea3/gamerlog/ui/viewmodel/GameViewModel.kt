package pmdm.tarea3.gamerlog.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pmdm.tarea3.gamerlog.data.di.GameRepository
import pmdm.tarea3.gamerlog.ui.model.GameModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    // Variable para el buscador
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // logica reactiva
    val uiState: StateFlow<List<GameModel>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                repository.getAllGames()
            } else {
                repository.searchGames(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
    fun getGame(id: Int): Flow<GameModel> {
        return repository.getGameById(id)
    }
    fun addGame(game: GameModel) {
        viewModelScope.launch {
            repository.insertGame(game)
        }
    }
}