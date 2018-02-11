package com.demo.my.myapplication;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.gson.Gson;

import java.util.List;

/**
 * AccessibilityService可以拦截到系统发出的一些消息（比如窗体状态的改变，通知栏状态的改变，View被点击了等等
 * 使用AccessibilityService之前，需要配置清单文件，并且让Service继承AccessibilityService
 * 使用AccessibilityService必须到辅助功能中打开对应服务，不然无法正常连接
 * 需要权限：<uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
 */
public class MyService extends AccessibilityService {
    private final String TAG = this.getClass().getSimpleName();
    private AccessibilityNodeInfo mRootNodeInfo;

    public MyService() {
//        检测服务是否开启
//
//        介绍了一些AccessibilityService的基础知识之后,再补充一点关于检测某个服务是否开启的知识.通常来说大体有一下两种方法来检测服务是否启用:
//
//        方法一:借助服务管理器AccessibilityManager来判断,但是该方法不能检测app本身开启的服务.
//
//        private boolean enabled(String name) {
//            AccessibilityManager am = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
//            List<AccessibilityServiceInfo> serviceInfos = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
//            List<AccessibilityServiceInfo> installedAccessibilityServiceList = am.getInstalledAccessibilityServiceList();
//            for (AccessibilityServiceInfo info : installedAccessibilityServiceList) {
//                Log.d("MainActivity", "all -->" + info.getId());
//                if (name.equals(info.getId())) {
//                    return true;
//                }
//            }
//            return false;
//        }
//
//
//        既然谈到了AccessibilityManager,那么在这里我们就做个简单的介绍:
//        AccessibilityManager是系统级别的服务,用来管理AccessibilityService服务,比如分发事件,查询系统中服务的状态等等,更多信息参考官方文档,常见方法如下:
//        方法 	说明
//        getAccessibilityServiceList() 	获取服务列表(api 14之后废弃,用下面的方法代替)
//        getInstalledAccessibilityServiceList() 	获取已安装到系统的服务列表
//        getEnabledAccessibilityServiceList(int feedbackTypeFlags) 	获取已启用的服务列表
//        isEnabled() 	判断服务是否启用
//        sendAccessibilityEvent(AccessibilityEvent event) 	发送事件
//
//        方法二:我们知道大部分的系统属性都在settings中进行设置,比如wifi,蓝牙状态等,而这些信息主要是存储在settings对应的的数据库中(system表和serure表),这就意味我们可以通过直接读取setting设置来判断相关服务是否开启:
//
//        private boolean checkStealFeature1(String service) {
//            int ok = 0;
//            try {
//                ok = Settings.Secure.getInt(getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
//            } catch (Settings.SettingNotFoundException e) {
//            }
//
//            TextUtils.SimpleStringSplitter ms = new TextUtils.SimpleStringSplitter(':');
//            if (ok == 1) {
//                String settingValue = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
//                if (settingValue != null) {
//                    ms.setString(settingValue);
//                    while (ms.hasNext()) {
//                        String accessibilityService = ms.next();
//                        if (accessibilityService.equalsIgnoreCase(service)) {
//                            return true;
//                        }
//
//                    }
//                }
//
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
//        在onServiceConnected中可以去配置AccessibilityService的一些信息，
// 也就是之前在xml文件可以在这里通过代码配置，不过这里没法配置canRetrieveWindowContent属性

        // //可用代码配置当前Service的信息
// AccessibilityServiceInfo info = new AccessibilityServiceInfo();
// info.packageNames = new String[]{"com.android.packageinstaller", "com.tencent.mobileqq", "com.trs.gygdapp"}; //监听过滤的包名
// info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK; //监听哪些行为
// info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN; //反馈
// info.notificationTimeout = 100; //通知的时间
// setServiceInfo(info);
        Log.i(TAG, "onServiceConnected: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        //跳转辅助功能，打开服务
        String action = Settings.ACTION_ACCESSIBILITY_SETTINGS;
        this.startActivity(new Intent().setAction(action));
    }

//    com.tencent.mm:id/hy 返回箭头
//    查看红包 com.tencent.mm:id/ae_
//    当我们微信收到通知时，状态栏会有一条推送信息到达，这个时候就会被TYPE_NOTIFICATION_STATE_CHANGED监听，执行里面的内容，当我们切换微信界面时，或者使用微信时，这个时候就会被TYPE_WINDOW_STATE_CHANGED监听，执行里面的内容
//
//            AccessibilityEvent的方法
//
//    getEventType()：事件类型
//
//    getSource()：获取事件源对应的结点信息
//
//    getClassName()：获取事件源对应类的类型，比如点击事件是有某个Button产生的，那么此时获取的就是Button的完整类名
//
//    getText()：获取事件源的文本信息，比如事件是有TextView发出的,此时获取的就是TextView的text属性。如果该事件源是树结构，那么此时获取的是这个树上所有具有text属性的值的集合
//
//    isEnabled()：事件源(对应的界面控件)是否处在可用状态
//
//    getItemCount()：如果事件源是树结构，将返回该树根节点下子节点的数量

//   获取窗口节点（根节点）
//    AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        凡是EditText中设置inputType为password类型的，都无法获取其输入值
        mRootNodeInfo = event.getSource();
        //如果当前的事件类型是窗口内容出现了变化，那么判断是否有红包视图出现

        Log.i(TAG, new Gson().toJson(mRootNodeInfo).toString());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            List<AccessibilityNodeInfo> hongbaoList = mRootNodeInfo.findAccessibilityNodeInfosByText("微信红包");
            List<AccessibilityNodeInfo> weikaiList = mRootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ae_");
            List<AccessibilityNodeInfo> back = mRootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/hy");
            for (AccessibilityNodeInfo accessibilityNodeInfo : back) {
                accessibilityNodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            Log.e(TAG, "发现红包检测数量 : " + hongbaoList.size());
            for (int i = 0; i < weikaiList.size(); i++) {
//                if (weikaiList.get(i).getText().equals("领取红包")) {
//                    AccessibilityNodeInfo curNodeInfo = weikaiList.get(i);
//                    curNodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                }
                if (weikaiList.get(i).getText().equals("查看红包")) {
                    AccessibilityNodeInfo curNodeInfo = weikaiList.get(i);
                    curNodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.i(TAG, "onInterrupt: ");
    }
}
