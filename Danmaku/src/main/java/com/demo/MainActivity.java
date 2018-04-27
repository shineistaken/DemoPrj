package com.demo;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Danmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.util.IOUtils;
import master.flame.danmaku.ui.widget.DanmakuView;

public class MainActivity extends AppCompatActivity {

    private DanmakuView danmaku_view;
    private Drawable mDrawable;
    private BaseDanmakuParser mParser;
    private DanmakuContext danmakuContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // NOTE : 2018/4/27  by  lenovo  : 1.初始化danmakuView 包含DanmakuTextureView 、DanmakuSurfaceView等多种可选
        danmaku_view = findViewById(R.id.danmaku_view);

        // NOTE : 2018/4/27  by  lenovo  : 2. 创建DanmakuContext，并进行相关配置
        danmakuContext = DanmakuContext.create();
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3f)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
                .setMaximumLines(getMaximumLines())
                .preventOverlapping(getPreventOverlapping())
                // NOTE : 2018/4/27  by  lenovo  : 3. 配置CacheStuffer 和 CacheStufferAdapter
                .setCacheStuffer(new SpannedCacheStuffer(), new BaseCacheStuffer.Proxy() {
                    @Override
                    public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
                        if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
                            // FIXME 这里只是简单启个线程来加载远程url图片，请使用你自己的异步线程池，最好加上你的缓存池
                            new Thread() {

                                @Override
                                public void run() {
                                    String url = "http://www.bilibili.com/favicon.ico";
                                    InputStream inputStream = null;
                                    Drawable drawable = mDrawable;
                                    if (drawable == null) {
                                        try {
                                            URLConnection urlConnection = new URL(url).openConnection();
                                            inputStream = urlConnection.getInputStream();
                                            drawable = BitmapDrawable.createFromStream(inputStream, "bitmap");
                                            mDrawable = drawable;
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } finally {
                                            IOUtils.closeQuietly(inputStream);
                                        }
                                    }
                                    if (drawable != null) {
                                        drawable.setBounds(0, 0, 100, 100);
                                        SpannableStringBuilder spannable = createSpannableDanmakuText(drawable);
                                        danmaku.text = spannable;
                                        if (danmaku_view != null) {
                                            danmaku_view.invalidateDanmaku(danmaku, false);
                                        }
                                        return;
                                    }
                                }
                            }.start();
                        }
                    }

                    @Override
                    public void releaseResource(BaseDanmaku danmaku) {
                        // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
                    }
                });

        if (danmaku_view != null) {
            // NOTE : 2018/4/27  by  lenovo  : 4. 创建弹幕解析器
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));

            // NOTE : 2018/4/27  by  lenovo  : 5. 设置DanmakuView的数据解析器、状态回调、点击事件监听、是否显示帧率、是否开启缓存等
            danmaku_view.prepare(mParser, danmakuContext);
            danmaku_view.showFPS(true);
            danmaku_view.enableDanmakuDrawingCache(true);
            danmaku_view.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
//                    Log.d("DFM", "danmakuShown(): text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    Toast.makeText(MainActivity.this, " danmaku_view.start()", Toast.LENGTH_SHORT).show();
                    // NOTE : 2018/4/27  by  lenovo  : 6. 始化完成后调用 DanmakuView.start(),开启DanmakuView，并绑定DanmakuView的其他生命周期到Activity/Fragment
                    danmaku_view.start();
                }
            });
            danmaku_view.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {

                @Override
                public boolean onDanmakuClick(IDanmakus danmakus) {
                    Log.d("DFM", "onDanmakuClick: danmakus size:" + danmakus.size());
                    BaseDanmaku latest = danmakus.last();
                    if (null != latest) {
                        Log.d("DFM", "onDanmakuClick: text of latest danmaku:" + latest.text);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onDanmakuLongClick(IDanmakus danmakus) {
                    return false;
                }

                @Override
                public boolean onViewClick(IDanmakuView view) {
//                    mMediaController.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }
//
//        if (mVideoView != null) {
//            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    mediaPlayer.start();
//                }
//            });
//            mVideoView.setVideoPath(Environment.getExternalStorageDirectory() + "/1.flv");
//        }

    }

    // NOTE : 2018/4/27  by  lenovo  : 绑定生命周期
    @Override
    protected void onResume() {
        super.onResume();
        if (danmaku_view != null && danmaku_view.isPrepared() && danmaku_view.isPaused())
            danmaku_view.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmaku_view != null && danmaku_view.isPrepared())
            danmaku_view.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        danmaku_view.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        danmaku_view.release();
        danmaku_view = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        danmaku_view.restart();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        danmaku_view.start();
    }

    // NOTE : 2018/4/27  by  lenovo  : 创建解析器
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    // NOTE : 2018/4/27  by  lenovo  : 添加Danmaku
    private SpannableStringBuilder createSpannableDanmakuText(Drawable drawable) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ImageSpan span = new ImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("这是一条图文混排弹幕");
        spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#F92672")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }

    /**
     * 设置是否禁止重叠
     *
     * @return
     */
    private Map<Integer, Boolean> getPreventOverlapping() {
        Map<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        return null;
    }

    /**
     * 设置最大显示行数
     *
     * @return
     */
    private Map<Integer, Integer> getMaximumLines() {
        HashMap<Integer, Integer> mMaximumLines = new HashMap<>();
        // 滚动弹幕最大显示5行
        mMaximumLines.put(BaseDanmaku.TYPE_SCROLL_RL, 5);
        return mMaximumLines;
    }
}
