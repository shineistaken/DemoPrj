package com.demo.my.weather

import android.app.Application
import com.demo.my.weather.extensions.DelegatesExt

/**
 * Created by lenovo on 2018/3/13.
 */

class App : Application() {
    // NOTE : 2018/3/16  by  lenovo  :  companion object
//    Kotlin允许我们去定义一些行为与静态对象一样的对象。尽管这些对象可以
//    用众所周知的模式来实现，比如容易实现的单例模式。
//    我们需要一个类里面有一些静态的属性、常量或者函数，我们可以使
//    用 companion objecvt  。这个对象被这个类的所有对象所共享，就像
//    Java中的静态属性或者方法。
    companion object {
        //        var instance: App by DelegatesExt.notNullSingleTon()

        //        var instance: App by DelegatesExt.notNullSingleValue<App>() //简写如下
        var instance: App by DelegatesExt.notNullSingleValue()

        // NOTE : 2018/3/16  by  lenovo  :泛型
        // kotlin可以自动推导的类型，泛型参数可以省略
//        举个例子，我们可以创建一个指定泛型类：
//        class TypedClass<T>(parameter: T) {
//            val value: T = parameter
//        }
//        这个类现在可以使用任何的类型初始化，并且参数也会使用定义的类型，我们可以
//        这么做：
//        val t1 = TypedClass<String>("Hello World!")
//        val t2 = TypedClass<Int>(25)
//        但是Kotlin很简单并且缩减了模版代码，所以如果编译器能够推断参数的类型，我
//        们甚至也就不需要去指定它：
//        val t1 = TypedClass("Hello World!")
//        val t2 = TypedClass(25)
//        val t3 = TypedClass<String?>(null)
//        如第三个对象接收一个null引用，那仍然还是需要指定它的类型，因为它不能去推
//        断出来。
//        我们可以像Java中那样在定义中指定的方式来增加类型限制。比如，如果我们想限
//        制上一个类中为非null类型，我们只需要这么做：
//        class TypedClass<T : Any>(parameter: T) {
//            val value: T = parameter
//        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

