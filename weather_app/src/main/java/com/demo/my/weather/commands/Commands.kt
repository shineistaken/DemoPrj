package com.demo.my.weather.commands

/**
 * Created by lenovo on 2018/3/16.
 */

// NOTE : 2018/3/16  by  lenovo  : Kotlin仅仅使用 out  来针对协变（ covariance  ）和使用 in  来针对逆变（ contravariance  ）
/*
当我们类产生的对象可以被保存到弱限
制的变量中，我们使用协变。我们可以直接在类中定义声明｛
class TypedClass<out T>() {
    fun doSomething(): T {
        ...
    }
}
这就是所有我们需要的。现在，在Java中不能编译的代码在Kotlin中可以完美运：
val t1 = TypedClass<String>()
val t2: TypedClass<Any> = t1
*/

interface Commands<out T> {
    fun execute(): T
}
