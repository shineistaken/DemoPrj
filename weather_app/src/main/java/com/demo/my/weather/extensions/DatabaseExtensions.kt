package com.demo.my.weather.extensions

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder

fun SQLiteDatabase.clear(tabName: String) {
   execSQL("delete from $tabName")
}

fun <K, V : Any> Map<K, V?>.toVarargArray(): Array<Pair<K, V?>> =
        map { Pair(it.key,it.value) }.toTypedArray()

// NOTE : 2018/3/28  by  lenovo  : 这个函数有两个可重载的方法，这个拓展方法用来解决重载冲突
// NOTE : 2018/3/28  by  lenovo  : 函数使用 RowParser  或 RapRowParser  这两个不同之处就是 RowParser  是依赖列的顺序的，而 MapRowParser  是从map中拿到作为column的key名的。
fun <T : Any> SelectQueryBuilder.parseList(parser: (Map<String, Any?>) -> T): List<T> =
        parseList(object : MapRowParser<T> {
    override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
})

// NOTE : 2018/3/28  by  lenovo  : 同上, 另外anko中还可以使用 classParser()  来替代MapRowParser  ，它是基于列名通过反射的方式去生成对象的。
fun <T : Any> SelectQueryBuilder.parseOpt(parser: (Map<String, Any?>) -> T): T? =
        parseOpt(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
        })

fun SelectQueryBuilder.byId(id: Long) = whereSimple("_id=?", id.toString())