package com.demo.my.weather.datasource

// NOTE : 2018/4/8  by  lenovo  : 可用as关键字指定别名 这里用作局部重命名
import com.demo.my.weather.commands.ForecastByZipCodeRequest
import com.demo.my.weather.model.Forecast as ModelForecast

import com.demo.my.weather.model.ForecastList
import java.lang.UnsupportedOperationException

class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val convertToDomain = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(convertToDomain)
        return forecastDb.requestForecastByZipCode(zipCode,date)
    }

    override fun requestDayForecast(id: Long)=throw UnsupportedOperationException()
}


