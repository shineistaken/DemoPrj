package com.demo.my.weather.extensions

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// NOTE : 2018/3/16  by  lenovo  : object 在这里可表示单例,object是lazy-init，即在第一次使用时被创造出来的. object声明的单例对象不能声明构造函数，因为单例对象只有一个实例，无需我们手动将它创建出来，因此自然不需要构造函数。如果需要对单例对象做初始化操作，可以在init初始化块内进行
object DelegatesExt {
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    fun <T> notNullSingleTon() = NotNullSingletonVar<T>()

    fun <T> sp(context: Context, key: String, default: T) = SharedPreference(context, key, default)
}

// NOTE : 2018/3/16  by  lenovo  : 标准委托写法，需指定泛型，并且继承ReadWriteProperty或者ReadOnlyProperty，重写对应的方法
class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {
    private var v: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            v ?: throw IllegalStateException("${property.name} not initialized")

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (v == null) v = value else throw  IllegalStateException("${property.name} already initialized")
    }
}

// NOTE : 2018/3/16  by  lenovo  : 委托的简单写法，只需要重载getValue和setValue方法
class NotNullSingletonVar<T> {
    private var v: T? = null
    // NOTE : 2018/3/16  by  lenovo  : 固定写法，重载的getValue需要两个参数，并且第二个参数只能是KProperty<*>类型
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
            v ?: throw IllegalStateException("${property.name} not initialized")

    // NOTE : 2018/3/16  by  lenovo  : 固定写法，重载的setValue需要三个个参数，并且第二个参数只能是KProperty<*>类型
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (v == null) v = value else throw  IllegalStateException("${property.name} already initialized")
    }
}

// NOTE : 2018/3/16  by  lenovo  : 可以直接在类的构造函数中定义成员变量
class SharedPreference<T>(context: Context, private val name: String, private val default: T) {
    //获取sp
    private val sp: SharedPreferences by lazy { context.getSharedPreferences("default", Context.MODE_PRIVATE) }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getPreference(name, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun putPreference(name: String, value: T) = with(sp.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            is Int -> putInt(name, value)
            is String -> putString(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
    }.apply()

    private fun getPreference(name: String, default: T): T =
            with(sp) {
                val res: Any = when (default) {
                    is Long -> getLong(name, default)
                    is Boolean -> getBoolean(name, default)
                    is Float -> getFloat(name, default)
                    is Int -> getInt(name, default)
                    is String -> getString(name, default)
                    else -> throw IllegalArgumentException("This type can be saved into Preferences")
                }
                res as T
            }
}
