package com.bignerdranch.android.nflteams

data class NflTeam(
    val teamID: String,
    val teamName: String,
    val logoFile: String,
    val conference: String,
    val division: String,
    val stadium: String,
    val latitude: Double,
    val longitude: Double
)