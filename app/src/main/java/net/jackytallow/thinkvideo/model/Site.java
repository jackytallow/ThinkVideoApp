package net.jackytallow.thinkvideo.model;

import android.content.Context;

import net.jackytallow.thinkvideo.R;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/21
 */
public class Site {

    public static final int LETV = 1;//乐视视频
    public static final int SOHU = 2;//搜狐视频
    public static final int MAX_SITE = 2;


    private int siteId;
    private String siteName;
    private Context mContext;

    public Site(int id, Context context) {
        siteId = id;
        mContext = context;
        switch (siteId) {
            case LETV:
                siteName = mContext.getResources().getString(R.string.site_letv);
                break;
            case SOHU:
                siteName = mContext.getResources().getString(R.string.site_sohu);
                break;
        }
    }



    public int getSiteId() {
        return siteId;
    }



}
