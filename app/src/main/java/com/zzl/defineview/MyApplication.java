package com.zzl.defineview;

import android.app.Application;
import android.util.Log;

/**
 * Created by zhenglin.zhu on 2021/6/14.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "onTrimMemory leve="+level);
        switch (level){
            case TRIM_MEMORY_UI_HIDDEN:
                Log.d(TAG, "onTrimMemory ");

                break;
        }
    }
}
