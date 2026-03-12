package com.example.watchlisttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.watchlisttracker.ui.theme.WatchListTrackerTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WatchlistApp()
                }
            }
        }                                               
    }
}


@Composable
fun WatchlistApp(){
    val state = remember { FilmsState() }

    WatchlistScreen(
        uiState = state.uiState,
        onSearchQueryChange = { state.onSearchQueryChange(it) },
        onFilterChange = { state.onFilterChange(it) },
        onFavoriteClick = { state.onFavoriteClick(it) },
        onStatusClick = { filmId, currentStatus ->
        val newStatus = state.getNextStatus(currentStatus)
        state.onStatusChange(filmId, newStatus)
        }
    )
}






