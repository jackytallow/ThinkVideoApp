package net.jackytallow.thinkvideo.api;

import net.jackytallow.thinkvideo.model.AlbumList;
import net.jackytallow.thinkvideo.model.ErrorInfo;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/27
 */
public interface OnGetChannelAlbumListener {

    void OnGetChannelAlbumSuccess(AlbumList albumList);

    void OnGetChannelAlbumFailed(ErrorInfo info);
}
