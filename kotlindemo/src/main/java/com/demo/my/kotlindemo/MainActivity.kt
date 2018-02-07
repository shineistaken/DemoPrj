package com.demo.my.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.demo.my.kotlindemo.bean.Person

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        //创建对象
        val testPerson = Person("Alex", 16, null)

    }

}
