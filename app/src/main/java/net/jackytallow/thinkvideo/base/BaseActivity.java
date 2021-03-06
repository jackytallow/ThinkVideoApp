package net.jackytallow.thinkvideo.base;

import android.os.Bundle;
import android.view.View;

import net.jackytallow.thinkvideo.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/5/6
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initData();
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    protected <T extends View> T bindViewId(int resId) {
        return findViewById(resId);
    }

    protected void setSupportActionBar() {
        mToolbar = bindViewId(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    protected void setSupportArrowActionBar(boolean isSupport) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isSupport);
    }

    protected void setActionBarIcon(int restId) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(restId);
        }
    }


}
