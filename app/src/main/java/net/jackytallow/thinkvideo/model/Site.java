package net.jackytallow.thinkvideo.model;

import android.content.Context;

import net.jackytallow.thinkvideo.R;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/21
 */
public class Site {

    public static final int SOHU = 1;//搜狐视频
    public static final int LETV = 2;//乐视视频
    public static final int MAX_SITE = 2;


    private int siteId;
    private String siteName;

    public Site(int id) {
        siteId = id;
        switch (siteId) {
            case LETV:
                siteName = "乐视视频";
                break;
            case SOHU:
                siteName = "搜狐视频";
                break;
        }
    }



    public int getSiteId() {
        return siteId;
    }



}
