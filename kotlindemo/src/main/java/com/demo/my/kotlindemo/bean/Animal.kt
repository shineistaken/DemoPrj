package com.demo.my.kotlindemo.bean

/**
 * Created by lenovo on 2018/2/8.
 */

// todo 修饰符
//
//Kotlin中修饰符是与Java中的有些不同。在kotlin中默认的修饰符是public，这节约了很多的时间和字符。
//
//private
//private修饰符是最限制的修饰符，和Java中private一样。它表示它只能被自己所在的文件可见。
// 所以如果我们给一个类声明为private，我们就不能在定义这个类之外的文件中使用它。
// 另一方面，如果我们在一个类里面使用了private修饰符，那访问权限就被限制在这个类里面了。
// 甚至是继承这个类的子类也不能使用它。
//
//protected.
//与Java一样，它可以被成员自己和继承它的成员可见。
//
//internal 如果是一个定义为internal的包成员的话，对所在的整个module可见。如果它是一个其它领域的成员，它就需要依赖那个领域的可见性了。比如如果写了一个private类，那么它的internal修饰的函数的可见性就会限制与它所在的这个类的可见性。
//
//public.
//你应该可以才想到，这是最没有限制的修饰符。这是默认的修饰符，成员在任何地方被修饰为public，很明显它只限制于它的领域。

abstract class Animal {
    protected fun breath() {}
    private fun layEgg() {}
    open fun move() {}
    abstract fun eat()
}