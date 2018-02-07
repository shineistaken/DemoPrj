package com.demo.my.kotlindemo.bean

import android.util.Log

/**
 * Created by lenovo on 2018/2/7.
 */

// 简单POJO数据类

//如果有参数的话你只需要在类名后面写上它的参数，如果这个类没有任何内容可以省略大括号：
//类有一个默认的构造函数 在Kotlin中的一个类可以有一个主构造函数和一个或多个次构造函数。
//class Person(name: String, age: Int)

// todo 构造函数
//主构造函数是类头的一部分:它跟在类名（和可选的类型参数）后:
//
//class Person constructor(name: String, surname: String) {
//}

//如果主构造函数没有任何注解或者可见性修饰符，可以省略constructor关键字:
//
//class Person(name: String, surname: String) {
//}

//主构造函数不能包含任何的代码。初始化的代码可以放到以init关键字作为前缀的初始化块中:
//
//class Person constructor(name: String, surname: String) {
//    init {
//        print("name is $name and surname is $surname")
//    }
//}

//如果构造函数有注解或可见性修饰符，那么constructor关键字是必需的，并且这些修饰符在它前面:
//
//class Person private @Inject constructor(name: String, surname: String) {
//    init {
//        print("name is $name and surname is $surname")
//    }
//}

//次构造函数
//
//类也可以声明前缀有constructor的次构造函数:
//
//class Person{
//    constructor(name: String) {
//        print("name is $name")
//    }
//}

// todo 构造函数委托
//如果类有一个主构造函数，每个次构造函数都需要委托给主构造函数(不然会报错)， 可以直接委托或者通过别的次构造函数间接委托。
// 委托到同一个类的另一个构造函数用this关键字即可:

//class Person constructor(name: String) {
//    constructor(name: String, surName: String) : this(name) {
//        Log.d("@@@", "name is : $name surName is : $surName")
//    }
//}

//如果一个非抽象类没有声明任何（主或次）构造函数，它会有一个生成的不带参数的主构造函数。
// 构造函数的可见性是public。如果你不希望你的类有一个公有构造函数，你需要声明一个带有非默认可见性的空的主构造函数：
//
//class Person private constructor(name: String) {
//}

// todo get/set方法
//class Person{
//    //可以手动实现get/set方法，field为保留字段,value可以用任意变量名替换
//    var name:String=""
//        get() =field
//        set(value) {
//            field="$value"
//        }
//    var age:Int=0
//        get() = field
//        set(value) {
//            field=value
//        }
//}

// todo 数据类:使用data class定义
//通过数据类，会自动提供以下函数：
//
//所有属性的get() set()方法
//equals()
//hashCode()
//copy()
//toString()
//一系列可以映射对象到变量中的函数
data class Person(var name: String, var age: Int?, var sex: String?) : BasePerson() {
    init {
        Log.i(this.javaClass.simpleName, "my name is $name ,I'm $age years old,and I'm a $sex.")
    }

    constructor(age: Int?) : this("no one", age, "mixedGender")

    constructor(sex: String?) : this(18) {
    }

    //    todo 属性覆盖
//    属性覆盖与方法覆盖类似，只能覆盖显示标明open的属性，并且要用override开头:
//    每个声明的属性可以由具有初始化器的属性或者具有get方法的属性覆盖，
// 你也可以用一个var属性覆盖一个val属性，但反之则不行。
    override var intellect: Float = super.intellect
        get() = super.intellect + 20F
        set(value) {
            field = value
        }
}