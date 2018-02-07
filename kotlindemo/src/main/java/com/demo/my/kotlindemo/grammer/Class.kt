package com.demo.my.kotlindemo.grammer

/**
 * Created by lenovo on 2018/2/8.
 */

/*
todo 嵌套类

嵌套类顾名思义，就是嵌套在其他类中的类。而嵌套类外部的类一般被称为包装类或者外部类。

class Outter{
    class Nested{
        fun execute(){
            Log.d("test", "Nested -> execute")
        }
    }
}

// 调用
Outter.Nested().execute()

//输出
Nested -> execute

嵌套类可以直接创建实例，方式是包装类.嵌套类 val nested : Outter.Nested()

todo 内部类

内部类和嵌套类有些类似，不同点是内部类用关键字inner修饰。

class Outter{
    val testVal = "test"
    inner class Inner{
        fun execute(){
            Log.d("test", "Inner -> execute : can read testVal=$testVal")
        }
    }
}

// 调用
val outter = Outter()
outter.Inner().execute()

// 输出
Inner -> execute : can read testVal=test

内部类不能直接创建实例，需要通过外部类调用

val outter = Outter()
outter.Inner().execute()

todo 匿名内部类

text.setOnClickListener(object : View.OnClickListener{
    override fun onClick(p0: View?) {
        Log.d("test", p0.string())
    }
})

todo 枚举

enum class Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY
}

枚举可以带参数:

enum class Icon(val res: Int) {
    UP(R.drawable.ic_up),
    SEARCH(R.drawable.ic_search),
    CAST(R.drawable.ic_cast)
}

val searchIconRes = Icon.SEARCH.res

枚举可以通过String匹配名字来获取，我们也可以获取包含所有枚举的Array，所以我们可以遍历它。

val search: Icon = Icon.valueOf("SEARCH")
val iconList: Array<Icon> = Icon.values()

而且每一个枚举都有一些函数来获取它的名字、声明的位置：

val searchName: String = Icon.SEARCH.name()
val searchPosition: Int = Icon.SEARCH.ordinal()

todo 异常

在Kotlin中，所有的Exception都是实现了Throwable，含有一个message且未经检查。这表示我们不会强迫我们在任何地方使用try/catch。这与Java中不太一样，比如在抛出IOException的方法，我们需要使用try-catch包围代码块。但是通过检查exception来处理显示并不是一个好的方法。

抛出异常的方式与Java很类似：

throw MyException("Exception message")

try表达式也是相同的：

try{
    // 一些代码
}
catch (e: SomeException) {
    // 处理
}
finally {
    // 可选的finally块
}

在Kotlin中，throw和try都是表达式，这意味着它们可以被赋值给一个变量。这个在处理一些边界问题的时候确实非常有用:

val s = when(x){
    is Int -> "Int instance"
    is String -> "String instance"
    else -> throw UnsupportedOperationException("Not valid type")
}

或者

val s = try { x as String } catch(e: ClassCastException) { null }

todo 单例

object Resource {
    val name = "Name"
}

委托(代理)
todo 类委托

委托模式是最常用的设计模式的一种，在委托模式中，有两个对象参与处理同一个请求，接受请求的对象将请求委托给另一个对象来处理。kotlin中的委托可以算是对委托模式的官方支持。 Kotlin直接支持委托模式，更加优雅，简洁。Kotlin通过关键字by实现委托。

interface Base{
    fun print()
}

class BaseImpl(val x : Int) : Base{
    override fun print() {
        Log.d(JTAG, "BaseImpl -> ${x.string()}")
    }
}

class Printer(b : Base) : Base by b

fun test(){
    val b = BaseImpl(5)
    Printer(b).print()
}

// 输出
BaseImpl -> 5

可以看到Printer类没有实现接口Base的方法print()，而是通过关键字by将实现委托给了b，而输出也和预想的一样。
todo 属性委托

语法是val/var <属性名>: <类型> by <表达式>。在by后面的表达式是该委托，因为属性对应的get()和set()会被委托给它的getValue()和setValue()方法。 属性的委托不必实现任何的接口，但是需要提供一个getValue()函数（和setValue()——对于`var属性）。

class Example {
    var property : String by DelegateProperty()
}

class DelegateProperty {
    var temp = "old"

    operator fun getValue(ref: Any?, p: KProperty<*>): String {
        return "DelegateProperty --> ${p.name} --> $temp"
    }

    operator fun setValue(ref: Any?, p: KProperty<*>, value: String) {
        temp = value
    }
}

fun test(){
    val e = Example()
    Log.d(JTAG, e.property)
    e.property = "new"
    Log.d(JTAG, e.property)
}

// 输出
DelegateProperty --> property --> old
DelegateProperty --> property --> new

像上面的DelegateProperty这样，被一个属性委托的类，我叫它被委托类，委托它的属性叫委托属性。其中:

    如果委托属性是只读属性即val，则被委托类需要实现getValue方法
    如果委托属性是可变属性即var，则被委托类需要实现getValue方法和setValue方法
    getValue方法的返回类型必须是与委托属性相同或是其子类
    getValue方法和setValue方法必须要用关键字operator标记

Kotlin通过属性委托的方式，为我们实现了一些常用的功能，包括:

    延迟属性lazy properties
    可观察属性observable properties
    map映射

todo 延迟属性

延迟属性我们应该不陌生，也就是通常说的懒汉，在定义的时候不进行初始化，把初始化的工作延迟到第一次调用的时候。kotlin中实现延迟属性很简单，来看一下。

val lazyValue: String by lazy {
    Log.d(JTAG, "Just run when first being used")
    "value"
}

fun test(){
    Log.d(JTAG, lazyValue)
    Log.d(JTAG, lazyValue)
}

// 输出
Just run when first being used
value
value

可以看到，只有第一次调用了lazy里的日志输出，说明lazy方法块只有第一次执行了。按照个人理解，上面的lazy模块可以这么翻译

String lazyValue;
String getLazyValue(){
    if(lazyValue == null){
        Log.d(JTAG, "Just run when first being used");
        lazyValue = "value";
    }
    return lazyValue;
}

void test(){
    Log.d(JTAG, getLazyValue());
    Log.d(JTAG, getLazyValue());
}

todo 可观察属性

可观察属性对应的是我们常用的观察者模式，机制类似于我们给View增加Listener。同样的kotlin给了我们很方便的实现:

class User {
    var name: Int by Delegates.observable(0) {
        prop, old, new -> Log.d(JTAG, "$old -> $new")
    }

    var gender: Int by Delegates.vetoable(0) {
        prop, old, new -> (old < new)
    }
}

fun test(){
    val user = User()
    user.name = 2    // 输出 0 -> 2
    user.name = 1   // 输出 2 -> 1

    user.gender = 2
    Log.d(JTAG, user.gender.string())   // 输出 2
    user.gender = 1
    Log.d(JTAG, user.gender.string())   // 输出 2
}

Delegates.observable()接受两个参数:初始值和修改时处理程序handler。 每当我们给属性赋值时会调用该处理程序（在赋值后执行）。 它有三个参数：被赋值的属性、旧值和新值。在上面的例子中，我们对user.name赋值，set变化触发了观察者，执行了Log.d代码段。

除了Delegates.observable()之外，我们还把gender委托给了Delegates.vetoable(),和observable不同的是，observable是执行了set变化之后，才触发observable,而vetoable则是在set执行之前被触发，它返回一个Boolean，如果为true才会继续执行set。在上面的例子中，我们看到在第一次赋值user.gender = 2时，由于2>0，所以old<new判断成立，所以执行了set方法，gender为2,第二次赋值user.gender = 1则没有通过判断，所以gender依然为2。
map映射

一个常见的用例是在一个映射map里存储属性的值。这经常出现在像解析JSON或者做其他“动态”事情的应用中。在这种情况下，你可以使用映射实例自身作为委托来实现委托属性。

class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}
// 在这个例子中，构造函数接受一个映射参数
val user = User(mapOf(
    "name" to "John Doe",
    "age"  to 25
))
委托属性会从这个映射中取值（通过字符串键——属性的名称）
println(user.name) // Prints "John Doe"
println(user.age)  // Prints 25
这也适用于var属性，如果把只读的Map换成MutableMap的话
class MutableUser(val map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int     by map
}

todo 反射

反射是这样的一组语言和库功能，它允许在运行时自省你的程序的结构。
 Kotlin让语言中的函数和属性做为一等公民、并对其自省（即在运行时获悉一个名称或者一个属性或函数的类型）与简单地使用函数式或响应式风格紧密相关。

在Java平台上，使用反射功能所需的运行时组件作为单独的JAR文件(kotlin-reflect.jar)分发。这样做是为了减少不使用反射功能的应用程序所需的运行时库的大小。如果你需要使用反射，请确保该.jar文件添加到项目的classpath中。

todo 类引用

最基本的反射功能是获取Kotlin类的运行时引用。要获取对静态已知的Kotlin类的引用，可以使用类字面值语法:

val c = MyClass::class

该引用是KClass类型的值。 通过使用对象作为接收者，可以用相同的::class语法获取指定对象的类的引用:

val widget: Widget = ……
assert(widget is GoodWidget) { "Bad widget: ${widget::class.qualifiedName}" }

当我们有一个命名函数声明如下:

fun isOdd(x: Int) = x % 2 != 0

我们可以很容易地直接调用它isOdd(5)，但是我们也可以把它作为一个值传递。例如传给另一个函数。为此我们使用::操作符:

val numbers = listOf(1, 2, 3)
println(numbers.filter(::isOdd)) // 输出 [1, 3]

todo 扩展

扩展是kotlin中非常重要的一个特性，它能让我们对一些已有的类进行功能增加、简化，使他们更好的应对我们的需求。

//  对Context的扩展，增加了toast方法。为了更好的看到效果，我还加了一段log日志
fun Context.toast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    Log.d("text", "Toast msg : $msg")
}

// Activity类，由于所有Activity都是Context的子类，所以可以直接使用扩展的toast方法
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ......
        toast("hello, Extension")
    }
}

// 输出
Toast msg : hello, Extension

按照通常的做法，会写一个ToastUtils工具类，或者在BaseActivity中实现toast方法，但是现在这些统统不需要了，
直接给Context增加扩展，这样不论是Activity或者Service都可以直接调用，更简洁、更优雅。接下来是我的学习进程，希望也能对你有所帮助。
todo 扩展函数

扩展函数是指对类的方法进行扩展，写法和定义方法类似，但是要声明目标类，也就是对哪个类进行扩展，kotlin中称之为Top Level。
 扩展函数表现得就像是属于这个类的一样，而且我们可以使用this关键字和调用所有public方法。
  扩展函数可以在已有类中添加新的方法，不会对原类做修改，扩展函数定义形式:

fun receiverType.functionName(params){
    body
}
receiverType：表示函数的接收者，也就是函数扩展的对象
functionName：扩展函数的名称
params：扩展函数的参数，可以为NULL

在上面我们举的扩展的例子就是扩展函数.其中Context就是目标类Top Level，我们把它放到方法名前，用点.表示从属关系。在方法体中用关键字this对本体进行调用。和普通方法一样，如果有返回值，在方法后面跟上返回类型，我这里没有返回值，所以直接省略了。
todo 扩展属性

扩展属性和扩展方法类似，是对目标类的属性进行扩展。扩展属性也会有set和get方法，并且要求实现这两个方法，不然会提示编译错误。 因为扩展并不是在目标类上增加了这个属性，所以目标类其实是不持有这个属性的，我们通过get和set对这个属性进行读写操作的时候也不能使用field指代属性本体。可以使用this，依然表示的目标类。

// 扩展了一个属性paddingH
var View.panddingH : Int
    get() = (paddingLeft + paddingRight) / 2
    set(value) {
        setPadding(value, paddingTop, value, paddingBottom)
    }

// 设置值
text.panddingH = 100

给View扩展了一个属性paddingH，并给属性增加了set和get方法，然后可以在activity中通过textview调用。
todo 静态扩展

kotlin中的静态用关键字companion表示，但是它不是修饰属性或方法，而是定义一个方法块，在方法块中的所有方法和属性都是静态的，这样就将静态部分统一包装了起来。 静态部分的访问和java一致，直接使用类名+静态属性/方法名调用。

// 定义静态部分
class Extension {
    companion object part{
        var name = "Extension"
    }
}

// 通过类名+属性名直接调用
toast("hello, ${Extension.name}")

// 输出
Toast msg : hello, Extension

上面例子中，companion object一起是修饰关键字，part是方法块的名称。其中方法块名称part可以省略，如果省略的话，默认缺省名为Companion

静态的扩展和普通的扩展类似，但是在目标类要加上静态方法块的名称，所以如果我们要对一个静态部分扩展，就要先知道静态方法块的名称才行。

class Extension {
    companion object part{
        var name = "Extension"
    }
}

// part为静态方法块名称
fun Extension.part.upCase() : String{
    return name.toUpperCase()
}

// 调用一下
toast("hello, ${Extension.name}")
toast("hello, ${Extension.upCase()}")

//输出
Toast msg : hello, Extension
Toast msg : hello, EXTENSION
 */