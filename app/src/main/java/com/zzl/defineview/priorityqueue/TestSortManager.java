package com.zzl.defineview.priorityqueue;

import android.util.Log;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by zhenglin.zhu on 2021/7/19.
 */
public class TestSortManager<T> {
    private static final String TAG = TestSortManager.class.getSimpleName();
    private PriorityBlockingQueue<com.zzl.defineview.priorityqueue.IBaseBean> mQueue = new PriorityBlockingQueue();
    private static TestSortManager sTestSortUtil;

    public static TestSortManager getInstance() {
        if (sTestSortUtil == null) {
            synchronized (TestSortManager.class) {
                if (sTestSortUtil == null) {
                    sTestSortUtil = new TestSortManager();
                }
            }
        }
        return sTestSortUtil;
    }

    public void initData() {
        mQueue.add(new UserBean(10, "wangwu10"));
        mQueue.add(new UserBean(4, "wangwu10"));
        mQueue.add(new UserBean(20, "wangwu10"));
        mQueue.add(new UserBean(13, "wangwu10"));
        mQueue.add(new UserBean(122, "wangwu10"));
        mQueue.add(new UserBean(9, "wangwu10"));
        mQueue.add(new UserBean(33, "wangwu10"));
        mQueue.add(new UserBean(65, "wangwu10"));
        mQueue.add(new UserBean(21, "wangwu10"));
        mQueue.add(new UserBean(58, "wangwu10"));
        mQueue.add(new UserBean(7, "wangwu10"));
        mQueue.add(new UserBean(6, "wangwu10"));
    }

    public void printSortResult() {
        Iterator<IBaseBean> iterator = (Iterator<IBaseBean>) mQueue.iterator();
        while (iterator.hasNext()) {
            IBaseBean bean = iterator.next();
            UserBean userBean = (UserBean) bean;
            try {
                Log.d(TAG, "printSortResult userBean" + mQueue.take().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (com.zzl.defineview.priorityqueue.IBaseBean iBaseBean : mQueue) {
            try {
                Log.d(TAG, "printSortResult userBean" +  mQueue.take().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "printSortResult "+mQueue.size());
    }
}
