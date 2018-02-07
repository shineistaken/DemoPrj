package com.demo.my.kotlindemo.bean

import java.nio.file.Files.walk

/**
 * Created by lenovo on 2018/2/8.
 */

//在Kotlin中所有类都有一个共同的超类Any，这对于没有超类型声明的类是默认超类:
//Any不是java.lang.Object。它除了equals()、hashCode()和toString()外没有任何成员。 Kotlin中所有的类默认都是不可继承的(final)
//只能继承那些明确声明open或者abstract的类

//要声明一个显式的超类型，我们把类型放到类头的冒号之后:
//
//open class Person(num: Int)
////todo 继承
//class SuperPerson(num: Int) : Person(num)

//如果该类有一个主构造函数，其基类必须用基类型的主构造函数参数就地初始化。
// 如果类没有主构造函数，那么每个次构造函数必须使用super关键字初始化其基类型，或委托给另一个构造函数做到这一点。
// 注意，在这种情况下，不同的次构造函数可以调用基类型的不同的构造函数:

//class MyView : View {
//    constructor(ctx: Context) : super(ctx)
//    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
//}


open class BasePerson : Mammal() {
    override fun eat() {
    }

    open var intellect: Float = 60.0F
    fun walk() {}
    override fun move() {
        super.move()
        walk()
    }
}