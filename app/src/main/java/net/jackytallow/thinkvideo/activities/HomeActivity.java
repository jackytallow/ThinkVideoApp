package net.jackytallow.thinkvideo.activities;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.navigation.NavigationView;
import net.jackytallow.thinkvideo.R;
import net.jackytallow.thinkvideo.base.BaseActivity;
import net.jackytallow.thinkvideo.fragment.AboutFragment;
import net.jackytallow.thinkvideo.fragment.BlogFragment;
import net.jackytallow.thinkvideo.fragment.FragmentManagerWrapper;
import net.jackytallow.thinkvideo.fragment.HomeFragment;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private MenuItem mPreItem;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setSupportActionBar();
        setActionBarIcon(R.mipmap.ic_drawer_home);
        setTitle("首页");

        mDrawerLayout = bindViewId(R.id.drawer_layout);
        mNavigationView = bindViewId(R.id.navigation_view);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,R.string.drawer_open,R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        //TODO
        mPreItem = mNavigationView.getMenu().getItem(0);
        mPreItem.setChecked(true);
        initFragment();
        handleNavigationView();
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = FragmentManagerWrapper.getInstance().createFragment(HomeFragment.class);
        mFragmentManager.beginTransaction().add(R.id.fl_main_content, mCurrentFragment).commit();
    }

    //TODO question 切换第一次的时候没有问题,但是第二次之后会出现多个item选中状态
    private void handleNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (mPreItem != null) {
                    mPreItem.setCheckable(false);
                }
                switch (item.getItemId()) {
                    case R.id.navigation_item_video:

                        mToolbar.setTitle(R.string.home_title);
                        break;
                    case R.id.navigation_item_blog:
                        switchFragment(BlogFragment.class);
                        mToolbar.setTitle(R.string.blog_title);
                        break;
                    case R.id.navigation_item_about:
                        switchFragment(AboutFragment.class);
                        mToolbar.setTitle(R.string.about_title);
                        break;
                }
                mPreItem = item;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                item.setChecked(true);
                return false;
            }
        });
    }

    private void switchFragment(Class<?> clazz) {
        Fragment fragment = FragmentManagerWrapper.getInstance().createFragment(clazz);
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.fl_main_content, fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }

    @Override
    protected void initData() {
        //TODO
    }
}
