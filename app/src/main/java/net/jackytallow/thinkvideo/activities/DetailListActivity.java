package net.jackytallow.thinkvideo.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.ViewGroup;

import net.jackytallow.thinkvideo.R;
import net.jackytallow.thinkvideo.base.BaseActivity;
import net.jackytallow.thinkvideo.fragment.DetailListFragment;
import net.jackytallow.thinkvideo.model.Channel;
import net.jackytallow.thinkvideo.model.Site;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/21
 */
public class DetailListActivity extends BaseActivity {

    private static final String CHANNEL_ID = "chanelId";
    private int mChannId;
    private ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_list;
    }


    //处理左上角返回箭头
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            mChannId = intent.getIntExtra(CHANNEL_ID, 0);
        }
        Channel channel = new Channel(mChannId,this);
        String titleName = channel.getChannelName();

        setSupportActionBar();
        setSupportArrowActionBar(true);
        setTitle(titleName);

        mViewPager = bindViewId(R.id.pager);
        mViewPager.setAdapter(new SitePagerAdapter(getSupportFragmentManager(), this, mChannId));
    }

    static class SitePagerAdapter extends FragmentPagerAdapter {

        private Context mContext;
        private int mChannelID;
        private HashMap<Integer, DetailListFragment> mPagerMap;

        public SitePagerAdapter(FragmentManager fm, Context context, int channelid) {
            super(fm);
            mContext = context;
            mChannelID = channelid;
            mPagerMap = new HashMap<>();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof DetailListFragment) {
                mPagerMap.put(position, (DetailListFragment) obj);
            }
            return obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPagerMap.remove(position);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = DetailListFragment.newInstance(new Site(1, mContext).getChannelId(), mChannelID);
            return fragment;
        }

        @Override
        public int getCount() {
            return Site.MAX_SITE;
        }
    }


    public static void launchDetailList(Context context,int chanelId) {
        Intent intent = new Intent(context,DetailListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(CHANNEL_ID,chanelId);
        context.startActivity(intent);
    }

}
