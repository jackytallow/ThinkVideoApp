package net.jackytallow.thinkvideo.fragment;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import net.jackytallow.thinkvideo.R;
import net.jackytallow.thinkvideo.base.BaseFragment;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/14 博客界面
 */
public class BlogFragment extends BaseFragment {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private static final int MAX_VALUE = 100;
    private static final String BLOG_URL = "https://github.com/jackytallow";

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initData() {
        mWebView = bindViewId(R.id.webview);
        mProgressBar = bindViewId(R.id.pb_progress);
        WebSettings settings = mWebView.getSettings();   //用来设置webview的settings
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        mProgressBar.setMax(MAX_VALUE);
        mWebView.loadUrl(BLOG_URL);
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress); //加载过程中更新进度
            if (newProgress == MAX_VALUE) {//最大值进行隐藏
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    };
}