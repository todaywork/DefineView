package com.zzl.defineview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by zhenglin.zhu on 2021/5/23.
 */
public class MyView extends LinearLayout {
    private static final String TAG = "MyView";
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private TextView mTextView;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.my_view, this);
        mTextView = (TextView) view.findViewById(R.id.text);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//这个函数获取view的宽高
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();

        Log.d(TAG, "onMeasure mMeasuredWidth="+mMeasuredWidth+" ,mMeasuredHeight="+mMeasuredHeight
        /*+" ,childviewheight="+mTextView.getMeasuredHeight()+" ,childviewwidth="+mTextView.getMeasuredWidth()*/);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();
        Log.d(TAG, "onLayout mMeasuredWidth="+mMeasuredWidth+" ,mMeasuredHeight="+mMeasuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();
        Log.d(TAG, "onDraw mMeasuredWidth="+mMeasuredWidth+" ,mMeasuredHeight="+mMeasuredHeight);
    }
}
