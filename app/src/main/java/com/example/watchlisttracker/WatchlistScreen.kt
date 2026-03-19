package com.example.watchlisttracker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable

fun WatchlistScreen(
    uiState: FilmsUiState,
    onSearchQueryChange: (String) -> Unit,
    onFilterChange: (String) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onStatusClick: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("WATCH LIST")


        //поиск
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Поиск фильма...") },
            singleLine = true
        )

        //фильтры
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = uiState.selectedFilter == "Все",
                onClick = { onFilterChange("Все") },
                label = { Text("Все") }
            )
            FilterChip(
                selected = uiState.selectedFilter == "Planned",
                onClick = { onFilterChange("Planned") },
                label = { Text("В планах") }
            )
            FilterChip(
                selected = uiState.selectedFilter == "Watching",
                onClick = { onFilterChange("Watching") },
                label = { Text("Смотрю") }
            )
            FilterChip(
                selected = uiState.selectedFilter == "Done",
                onClick = { onFilterChange("Done") },
                label = { Text("Просмотрено") }
            )
            FilterChip(
                selected = uiState.selectedFilter == "Избранное",
                onClick = { onFilterChange("Избранное") },
                label = { Text("Избранное") }
            )
        }

        //статистика
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Смотрю: ${uiState.watchingCount}")
                Text("Просмотрено: ${uiState.doneCount}")
                Text("Избранное: ${uiState.favoriteCount}")
            }
        }


        //список фильмов
        if (uiState.filteredFilms.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ничего не найдено!"
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.filteredFilms) { film ->
                    FilmCard(
                        film = film,
                        onFavoriteClick = { onFavoriteClick(film.id) },
                        onStatusClick = onStatusClick
                    )
                }
            }
        }

    }

}

@Composable
fun FilmCard(
    film: Film,
    onFavoriteClick: (Int) -> Unit,
    onStatusClick: (Int, String) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, vertical = 4.dp),


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Информация о фильме
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = film.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${film.year} • ${film.genre}",
                    style = MaterialTheme.typography.bodyMedium
                )
                // Статус фильма
                Text(
                    text = when (film.status) {
                        "Planned" -> "В планах"
                        "Watching" -> "Смотрю"
                        "Done" -> "Просмотрено"
                        else -> film.status
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Кнопки справа
            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Кнопка для статуса
                Button(
                    onClick = { onStatusClick(film.id, film.status) },
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text("Изменить статус")
                }

                // Кнопка для избранного
                Button(
                    onClick = { onFavoriteClick(film.id) }
                ) {
                    Text(
                        text = if (film.favorite) "❤️" else "🤍"
                    )
                }
            }
        }
    }
}