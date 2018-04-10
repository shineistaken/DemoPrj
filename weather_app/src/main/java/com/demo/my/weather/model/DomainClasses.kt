package com.demo.my.weather.model
/**
 * Created by lenovo on 2018/3/16.
 */

// NOTE : 2018/3/16  by  lenovo  : 数据类的写法
data class ForecastList(val id: Long,
                        val city: String,
                        val country: String,
                        val dailyForecast: List<Forecast>) {
    val size = dailyForecast.size
    operator fun get(position: Int) = dailyForecast[position]
}

data class Forecast(val id: Long, val date: Long, val description: String, val high: Int, val low: Int, val iconUrl: String) {
}
