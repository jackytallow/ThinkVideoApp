package net.jackytallow.thinkvideo.api;

import android.content.Context;

import net.jackytallow.thinkvideo.model.Channel;
import net.jackytallow.thinkvideo.model.Site;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/27
 */
public class SiteApi {
    public void onGetChannelAlbums(Context context, int pageNo, int pageSize, int siteId, int channelId, OnGetChannelAlbumListener listener) {
        switch (siteId) {
            case Site.LETV:
                new LetvApi().onGetChannelAlbums(new Channel(channelId, context), pageNo, pageSize, listener);
                break;
            case Site.SOHU:
                new SohuApi().onGetChannelAlbums(new Channel(channelId, context), pageNo, pageSize, listener);
                break;
        }
    }
}
