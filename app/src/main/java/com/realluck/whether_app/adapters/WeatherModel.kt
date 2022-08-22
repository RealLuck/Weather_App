package com.realluck.whether_app.adapters

data class WeatherModel(
    val city: String,
    val time: String,
    val weather: String,
    val tempCurrent: String,
    val tempMax: String,
    val tempMin: String,
    val imageWeather: String,
    val hours: String
)
