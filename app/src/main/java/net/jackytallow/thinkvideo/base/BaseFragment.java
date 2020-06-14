package net.jackytallow.thinkvideo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/14 Fragment基类
 */
public abstract class BaseFragment extends Fragment {

    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = getActivity().getLayoutInflater().inflate(getLayoutId(), container,false);
        initView();
        initData();
        return mContentView;
    }


    protected <T extends View> T bindViewId(int resId){
        return (T)mContentView.findViewById(resId);
    }


    protected abstract int getLayoutId();
    protected abstract void initView();

    protected abstract void initData();


}
