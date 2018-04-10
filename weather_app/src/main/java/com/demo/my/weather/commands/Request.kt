package com.demo.my.weather.commands

import com.demo.my.weather.datasource.ForecastProvider
import com.demo.my.weather.model.Forecast

class RequestDayForecastCommand(val id: Long,
                                private val forecastProvider: ForecastProvider = ForecastProvider())
    : Command<Forecast> {
    override fun execute(): Forecast = forecastProvider.requestForecast(id)
}

interface Command<out T> {
    fun execute(): T
}