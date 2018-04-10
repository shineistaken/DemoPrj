package com.demo.my.weather.datasource

import com.demo.my.weather.model.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit

class ServerDataMapper {
    fun convertToDomain(zipCode: Long, result: ForecastResult) = with(result) {
        ForecastList(zipCode, city.name, city.country, convertForecastListToDomain(list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<com.demo.my.weather.model.Forecast> {
        return list.mapIndexed { index, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMicros(index.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast)= with(forecast){
        com.demo.my.weather.model.Forecast(-1, dt, weather[0].description, temp.max.toInt(), temp.min.toInt(),
                generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(icon: String)="http://openweathermap.org/img/w/$icon.png"
}