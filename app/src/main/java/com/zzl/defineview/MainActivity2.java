package com.zzl.defineview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        Log.d(TAG, "onCreate ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume ");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

    }


    @Override
    protected void onPause() {
        super.onPause();
        //android8.0之后 设置该标志有效果。8.0之前没有效果
        Log.d(TAG, "onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mHomeKeyEventReceiver);
        Log.d(TAG, "onDestroy ");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow ");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEvent "+event);
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        Thread thread = Thread.currentThread();
        try {
            thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onWindowAttributesChanged(params);
        Log.d(TAG, "onWindowAttributesChanged ");
    }
    /**
     * 该方法是按下home键必经的方法
     */
    @Override
    protected void onUserLeaveHint() {
        //按返回键，模拟home按键
        super.onUserLeaveHint();
        Log.d(TAG, "onUserLeaveHint ");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed ");
    }

    public void click(View view) {
        Log.d(TAG, "click ");
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().getDecorView().postDelayed(() ->{
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        },10);

    }

    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) { // 监听home键
                String reason = intent.getStringExtra(SYSTEM_REASON);
                Log.d(TAG, "onReceive "+action);

                // 表示按了home键,程序到了后台
            }
        }
    };

}