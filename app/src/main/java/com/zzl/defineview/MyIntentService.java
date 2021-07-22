package com.zzl.defineview;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * Created by zhenglin.zhu on 2021/6/14.
 */
public class MyIntentService extends IntentService {
    /**
     * @param name
     * @deprecated
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //处理耗时任务的，执行完成后会自动销毁服务
    }
}
