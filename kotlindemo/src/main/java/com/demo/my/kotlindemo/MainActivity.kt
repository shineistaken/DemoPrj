package com.demo.my.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.demo.my.kotlindemo.R.layout.activity_main
import com.demo.my.kotlindemo.bean.Person
import com.demo.my.kotlindemo.extension.tip

class MainActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
//    标记为override的成员本身是开放的，也就是说，它可以在子类中覆盖。如果你想禁止再次覆盖，可以使用final关键字:
//
//   final override fun onCreate(savedInstanceState: Bundle?) {...}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val tv = findViewById<TextView>(R.id.tv)

//        使用Android Extension后直接使用控件,不需要findViewById
//        tv.setText("go")


//        createObject()
        tip("From Extension!")
    }

    private fun createObject() {
        // todo 创建对象
//        通过主构造函数创建对象
//        val testPerson = Person("Alex", 16, null)

//        通过其他构造函数创建对象
        val testPerson = Person(15)

//        拷贝并且只修改一部分值
//        val copy = testPerson.copy(sex = "Male")

//        toast方法中给length指定了一个默认值。这意味着你调用的时候可以传入第二个值或者不传，这样可以避免你需要的重载函数：
//        toast("create Person",Toast.LENGTH_LONG)
        toast("create Person: ${testPerson.name};${testPerson.age};${testPerson.sex};")

//       todo 多声明
//
//        多声明，也可以理解为变量映射，这就是编译器自动生成的componentN()方法。
        val person = Person("male")
        //这里log输出 no one,即Person中定义的第一个变量的值，person.name
        Log.i(TAG, person.component1())

        var (name, Age, sex) = person
        //等价于:
//        var name = person.component1()
//        var Age = person.component2()
//        var sex=person.component3()
        Log.i(TAG, "name: $name,age: $Age,sex:$sex")
    }

    //  todo 指定参数默认值
    // 可以给参数指定一个默认值使得它们变得可选
    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, length).show()
    }

}
