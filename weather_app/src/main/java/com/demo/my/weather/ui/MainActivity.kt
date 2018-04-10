package com.demo.my.weather.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.demo.my.weather.R
import com.demo.my.weather.commands.RequestForecastCommand
import com.demo.my.weather.extensions.DelegatesExt
import com.demo.my.weather.model.ForecastList
import com.demo.my.weather.model.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar: Toolbar
            // NOTE : 2018/3/16  by  lenovo  : 初始化一个View可以使用的方法
//       get() =  tb   //方法1.重写get方法
//            by lazy {find<Toolbar>(R.id.tb) } //方法2 使用anko的拓展方法
            by lazy { tb } // 方法3 改用懒加载的方式初始化toolba


    private val zipCode: Long by DelegatesExt.sp(this, SettingActivity.ZIP_CODE, SettingActivity.DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //接口方法具体实现在ToolbarManager中，这里直接调用了接口的方法
        initToolBar()
        initRecylcer()
        attachToScroll(recycler)
    }

    override fun onResume() {
        super.onResume()
        loadForcastData()
    }


    // 异步加载天气数据
    private fun loadForcastData() = async(kotlinx.coroutines.experimental.android.UI) {
        //        toast("请求网络")

//        val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
//        val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
//        val COMPLETE_URL = "$URL&APPID=$APP_ID&zip="
//
//        val result = bg { java.net.URL(COMPLETE_URL + zipCode).readText() }
        val result = bg { RequestForecastCommand(zipCode).execute() }
        updateUI(result.await())
    }

    //返回主线程更新UI数据
    private fun updateUI(weekForecast: ForecastList) {
        val listAdapter = ForecastListAdapter(weekForecast) {
            startActivity<DetailActivity>(DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to weekForecast.city)
        }
        recycler.adapter = listAdapter
        toolbarTitle = "(${weekForecast.country}) ${weekForecast.city} "
    }

    private fun initRecylcer() {
        recycler.layoutManager = LinearLayoutManager(this)
    }
}



