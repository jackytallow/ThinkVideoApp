package net.jackytallow.thinkvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 */
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<View> mViewList;
    private ViewPager viewPager;
    private ImageView[] mDotList;
    private int mLastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initViewPager();
        initDots();
    }

    private void initDots() {
        LinearLayout dotsLayout = findViewById(R.id.ll_dots_layout);
        mDotList = new ImageView[mViewList.size()];
        for (int i = 0; i < mViewList.size(); i++) {
            mDotList[i] = (ImageView) dotsLayout.getChildAt(i);
            mDotList[i].setEnabled(false);
        }
        mLastPosition = 0;
        mDotList[0].setEnabled(true);
    }


    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mViewList = new ArrayList<>();
        mViewList.add(inflater.inflate(R.layout.guide_one_layout, null));
        mViewList.add(inflater.inflate(R.layout.guide_two_layout, null));
        mViewList.add(inflater.inflate(R.layout.guide_three_layout, null));
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        MyPagerAdapter adapter = new MyPagerAdapter(mViewList, this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentDotPosition(position);
    }

    private void setCurrentDotPosition(int position) {
        mDotList[position].setEnabled(true);
        mDotList[mLastPosition].setEnabled(false);
        mLastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPagerAdapter extends PagerAdapter {

        private List<View> mImageViewList;
        private Context mContext;

        MyPagerAdapter(List<View> list, Context context) {
            super();
            mImageViewList = list;
            mContext = context;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (mImageViewList != null) {
                if (mImageViewList.size() > 0) {
                    container.addView(mImageViewList.get(position));
                    if (position == mImageViewList.size() - 1) {
                        ImageView imageView = mImageViewList.get(position).findViewById(R.id.iv_start);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startHomeActivity();
                                setGuided();
                            }
                        });
                    }
                    return mImageViewList.get(position);
                }
            }
            return null;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (mImageViewList != null) {
                if (mImageViewList.size() > 0) {
                    container.removeView(mImageViewList.get(position));
                }
            }

        }

        @Override
        public int getCount() {
            if (mImageViewList != null) {
                return mImageViewList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    private void setGuided() {
          SharedPreferences sp =  getSharedPreferences("config",MODE_PRIVATE);
          sp.edit().putBoolean("mIsFirstIn",false);
          sp.edit().commit();
    }


    private void startHomeActivity() {
        Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
