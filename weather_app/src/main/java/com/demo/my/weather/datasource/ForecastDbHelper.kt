package com.demo.my.weather.datasource

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.demo.my.weather.App
import org.jetbrains.anko.db.*

// NOTE : 2018/3/27  by  lenovo  : 使用anko的ManagedSQLiteOpenHelper,接收四个参数(ctx, name, factory, version)
//public class ForecastDbHelper(ctx: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : ManagedSQLiteOpenHelper(ctx, name, factory, version) {
class ForecastDbHelper(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx,
        ForecastDbHelper.DB_NAME, null, ForecastDbHelper.DB_VERSION) {

    companion object {
        val DB_NAME = "forecast.db"
        val DB_VERSION = 1
        val instance by lazy { ForecastDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
//        db.createTable(tableName: String, ifNotExists: Boolean = false, vararg columns: Pair<String, SqlType>) {
//// NOTE : 2018/3/27  by  lenovo  :  第一个参数是表的名称, 第二个参数，当是true的时候，创建之前会检查这个表是否存在, 第三个参数是一个 Pair  类型的 vararg  参数。 vararg  也存在在Java中, 这是一种在一个函数中传入联系很多相同类型的参数。这个函数也接收一个对 象数组。
//        Anko中有一种叫做 SqlType  的特殊类型，它可以与 SqlTypeModifiers  混合，
//        比如 PRIMARY_KEY  。 +  操作符像之前那样被重写了。这个 plus  函数会把两者
//        通过合适的方式结合起来，然后返回一个新的 SqlType  ：
//        fun SqlType.plus(m: SqlTypeModifier) : SqlType {
//            return SqlTypeImpl(name, if (modifier == null) m.toString()
//            else "$modifier $m")
//        }
//        如你所见，她会把多个修饰符组合起来。
//        但是回到我们的代码，我们可以修改得更好。Kotlin标准库中包含了一个叫 to  的
//        函数，又一次，让我们来展示Kotlin的强大之处。它作为第一参数的扩展函数，接
//        收另外一个对象作为参数，把两者组装并返回一个 Pair  。
//        public fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
//        因为带有一个函数参数的函数可以被用于inline，所以结果非常清晰：
//        val pair = object1 to object2
        db.createTable(CityForecastTable.NAME, true,
                CityForecastTable.ID to INTEGER + PRIMARY_KEY,
                CityForecastTable.CITY to TEXT,
                CityForecastTable.COUNTRY to TEXT)

        db.createTable(DayForecastTable.NAME, true,
                DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DayForecastTable.DATE to INTEGER,
                DayForecastTable.DESCRIPTION to TEXT,
                DayForecastTable.HIGH to INTEGER,
                DayForecastTable.LOW to INTEGER,
                DayForecastTable.ICON_URL to TEXT,
                DayForecastTable.CITY_ID to INTEGER)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(CityForecastTable.NAME, true)
        db.dropTable(DayForecastTable.NAME, true)
        onCreate(db)
    }

}