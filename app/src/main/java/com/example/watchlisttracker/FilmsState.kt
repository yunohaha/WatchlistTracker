package com.example.watchlisttracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class FilmsUiState(
    val films: List<Film> = sampleFilmsList,
    val searchQuery: String = "",
    val selectedFilter: String = "Все"
) {

    val watchingCount: Int = films.count { it.status == "Watching" }
    val doneCount: Int = films.count { it.status == "Done" }
    val favoriteCount: Int = films.count { it.favorite }

    //фильтр поиска по названию
    val filteredFilms: List<Film>
        get() {
            val afterSearch = if (searchQuery.isBlank()) {
                films
            } else {
                films.filter { film ->
                    film.name.contains(searchQuery, ignoreCase = true)
                }
            }
            return when (selectedFilter) {
                "Все" -> afterSearch
                "Planned" -> afterSearch.filter { it.status == "Planned" }
                "Watching" -> afterSearch.filter { it.status == "Watching" }
                "Done" -> afterSearch.filter { it.status == "Done" }
                "Избранное" -> afterSearch.filter { it.favorite }
                else -> afterSearch
            }
        }

}

class FilmsState {
    var uiState by mutableStateOf(FilmsUiState())
        private set

    fun onSearchQueryChange(query: String){
        uiState = uiState.copy(searchQuery = query)
    }

    fun onFilterChange(filter: String){
        uiState = uiState.copy(selectedFilter = filter)
    }

    fun onFavoriteClick(filmId: Int){
        val updatedFilms = uiState.films.map { film ->
            if (film.id == filmId) {
                film.copy(favorite = !film.favorite)
            } else
                film
        }
        uiState = uiState.copy(films = updatedFilms)
    }


    fun onStatusChange(filmID: Int, newStatus: String){
        val updatedFilms = uiState.films.map { film ->
            if (film.id == filmID){
                film.copy(status = newStatus)
            }else {
                film
            }
        }
        uiState = uiState.copy(films =  updatedFilms)
    }


    fun getNextStatus(currentStatus: String): String{
        return when (currentStatus){
            "Planned" -> "Watching"
            "Watching" -> "Done"
            "Done" -> "Planned"
            else -> "Planned"
        }
    }
}