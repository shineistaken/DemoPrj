package com.demo.my.kotlindemo.grammer

/**
 *
 * Created by lenovo on 2018/2/8.
 */
/*

todo Kotlin用到的关键字

    var：定义变量
    val：定义常量
    fun：定义方法
    Unit：默认方法返回值，类似于Java中的void，可以理解成返回没什么用的值
    vararg：可变参数
    $：字符串模板(取值)
    位运算符：or(按位或)，and(按位与)，shl(有符号左移)，shr(有符号右移)，
    ushr(无符号右移)，xor(按位异或)，inv(按位取反)
    in：在某个范围中 检查值是否在或不在(in/!in)范围内或集合中
    downTo：递减，循环时可用，每次减1
    step:步长，循环时可用，设置每次循环的增加或减少的量
    when:Kotlin中增强版的switch，可以匹配值，范围，类型与参数
    is：判断类型用，类似于Java中的instanceof()，is运算符检查表达式是否是类型的实例。 如果一个不可变的局部变量或属性是指定类型，则不需要显式转换
    private仅在同一个文件中可见
    protected同一个文件中或子类可见
    public所有调用的地方都可见
    internal同一个模块中可见
    abstract抽象类标示
    final标示类不可继承，默认属性
    enum标示类为枚举
    open类可继承，类默认是final的
    annotation注解类
    init主构造函数不能包含任何的代码。初始化的代码可以放到以init关键字作为前缀的初始化块(initializer blocks)中
    field只能用在属性的访问器内。特别注意的是，get set方法中只能能使用filed。属性访问器就是get set方法。
    :用于类的继承，变量的定义
    ..围操作符(递增的) 1..5，2..6千万不要6..2
    ::作用域限定符
    inner类可以标记为inner {: .keyword }以便能够访问外部类的成员。内部类会带有一个对外部类的对象的引用
    object对象声明并且它总是在object{: .keyword }关键字后跟一个名称。对象表达式：在要创建一个继承自某个(或某些)类型的匿名类的对象会用到

todo 可null类型

因为在Kotlin中一切都是对象，一切都是可null的。当某个变量的值可以为null的时候，
 必须在声明处的类型后添加?来标识该引用可为空。 Kotlin通过?将是否允许为空分割开来，
 比如str:String为不能空，加上?后的str:String?为允许空，通过这种方式，将本是不能确定的变量人为的加入了限制条件。而不符合条件的输入，则会在IDE上显示编译错误而无法执行。

var value1 : String = "abc"
value1 = null        // 编译不错误

var value2 : String? = "abc"
value2 = null       // 编译能通过

在对变量进行操作时，如果变量是可能为空的，那么将不能直接调用，因为编译器不知道你的变量是否为空，所以编译器就要求你一定要对变量进行判断

var str : String? = null
str.length    // 编译不错误
str?.length   // 编译能通过

那么问题来了，我们知道在java中String.length返回的是int，上面的str?.length既然编译通过了，那么它返回了什么？我们可以这么写:

var result = str?.length

这么写编译器是能通过的，那么result的类型是什么呢？在Kotlin中，编译器会自动根据结果判断变量的类型，翻译成普通代码如下:

if(str == null)
result = null;            // 这里result为一个引用类型
else
result = str.length;    // 这里result为Int

那么如果我们需要的就是一个Int的结果(事实上大部分情况都是如此)，那又该怎么办呢？在kotlin中除了?表示可为空以外，还有一个新的符号:双感叹号!!，表示一定不能为空。所以上面的例子，如果要对result进行操作，可以这么写:

var str : String? = null
var result : Int = str!!.length

这样的话，就能保证result的数据类型，但是这样还有一个问题，那就是str的定义是可为空的，上面的代码中，str就是空，这时候下面的操作虽然不会报编译异常，但是运行时就会见到我们熟悉的空指针异常NullPointerExectpion，这显然不是我们希望见到的，也不是kotlin愿意见到的。java中的三元操作符大家应该都很熟悉了，kotlin中也有类似的，它很好的解决了刚刚说到的问题。在kotlin中，三元操作符是?:，写起来也比java要方便一些。

var str : String? = null
var result = str?.length ?: -1
//等价于
var result : Int = if(str != null) str.length else -1

if null缩写

val data = ……
val email = data["email"] ?: throw IllegalStateException("Email is missing!")

表达式
 todo if表达式

在Kotlin中，if是一个表达式，即它会返回一个值。因此就不需要三元运算符条件 ? 然后 : 否则，因为普通的if就能胜任这个角色。 if的分支可以是代码块，最后的表达式作为该块的值:

val max = if (a > b) {
    print("Choose a")
    a
} else {
    print("Choose b")
    b
}

todo when表达式

when表达式与Java中的switch/case类似，但是要强大得多。这个表达式会去试图匹配所有可能的分支直到找到满意的一项。然后它会运行右边的表达式。 与Java的switch/case不同之处是参数可以是任何类型，并且分支也可以是一个条件。

对于默认的选项，我们可以增加一个else分支，它会在前面没有任何条件匹配时再执行。条件匹配成功后执行的代码也可以是代码块:

when (x){
    1 -> print("x == 1")
    2 -> print("x == 2")
    else -> {
        print("I'm a block")
        print("x is neither 1 nor 2")
    }
}

因为它是一个表达式，它也可以返回一个值。我们需要考虑什么时候作为一个表达式使用，它必须要覆盖所有分支的可能性或者实现else分支。否则它不会被编译成功:

val result = when (x) {
    0, 1 -> "binary"
    else -> "error"
}

如你所见，条件可以是一系列被逗号分割的值。但是它可以更多的匹配方式。比如，我们可以检测参数类型并进行判断:

when(view) {
    is TextView -> view.setText("I'm a TextView")
    is EditText -> toast("EditText value: ${view.getText()}")
    is ViewGroup -> toast("Number of children: ${view.getChildCount()} ")
    else -> view.visibility = View.GONE
}

todo for循环

val items = listOf("apple", "banana", "kiwi")
for (item in items) {
    println(item)
}

for (i in array.indices)
print(array[i])

todo 使用类型检测及自动类型转换

is运算符检测一个表达式是否某类型的一个实例。 如果一个不可变的局部变量或属性已经判断出为某类型，那么检测后的分支中可以直接当作该类型使用，无需显式转换:

fun getStringLength(obj: Any): Int? {
    if (obj !is String) return null

    // `obj` 在这一分支自动转换为 `String`
    return obj.length
}

todo 返回和跳转

Kotlin有三种结构化跳转表达式:

return:默认从最直接包围它的函数或者匿名函数返回。
break:终止最直接包围它的循环。
continue:继续下一次最直接包围它的循环。

在Kotlin中任何表达式都可以用标签label来标记。标签的格式为标识符后跟@符号，例如:abc@、fooBar@都是有效的标签。

要为一个表达式加标签，我们只要在其前加标签即可。

loop@ for (i in 1..100) {
    for (j in 1..100) {
        if (……) break@loop
    }
}

todo Ranges

Range表达式使用一个..操作符。

if(i >= 0 && i <= 10)
println(i)

转换成

if (i in 0..10)
println(i)

Ranges默认会自增长，所以如果像以下的代码：

for (i in 10..0)
println(i)

它就不会做任何事情。但是你可以使用downTo函数：

for(i in 10 downTo 0)
println(i)

我们可以在Ranges中使用step来定义一个从1到一个值的不同的空隙:

for (i in 1..4 step 2) println(i)
for (i in 4 downTo 1 step 2) println(i)

*/