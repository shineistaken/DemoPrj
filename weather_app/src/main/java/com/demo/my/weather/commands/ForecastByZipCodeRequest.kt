package com.demo.my.weather.commands

import com.demo.my.weather.datasource.ForecastResult
import com.google.gson.Gson

class ForecastByZipCodeRequest(private val zipCode: Long, val gson: Gson = Gson()) {

    companion object {
          val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
          val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
          val COMPLETE_URL = "${URL}&APPID=${APP_ID}&zip="
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = java.net.URL(COMPLETE_URL + zipCode).readText()
        return gson.fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}