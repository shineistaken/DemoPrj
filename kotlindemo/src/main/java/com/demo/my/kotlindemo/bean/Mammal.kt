package com.demo.my.kotlindemo.bean

/**
 * Created by lenovo on 2018/2/8.
 */
// todo 抽象类
//类和其中的某些成员可以声明为abstract。抽象成员在本类中可以不用实现。
// 需要注意的是，我们并不需要用open标注一个抽象类或者函数——因为这不言而喻。
//
//我们可以用一个抽象成员覆盖一个非抽象的开放成员:
abstract class Mammal : Animal() {
    override fun move() {
        super.move()
        super.breath()
    }
}