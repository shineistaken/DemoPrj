package com.demo.my.weather.datasource

import com.demo.my.weather.model.Forecast
import com.demo.my.weather.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}