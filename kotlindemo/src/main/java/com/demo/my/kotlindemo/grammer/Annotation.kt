package com.demo.my.kotlindemo.grammer

/**
 * Created by lenovo on 2018/2/8.
 */

/*
todo 注解

注解是将元数据附加到代码的方法。要声明注解，请将annotation修饰符放在类的前面:

annotation class Fancy

注解的附加属性可以通过用元注解标注注解类来指定:

    @Target指定可以用该注解标注的元素的可能的类型(类、函数、属性、表达式等)
    @Retention指定该注解是否存储在编译后的class文件中，以及它在运行时能否通过反射可见(默认都是true)
    @Repeatable允许在单个元素上多次使用相同的该注解
    @MustBeDocumented指定该注解是公有API的一部分，并且应该包含在生成的API文档中显示的类或方法的签名中

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Fancy

用法

@Fancy class Foo {
    @Fancy fun baz(@Fancy foo: Int): Int {
        return (@Fancy 1)
    }
}

如果需要对类的主构造函数进行标注，则需要在构造函数声明中添加constructor关键字，并将注解添加到其前面:

class Foo @Inject constructor(dependency: MyDependency) {
    // ……
}
 */