package net.jackytallow.thinkvideo.fragment;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import net.jackytallow.thinkvideo.R;
import net.jackytallow.thinkvideo.base.BaseFragment;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/14
 */
public class AboutFragment extends BaseFragment {
    @Override
    protected void initView() {
        TextView textView = bindViewId(R.id.tv_app_des);
        textView.setAutoLinkMask(Linkify.ALL); //表示文字中有链接可点
        textView.setMovementMethod(LinkMovementMethod.getInstance()); //表示文字可以滚动
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initData() {

    }
}
