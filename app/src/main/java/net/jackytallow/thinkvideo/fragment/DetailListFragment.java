package net.jackytallow.thinkvideo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jackytallow.thinkvideo.R;
import net.jackytallow.thinkvideo.base.BaseFragment;
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
        //TODO 加载更多数据
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
