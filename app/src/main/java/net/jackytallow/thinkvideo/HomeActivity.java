package net.jackytallow.thinkvideo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        setSupportActionBar();
        setTitle("首页");
        setActionBarIcon(R.drawable.ic_drawer_home);
    }

    @Override
    protected void initView() {

    }
}
