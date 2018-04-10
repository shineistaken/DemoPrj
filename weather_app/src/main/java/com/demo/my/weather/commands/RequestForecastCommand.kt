package com.demo.my.weather.commands

import com.demo.my.weather.datasource.ForecastProvider
import com.demo.my.weather.model.ForecastList


class RequestForecastCommand(private val zipCode: Long,
                             private val forecastProvider: ForecastProvider = ForecastProvider())
    : Commands<ForecastList> {
    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList = forecastProvider.requestByZipCode(zipCode, DAYS)
}