package com.demo.my.weather.datasource

import android.telecom.StatusHints

// NOTE : 2018/3/28  by  lenovo  :  Map委托 值会根据key映射到相应的属性中去，属性的名字必须要和数据库中对应的名字一致
class CityForcast(val map: MutableMap<String, Any?>, val dailyForecast: List<DayForecast>) {
    var _id: Long by map
    var city: String by map
    var country: String by map

    constructor(id:Long,city:String,country:String,dailyForecast: List<DayForecast>) : this(HashMap(),dailyForecast){
        this._id=id
        this.city=city
        this.country=country
    }

}

class DayForecast(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var date: Long by map
    var description: String by map
    var high: Int by map
    var low: Int by map
    var iconUrl: String by map
    var cityId: Long by map


    constructor(date: Long,
                description: String,
                high: Int,
                low: Int,
                iconUrl: String,
                cityId: Long) : this(HashMap()) {
        this.date = date
        this.description = description
        this.high = high
        this.low = low
        this.iconUrl = iconUrl
        this.cityId = cityId
    }
}
