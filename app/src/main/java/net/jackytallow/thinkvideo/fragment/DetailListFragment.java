package net.jackytallow.thinkvideo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jackytallow.thinkvideo.R;
import net.jackytallow.thinkvideo.api.OnGetChannelAlbumListener;
import net.jackytallow.thinkvideo.api.SiteApi;
import net.jackytallow.thinkvideo.base.BaseFragment;
import net.jackytallow.thinkvideo.model.Album;
import net.jackytallow.thinkvideo.model.AlbumList;
import net.jackytallow.thinkvideo.model.ErrorInfo;
import net.jackytallow.thinkvideo.model.Site;
import net.jackytallow.thinkvideo.widget.PullLoadRecycleView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/21
 */
public class DetailListFragment extends BaseFragment {

    private static final String TAG = DetailListFragment.class.getSimpleName();

    private static int mSiteId;
    private static int mChannelId;
    private static final String CHANNEL_ID = "channelId";
    private static final String SITE_ID = "siteId";
    private PullLoadRecycleView mRecyclerView;
    private TextView mEmptyView;
    private int mColunms;
    private DetailListAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int REFRESH_DURATION = 1500;
    private static final int LOAD_MORE_DURATION = 3000;
    private int pageNo;
    private int pageSize = 30;


    public DetailListFragment() {
    }

    public static Fragment newInstance(int siteId, int channelId) {
        DetailListFragment detailListFragment = new DetailListFragment();
        mSiteId = siteId;
        mChannelId = channelId;
        Bundle bundle = new Bundle();
        bundle.putInt(CHANNEL_ID,mChannelId);
        bundle.putInt(SITE_ID,mSiteId);
        detailListFragment.setArguments(bundle);
        return detailListFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detaillist;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNo = 0;
        loadData();
        mAdapter = new DetailListAdapter();
        if (mSiteId == Site.LETV) { //乐视下2列
            mColunms = 2;
            mAdapter.setColumns(mColunms);
        }
    }

    @Override
    protected void initView() {
       mEmptyView =  bindViewId(R.id.tv_empty);
       mEmptyView.setText(getActivity().getResources().getString(R.string.load_more_text));
       mRecyclerView =  bindViewId(R.id.pullloadRecyclerView);
       mRecyclerView.setGridLayout(3);
       mRecyclerView.setAdapter(mAdapter);
       mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
    }

    private void reFreshData() {
        //TODO 请求接口，加载数据
    }

    private void loadData() {
        //加载更多数据
        pageNo ++;
        SiteApi.onGetChannelAlbums(getActivity(), pageNo, pageSize, 2, mChannelId, new OnGetChannelAlbumListener() {
            @Override
            public void OnGetChannelAlbumSuccess(AlbumList albumList) {
                for (Album album : albumList) {
                    Log.d(TAG, "OnGetChannelAlbumSuccess: album" + album.toString());
                }
            }

            @Override
            public void OnGetChannelAlbumFailed(ErrorInfo info) {
                Log.d(TAG, "OnGetChannelAlbumFailed: failed");
                //打印一下错误信息
                Log.d(TAG, "OnGetChannelAlbumFailed: errorInfo>>>"+info.getReason());
                Log.d(TAG, "OnGetChannelAlbumFailed: errorInfo>>>"+info.getExceptionString());
            }
        });
    }

    class PullLoadMoreListener implements PullLoadRecycleView.OnPullLoadMoreListener {

        @Override
        public void reFresh() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reFreshData();
                    mRecyclerView.setRefreshCompleted();
                }
            },REFRESH_DURATION);
        }

        @Override
        public void loadMore() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadData();
                    mRecyclerView.setLoadMoreCompleted();
                }
            },LOAD_MORE_DURATION);
        }
    }

    class DetailListAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public void setColumns(int columns) {
            //TODO
        }
    }

    @Override
    protected void initData() {

    }
}
