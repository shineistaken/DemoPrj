package com.demo.my.weather.extensions

import android.content.Context
import android.support.v4.content.ContextCompat
import java.text.DateFormat
import java.util.*

/**
 *   /** getDateInstance() */
/** 输出格式: 2006-4-16 */
s = DateFormat.getDateInstance().format(d);
System.out.println(s);

/** 输出格式: 2006-4-16 */
s = DateFormat.getDateInstance(DateFormat.DEFAULT).format(d);
System.out.println(s);

/** 输出格式: 2006年4月16日 星期六 */
s = DateFormat.getDateInstance(DateFormat.FULL).format(d);
System.out.println(s);

/** 输出格式: 2006-4-16 */
s = DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
System.out.println(s);

/** 输出格式: 06-4-16 */
s = DateFormat.getDateInstance(DateFormat.SHORT).format(d);
System.out.println(s);

/** 输出格式: 2006-01-01 00:00:00 */
java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
s = format1.format(new Date());
System.out.println(s);
 */
fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val dateFormat = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return dateFormat.format(this)
}

fun <T, T1> Iterable<T>.firstResult(function: (T) -> T1?): T1 {
    for (element in this) {
        val result = function(element)
        if (result != null) return result
    }
    throw NoSuchElementException("No element matching predicate was found.")
}

// NOTE : 2018/4/9  by  lenovo  : 拓展方法 根据资源id获取颜色
fun Context.color(res: Int) = ContextCompat.getColor(this, res)