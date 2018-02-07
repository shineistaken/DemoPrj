package com.demo.my.kotlindemo.grammer

/**
 * Created by lenovo on 2018/2/8.
 */
/*
todo Lambda 表达式定义
“Lambda 表达式”(lambda expression)是一个匿名函数，
Lambda表达式基于数学中的λ演算得名，直接对应于其中的lambda抽象(lambda abstraction)，
是一个匿名函数，即没有函数名的函数。Lambda表达式可以表示闭包（注意和数学传统意义上的不同）。

Lambda能让代码更简洁，而主打简洁的Kotlin怎么可能不支持呢？ 当然会支持。

概述:

    lambda表达式总是被大括号括着
    其参数(如果有的话)在->之前声明(参数类型可以省略)，
    函数体(如果存在的话)在->后面。

Lambda表达式是定义匿名函数的简单方法。
由于Lambda表达式避免在抽象类或接口中编写明确的函数声明，
进而也避免了类的实现部分，所以它是非常有用的。
 在Kotlin语言中，可以将一函数作为另一函数的参数。

Lambda表达式由箭头左侧函数的参数（在圆括号里的内容）定义的，将值返回到箭头右侧。
 view.setOnClickListener({ view -> toast("Click")})

 在定义函数时，必须在箭头的左侧用方括号，并指定参数值，而函数的执行代码在箭头右侧。
 如果左侧不使用参数，甚至可以省去左侧部分:
view.setOnClickListener({ toast("Click") })

如果函数的最后一个参数是一个函数的话，可以将作为参数的函数移到圆括号外面:
 view.setOnClickListener() { toast("Click") }

先看一个例子:

fun compare(a: String, b: String): Boolean {
    return a.length < b.length
}
max(strings, compare)

就是找出strings里面最长的那个。但是我个人觉得compare还是很碍眼的，因为我并不想在后面引用他，那我怎么办呢，就是用“匿名函数”方式。

max(strings, (a,b)->{a.length < b.length})

(a,b)->{a.length < b.length}就是一个没有名字的函数，直接作为参数赋给max方法的第二个参数。但这个方法有很多东西都没有写明，如:

    参数的类型
    返回值的类型

但这些真的必要吗？a.length < b.length很明显返回一个Boolean的值，再就是max的定义中肯定也定义了这个函数的参数类型和返回值类型。
这么明显的事为什么不让计算机自己去做而要让人写代码去做呢？这就是匿名函数的好处了。到这里，我们已经和Lambda很接近了。

val sum: (Int, Int) -> Int = { x, y -> x + y }

Lambda表达式就是被大括号括着的那一部分，
在->符号之前有参数声明，函数体跟在一个->符号之后。
而且此Lambda表达式之前有一个匿名的函数声明(在此例中两个Int型的输入，一个Int型的返回值)，
这个声明是可以不使用的。
 则此Lambda表达式变成val sum = { x: Int, y: Int -> x + y }，
 此时Lambda表达式会根据主体中的最后一个（或可能是单个）表达式会视为返回值。
  当然，在某些特定情况下，x、y的类型了是可以推断的，所以val sum = { x, y -> x + y }。

 */