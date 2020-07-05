package net.jackytallow.thinkvideo.model.sohu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/7/5 搜狐列表页数据返回集
 */
public class Result {


    @Expose
    private long status;

    @Expose
    private String statusText;

    //for 列表页
    @Expose
    private Data data;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
