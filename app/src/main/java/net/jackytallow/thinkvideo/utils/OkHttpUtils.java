package net.jackytallow.thinkvideo.utils;

import net.jackytallow.thinkvideo.base.AppManager;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/7/5
 */
public class OkHttpUtils {

    private static final String REQUEST_TAG = "okHttp";

    public static Request buildRequest(String url) {
        if (AppManager.isNetWorkAvaliable()) {
            Request request = new Request.Builder()
                    .tag(REQUEST_TAG)
                    .url(url)
                    .build();
            return null;
        }
        return null;
    }

    public static void excute(String url, Callback callback) {
        Request request = buildRequest(url);
        excute(request,callback);
    }

    public static void excute(Request request, Callback callback) {
        AppManager.getHttpClient().newCall(request).enqueue(callback);
    }
}
