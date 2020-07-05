package net.jackytallow.thinkvideo.model.sohu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/7/5
 */
public class Data {

    @Expose
    private int count;

    @Expose
    @SerializedName("videos")
    private List<ResultAlbum> resultAlbumList;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultAlbum> getResultAlbumList() {
        return resultAlbumList;
    }

    public void setResultAlbumList(List<ResultAlbum> resultAlbumList) {
        this.resultAlbumList = resultAlbumList;
    }

}
