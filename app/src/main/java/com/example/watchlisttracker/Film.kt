package com.example.watchlisttracker
data class Film(
    val id: Int,
    val name: String,
    val year: Int,
    val genre: String,
    val status: String,
    val favorite: Boolean = false

)

val sampleFilmsList = listOf(
    Film(1, "The Shawshank Redemption", 	1994, "драма","Planned"),
    Film(2, "The Godfather",1972,"приключения","Watching"),
    Film(3, "Sen to Chihiro no kamikakushi", 1972,"криминал","Done"),
    Film(4, "Schindler's List",1999,"аниме","Done"),
    Film(5, "Pulp Fiction",2001,"драма","Watching"),
    Film(6, "Dune: Part Two",1993,"комедия","Watching"),
    Film(7, "Anora",1994,"мелодрама","Planned"),
    Film(8, "Sinners",2024,"приключения","Planned")
)
