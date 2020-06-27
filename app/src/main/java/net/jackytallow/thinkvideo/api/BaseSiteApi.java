package net.jackytallow.thinkvideo.api;

import net.jackytallow.thinkvideo.model.Channel;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/27
 */
public abstract class BaseSiteApi {

   public abstract void onGetChannelAlbums(Channel channel , int pageNo, int pageSize, OnGetChannelAlbumListener listener);
}
