package net.jackytallow.thinkvideo.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/27
 */
public class AppManager extends Application {

    private static Gson mGson;
    private static OkHttpClient mOkHttpClient;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mGson = new Gson();
        mOkHttpClient = new OkHttpClient();
    }

    public static Gson getGson() {
        return mGson;
    }

    public static OkHttpClient getHttpClient() {
        return mOkHttpClient;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Resources getResource() {
        return mContext.getResources();
    }
}
