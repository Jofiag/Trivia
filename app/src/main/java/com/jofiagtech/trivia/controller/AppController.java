package com.jofiagtech.trivia.controller;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application
{
    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized AppController getInstance() {
        if (mInstance == null) {
            mInstance = new AppController();
        }
        return mInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        //set the default tag if it's empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
    req.setTag(TAG);
    getRequestQueue().add(req);
    }

    public void cancelledPendingRequest(Object tag){
      if (mRequestQueue != null)
          mRequestQueue.cancelAll(tag);
    }
}
