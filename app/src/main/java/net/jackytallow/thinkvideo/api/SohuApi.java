package net.jackytallow.thinkvideo.api;

import net.jackytallow.thinkvideo.base.AppManager;
import net.jackytallow.thinkvideo.model.Album;
import net.jackytallow.thinkvideo.model.AlbumList;
import net.jackytallow.thinkvideo.model.Channel;
import net.jackytallow.thinkvideo.model.ErrorInfo;
import net.jackytallow.thinkvideo.model.Site;
import net.jackytallow.thinkvideo.model.sohu.Result;
import net.jackytallow.thinkvideo.model.sohu.ResultAlbum;
import net.jackytallow.thinkvideo.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/27
 */
public class SohuApi extends BaseSiteApi {

    private static final String TAG = SohuApi.class.getSimpleName();

    private static final int SOHU_CHANNELID_MOVIE = 1; //搜狐电影频道ID
    private static final int SOHU_CHANNELID_SERIES = 2; //搜狐电视剧频道ID
    private static final int SOHU_CHANNELID_VARIETY = 7; //搜狐综艺频道ID
    private static final int SOHU_CHANNELID_DOCUMENTRY = 8; //搜狐纪录片频道ID
    private static final int SOHU_CHANNELID_COMIC = 16; //搜狐动漫频道ID
    private static final int SOHU_CHANNELID_MUSIC = 24; //搜狐音乐频道ID


    private final static String API_CHANNEL_ALBUM_FORMAT = "http://api.tv.sohu.com/v4/search/channel.json" +
            "?cid=%s&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&" +
            "sver=6.2.0&sysver=4.4.2&partner=47&page=%s&page_size=%s";


    @Override
    public void onGetChannelAlbums(Channel channel, int pageNo, int pageSize, OnGetChannelAlbumListener listener) {
        String url = getChannelAlbumUrl(channel, pageNo, pageSize);
        doGetChannelAlbumsByUrl(url, listener);
    }

    private void doGetChannelAlbumsByUrl(final String url, final OnGetChannelAlbumListener listener) {
        //TODO 网络请求
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    ErrorInfo info = buildErrorInfo(url, "doGetChannelAlbumsByUrl", e, ErrorInfo.ERROR_TYPE_URL);
                    listener.OnGetChannelAlbumFailed(info);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo info = buildErrorInfo(url, "doGetChannelAlbumsByUrl", null, ErrorInfo.ERROR_TYPE_FATAL);
                    listener.OnGetChannelAlbumFailed(info);
                    return;
                }
                //1. 取到数据映射ResulAlbum
                //2.转换ResultAlbum变成Album
                //3.Album存到AlbumList中
                Result result = AppManager.getGson().fromJson(response.body().string(), Result.class);
                AlbumList albumList = toConvertAlbumList(result);
                if (albumList != null) {
                    if (albumList.size() > 0 && listener != null) {
                        listener.OnGetChannelAlbumSuccess(albumList);
                    }
                } else {
                    ErrorInfo info = buildErrorInfo(url, "doGetChannelAlbumsByUrl", null, ErrorInfo.ERROR_TYPE_DATA_CONVERT);
                    listener.OnGetChannelAlbumFailed(info);
                }
            }
        });
    }

    private AlbumList toConvertAlbumList(Result result) {
        if (result.getData().getResultAlbumList().size() > 0) {
            AlbumList albumList = new AlbumList();

            for (ResultAlbum resultAlbum : result.getData().getResultAlbumList()) {
                Album album = new Album(Site.SOHU, AppManager.getContext());
                album.setAlbumDesc(resultAlbum.getTvDesc());
                album.setAlbumId(resultAlbum.getAlbumId());
                album.setHorImgUrl(resultAlbum.getHorHighPic());
                album.setMainActor(resultAlbum.getMainActor());
                album.setTip(resultAlbum.getTip());
                album.setTitle(resultAlbum.getVerHighPic());
                album.setDirector(resultAlbum.getDirector());
                albumList.add(album);
            }
            return albumList;
        }
        return null;
    }

    private ErrorInfo buildErrorInfo(String url, String functionName, Exception e, int type) {
        ErrorInfo info = new ErrorInfo(Site.SOHU, type);
        info.setExceptionString(e.getMessage());
        info.setFunctionName(functionName);
        info.setUrl(url);
        info.setTag(TAG);
        info.setClassName(TAG);
        return info;
    }

    private String getChannelAlbumUrl(Channel channel, int pageNo, int pageSize) {
        //格式化url
        return String.format(API_CHANNEL_ALBUM_FORMAT, toConvertChanneId(channel), pageNo, pageSize);
    }

    private int toConvertChanneId(Channel channel) {
        int channelId = -1; //-1 无效值
        switch (channel.getChannelId()) {
            case Channel.SHOW:
                channelId = SOHU_CHANNELID_SERIES;
                break;
            case Channel.MOVIE:
                channelId = SOHU_CHANNELID_MOVIE;
                break;
            case Channel.COMIC:
                channelId = SOHU_CHANNELID_COMIC;
                break;
            case Channel.MUSIC:
                channelId = SOHU_CHANNELID_MUSIC;
                break;
            case Channel.DOCUMENTRY:
                channelId = SOHU_CHANNELID_DOCUMENTRY;
                break;
            case Channel.VARIETY:
                channelId = SOHU_CHANNELID_VARIETY;
                break;
        }
        return channelId;
    }
}