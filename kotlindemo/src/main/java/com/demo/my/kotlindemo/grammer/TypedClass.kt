package com.demo.my.kotlindemo.grammer

/**
 * Created by lenovo on 2018/2/8.
 */

/*
// todo 泛型

class Data<T>(var t : T)
interface Data<T>
fun <T> logic(t : T){}

定义:

class TypedClass<T>(parameter: T) {
    val value: T = parameter
}

这个类现在可以使用任何的类型初始化，并且参数也会使用定义的类型，我们可以这么做：

val t1 = TypedClass<String>("Hello World!")
val t2 = TypedClass<Int>(25)

但是Kotlin很简单并且缩减了模版代码，所以如果编译器能够推断参数的类型，我们甚至也就不需要去指定它：

val t1 = TypedClass("Hello World!")
val t2 = TypedClass(25)
val t3 = TypedClass<String?>(null)

todo 类型擦除

class Data<T>{}

Log.d("test", Data<Int>().javaClass.name)
Log.d("test", Data<String>().javaClass.name)

// 输出
com.study.jcking.weatherkotlin.exec.Data
com.study.jcking.weatherkotlin.exec.Data

声明了一个泛型类Data<T>，并实现了两种不同类型的实例。
但是在获取类名是，却发现得到了同样的结果com.study.jcking.weatherkotlin.exec.Data，
这其实是在编译期擦除了泛型类型声明。
 */