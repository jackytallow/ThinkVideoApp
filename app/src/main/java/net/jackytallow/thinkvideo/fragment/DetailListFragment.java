package net.jackytallow.thinkvideo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.jackytallow.thinkvideo.R;
import net.jackytallow.thinkvideo.api.OnGetChannelAlbumListener;
import net.jackytallow.thinkvideo.api.SiteApi;
import net.jackytallow.thinkvideo.base.BaseFragment;
import net.jackytallow.thinkvideo.model.Album;
import net.jackytallow.thinkvideo.model.AlbumList;
import net.jackytallow.thinkvideo.model.Channel;
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

    private int mSiteId;
    private int mChannelId;
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
        Bundle bundle = new Bundle();
        bundle.putInt(CHANNEL_ID, channelId);
        bundle.putInt(SITE_ID, siteId);
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
        if (getArguments() != null) {
            mSiteId = getArguments().getInt(SITE_ID);
            mChannelId = getArguments().getInt(CHANNEL_ID);
        }


        pageNo = 0;
        mAdapter = new DetailListAdapter(getActivity(), new Channel(mChannelId, getActivity()));
        loadData(); //第一次加载数据
        if (mSiteId == Site.LETV) { //乐视下2列
            mColunms = 2;
            mAdapter.setColumns(mColunms);
        }
    }

    @Override
    protected void initView() {
        mEmptyView = bindViewId(R.id.tv_empty);
        mEmptyView.setText(getActivity().getResources().getString(R.string.load_more_text));
        mRecyclerView = bindViewId(R.id.pullloadRecyclerView);
        mRecyclerView.setGridLayout(3);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
    }

    private void reFreshData() {
        //TODO 请求接口，加载数据
    }

    private void loadData() {
        //加载更多数据
        pageNo++;
        SiteApi.onGetChannelAlbums(getActivity(), pageNo, pageSize, mSiteId, mChannelId, new OnGetChannelAlbumListener() {
            @Override
            public void OnGetChannelAlbumSuccess(AlbumList albumList) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEmptyView.setVisibility(View.GONE);
                    }
                });
                for (Album album : albumList) {
                    mAdapter.setData(album);
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
                //                for (Album album : albumList) {
//                    Log.d(TAG, "OnGetChannelAlbumSuccess: album" + album.toString());
//                }

            }

            @Override
            public void OnGetChannelAlbumFailed(ErrorInfo info) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEmptyView.setText(getActivity().getResources().getString(R.string.data_failed_tip));
                    }
                });
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
            }, REFRESH_DURATION);
        }

        @Override
        public void loadMore() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadData();
                    mRecyclerView.setLoadMoreCompleted();
                }
            }, LOAD_MORE_DURATION);
        }
    }

    class DetailListAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private Channel mChannel;
        private AlbumList mAlbumList = new AlbumList();
        private int mColumns;

        public DetailListAdapter(Context context, Channel channel) {
            mContext = context;
            mChannel = channel;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.detailist_item, null);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            view.setTag(itemViewHolder);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
             if (mAlbumList.size() == 0) {
                 return;
             }
            Album album =  getItem(position);
             if (holder instanceof ItemViewHolder){
                 ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                 itemViewHolder.albumName.setText(album.getTitle());
                 if (album.getTip().isEmpty()) {
                     itemViewHolder.albumTip.setVisibility(View.GONE);
                 } else {
                     itemViewHolder.albumTip.setText(album.getTip());
                 }


             }
        }

        private Album getItem(int position) {
            return mAlbumList.get(position);
        }

        @Override
        public int getItemCount() {
            if (mAlbumList.size() > 0) {
                return mAlbumList.size();
            }
            return 0;
        }

        public void setColumns(int columns) {
           mColumns = columns;
        }

        public void setData(Album album) {
            mAlbumList.add(album);
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private LinearLayout resultContainer;
            private ImageView albumPoster;
            private TextView albumName;
            private TextView albumTip;

            public ItemViewHolder(View view) {
                super(view);
                resultContainer = view.findViewById(R.id.album_container);
                albumPoster = view.findViewById(R.id.iv_album_poster);
                albumName = view.findViewById(R.id.tv_album_name);
                albumTip = view.findViewById(R.id.tv_album_tip);
            }
        }
    }

    @Override
    protected void initData() {

    }
}
