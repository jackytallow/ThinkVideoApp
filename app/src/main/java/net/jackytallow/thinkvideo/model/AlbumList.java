package net.jackytallow.thinkvideo.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/27
 */
public class AlbumList extends ArrayList<Album> {

    private static final String TAG = AlbumList.class.getSimpleName();

    public void debug () {
        for (Album a : this) {
            Log.d(TAG, ">> albumlist " + a.toString());
        }
    }

}
