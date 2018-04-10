package com.demo.my.kotlindemo.grammer

import android.util.Log

/**
 *todo  函数作为返回值
* Created by lenovo on 2018/2/12.
 */
class DeliveryCaculator {
    val TAG = this.javaClass.simpleName

//    函数作为返回值也非常实用，例如我们的需求是根据不同的快递类型返回不同计价公式，
// 普通快递和高级快递的计价规则不一样，
// 这时候我们可以将计价规则函数作为返回值：

    enum class Delivery {
        STANDARD, EXPEDITED
    }

    fun getShippingCostCalculator(delivery: Delivery): (Int) -> Double {
        if (delivery == Delivery.EXPEDITED) {
            return { 6 + 2.1 * it }
        } else {
            return { i -> 1.6 * i }
        }
    }

    fun test() {
        val shippingCostCalculator_EXPEDITED = getShippingCostCalculator(Delivery.EXPEDITED)
        val shippingCostCalculator_STANDARD = getShippingCostCalculator(Delivery.STANDARD)
        Log.i(TAG,"Expedited: ${shippingCostCalculator_EXPEDITED(6)}" )
        Log.i(TAG,"Standard: ${shippingCostCalculator_STANDARD(6)}" )
    }
}