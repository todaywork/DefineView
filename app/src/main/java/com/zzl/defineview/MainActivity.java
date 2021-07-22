package com.zzl.defineview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.zzl.defineview.cookie.CookieJarImpl;
import com.zzl.defineview.cookie.PersistentCookieStore;
import com.zzl.defineview.priorityqueue.TestSortManager;

import java.lang.reflect.Field;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
        MyView myView = new MyView(this);
        layout.addView(myView);
        MemorySizeCalculator build = new MemorySizeCalculator.Builder(this).build();
        int bitmapPoolSize = build.getBitmapPoolSize();
        int memoryCacheSize = build.getMemoryCacheSize();
        Field f = null;
        try {
            f = mRunnable.getClass().getDeclaredField("this$0");
        } catch (NoSuchFieldException e) {

        }
        Log.d(TAG, "onCreate " + f);
        f.setAccessible(true);
        try {
            f.set(mRunnable, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //cookie使用
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(MainActivity.this)));
        //        TaskSnapshotController taskSnapshotController = new TaskSnapshotController();
//        if (android.os.Build.VERSION.SDK_INT >= 29) {
//            SurfaceControl mysurface = new SurfaceControl.Builder().setName("mysurface").build();
//
//        }
        TestSortManager.getInstance().initData();
        TestSortManager.getInstance().printSortResult();
        //test3git
        MyView myView2 = new MyView(this);
        MyView myView3 = new MyView(this);

        Log.d(TAG, "onCreate branch1 再次修改");
        Toast.makeText(MainActivity.this, "test branch1", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate branch2");
        Log.d(TAG, "onCreate branch`1");

    }
    private Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "run branch2");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        Log.d(TAG, "onWindowAttributesChanged ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //最终会调用windowmanage的updaviewlayout界面刷新。

    }


    public void click(View view) {
        startActivity(new Intent(this, MainActivity2.class));
    }


}