package com.demo.my.kotlindemo.list

import com.demo.my.kotlindemo.bean.Person


/**
 * Created by lenovo on 2018/2/8.
 */
class List {
    //   todo 数组
    //    数组用类Array实现，并且还有一个size属性及get和set方法，
// 由于使用[]重载了get和set方法，所以我们可以通过下标很方便的获取或者设置数组对应位置的值。
// Kotlin标准库提供了arrayOf()创建数组和xxArrayOf创建特定类型数组

    //    和Java不一样的是Kotlin的数组是容器类，
// 提供了ByteArray,CharArray,ShortArray,IntArray,LongArray,BooleanArray,FloatArray和DoubleArray。
    val array = arrayOf(1, 2, 3)
    val countries = arrayOf("UK", "Germany", "Italy")
    val numbersArray = intArrayOf(10, 20, 30)
    val array1 = Array(10, { k -> k * k })
    val personArry = emptyArray<Person>()


//    todo 集合

//    Kotlin的List<out T>类型是一个提供只读操作如size、get等的接口。
// 和Java类似，它继承自Collection<T>进而继承自Iterable<T>。
// 改变list的方法是由MutableList<T>加入的。这一模式同样适用于Set<out T>/MutableSet<T>及Map<K, out V>/MutableMap<K, V>。
//
//    Kotlin没有专门的语法结构创建list或set。
// 要用标准库的方法如listOf()、mutableListOf()、setOf()、mutableSetOf()。
// 创建map可以用mapOf(a to b, c to d)。

//    val numbers: MutableList<Int> = mutableListOf(1, 2, 3)
//    val readOnlyView:List<Int>=numbers
//    println(numbers)        // 输出 "[1, 2, 3]"
//    numbers.add(4)
//    println(readOnlyView)   // 输出 "[1, 2, 3, 4]"
//    readOnlyView.clear()    // -> 不能编译
//
//    val strings = hashSetOf("a", "b", "c", "c")
//    assert(strings.size == 3) //输出true
}