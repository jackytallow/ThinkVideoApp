package net.jackytallow.thinkvideo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.jackytallow.thinkvideo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/27
 */
public class PullLoadRecycleView extends LinearLayout {

    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View mFootView;
    private boolean mIsRefresh = false; //是否刷新
    private boolean mIsLoadMore = false; //是否加载更多
    private AnimationDrawable mAnimationDrawable;
    private OnPullLoadMoreListener mOnPullLoadMoreListener;


    public PullLoadRecycleView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullLoadRecycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pull_loadmore_layout, null);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        //设置颜色渐变
        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh());

        //处理RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true); //设置固定大小
        mRecyclerView.setItemAnimator(new DefaultItemAnimator()); //默认动画
        mRecyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mIsRefresh || mIsLoadMore;
            }
        });

        mRecyclerView.setVerticalScrollBarEnabled(false); //隐藏
        mRecyclerView.addOnScrollListener(new RecyclerViewOnScroll());

        mFootView = view.findViewById(R.id.footer_view);
        ImageView imageView = mFootView.findViewById(R.id.iv_load_img);
        imageView.setBackgroundResource(R.drawable.thinkvideo_loading);
        mAnimationDrawable = (AnimationDrawable) imageView.getBackground();

        TextView textView = mFootView.findViewById(R.id.tv_load_text);
        mFootView.setVisibility(View.GONE);
        //view 包含swipeRefreshLayout,RecyclerView,FootView
        this.addView(view);
    }

    //外部可以recyclerView的列数
    public void setGridLayout(int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(mContext, spanCount);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }


    class SwipeRefreshLayoutOnRefresh implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            if (!mIsRefresh) {
                mIsRefresh = true;
                refreshData();
            }
        }
    }

    class RecyclerViewOnScroll extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int firstItem = 0;
            int lastItem = 0;
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            int totalCount = manager.getItemCount();
            if (manager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
                //完全可见
                firstItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (firstItem == 0 || firstItem == RecyclerView.NO_POSITION) {
                    lastItem = gridLayoutManager.findLastVisibleItemPosition();
                }
            }

            //触发上拉加载更多
            if (mSwipeRefreshLayout.isEnabled()) {
                mSwipeRefreshLayout.setEnabled(true);
            } else {
                mSwipeRefreshLayout.setEnabled(false);
            }
            //1.没有加载更多，totalCount- 1 == lastItem
            //3.刷新layout可以用
            //4.不是处于下拉刷新状态
            if (!mIsLoadMore && totalCount - 1 == lastItem
                    && mSwipeRefreshLayout.isEnabled()
                    && mIsRefresh
                    && (dx > 0 || dy > 0)) {
                loadMoreData();
            }
        }
    }

    private void loadMoreData() {
        if (mOnPullLoadMoreListener != null) {
            mOnPullLoadMoreListener.reFresh();
        }
    }


    private void refreshData() {
        if (mOnPullLoadMoreListener != null) {
            mFootView.animate().translationY(mFootView.getHeight())
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(300).start();
            invalidate();
            mOnPullLoadMoreListener.loadMore();
        }
    }


    //设置刷新关闭
    public void setRefreshCompleted() {
        mIsRefresh = false;
        setRefreshing(false);
    }

    //设置是否正在刷新
    private void setRefreshing(final boolean isRefreshing) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
              mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });
    }

    public void setLoadMoreCompleted() {
        mIsLoadMore = false;
        mIsRefresh = false;
        setRefreshing(false);
    }


    public interface OnPullLoadMoreListener {
        void reFresh();
        void loadMore();
    }

    public void setOnPullLoadMoreListener(OnPullLoadMoreListener listener) {
        mOnPullLoadMoreListener = listener;
    }

}
