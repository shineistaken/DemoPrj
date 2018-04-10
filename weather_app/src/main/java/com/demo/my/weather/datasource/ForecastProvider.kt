package com.demo.my.weather.datasource

import com.demo.my.weather.extensions.firstResult
import com.demo.my.weather.model.ForecastList

/**
 * Created by lenovo on 2018/3/16.
 */

class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {
    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24 //一天
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }

    // NOTE : 2018/3/27  by  lenovo  : 获取指定地区zipCoded 的 days 天数的天气数据
    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    // NOTE : 2018/3/27  by  lenovo  : 先使用拓展函数从sources这个list中取出了第一个元素，然后执行requestByZipCode(zipCode: Long, days: Int)中定义的方法
    private fun <T : Any> requestToSources(function: (ForecastDataSource) -> T?): T = sources.firstResult { function(it) }

    private fun todayTimeSpan(): Long = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    fun requestForecast(id: Long) =requestToSources { it.requestDayForecast(id) }
}

