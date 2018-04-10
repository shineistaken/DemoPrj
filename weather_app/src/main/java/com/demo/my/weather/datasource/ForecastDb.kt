package com.demo.my.weather.datasource

import com.demo.my.weather.extensions.*
import com.demo.my.weather.model.ForecastList
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {
//    在lambda里面，我们可以直接使用 SqliteDatabase  中的函数。它是怎么工作的？阅读Anko函数的实现方式真是一件有趣的事情，你可以从这里学到Kotlin的很多知识：
//    public fun <T> use(f: SQLiteDatabase.() -> T): T {
//        try {
//            return openDatabase().f()
//        } finally {
//            closeDatabase()
//        }
//    }
    // NOTE : 2018/3/27  by  lenovo  :    首先， use  接收一个 SQLiteDatabase  的扩展函数。这表示，我们可以使用 this  在大括号中，并且处于 SQLiteDatabase  对象中。这个函数扩展可以返 回一个值，所以我们可以像这么做：
//    val result = forecastDbHelper.use {
//        val queriedObject = ...
//        queriedObject
//    }

//    要记住，在一个函数中，最后一行表示返回值。因为T没有任何的限制，所以我们
//    可以返回任何对象。甚至如果我们不想返回任何值就使用 Unit  。
//    由于使用了 try-finally  ， use  方法会确保不管在数据库操作执行成功还是失
//    败都会去关闭数据库。

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID}=? AND ${DayForecastTable.DATE}>= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                // NOTE : 2018/3/28  by  lenovo  :  parseList 把cursor转换成一个对象的集合。
                .parseList {
                    DayForecast(HashMap(it))
                }
        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID}=?", zipCode.toString())
                // NOTE : 2018/3/28  by  lenovo  : 这个函数返回一个可null的对象，如果查到数据则返回一个对象，否则返回null
                //有另外一个叫 parseSingle  的函数，本质上是一样的，但是它返回的事一个不可null的对象。所以如果没有在数据库中找到这一条数据，它会抛出一个异常。
                .parseOpt { CityForcast(HashMap(it), dailyForecast) }
        city?.let { dataMapper.convertToDomain(it) }
    }

    override fun requestDayForecast(id: Long) = forecastDbHelper.use {
        val dayForecast = select(DayForecastTable.NAME).byId(id)//这里把查询语句放到了拓展函数中
                .parseOpt { DayForecast(HashMap(it)) }
        dayForecast?.let { dataMapper.convertDayToDomain(it) }
    }

    fun saveForecast(forecasts: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecasts)) {
            insert(CityForecastTable.NAME,*map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME,*it.map.toVarargArray()) }
        }
    }
}

