package net.cs.androidbaseapplication;

import android.app.Application;
import android.os.Environment;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;

import net.cs.appbaselibrary.utils.ToastUtils;

/**
 * Created by lenovo on 2017/1/3.
 */

public class AppConfig extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initLogger();
    }

    /**
     * Logger日志初始化
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .isSaveToFile(true)
                .versionName("_999_")
                .showThreadInfo(false)  // 线程信息
                .fileFolder(Environment.getExternalStorageDirectory().getAbsolutePath() +"/123456/")
                .methodCount(1)         // 方法堆栈数
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("LotMasterLog")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
//                return BuildConfig.DEBUG;
                return true;
            }
        });
    }
    private void init(){
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }

        ToastUtils.init(this);

    }
}
