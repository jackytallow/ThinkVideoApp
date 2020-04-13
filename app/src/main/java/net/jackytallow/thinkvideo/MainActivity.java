package net.jackytallow.thinkvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final int GO_HOME = 1;
    private static final int GO_GUIDE = 2;
    private static final int ENTER_DURATION = 2000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    showHome();
                    break;
                case GO_GUIDE:
                    showGuide();
                    break;
                default:
                    break;
            }
        }
    };

    private void showHome() {
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void showGuide() {
        Intent intent = new Intent(MainActivity.this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        //初始化
        init();
    }

    private void init() {
        boolean isFirstIn = sharedPreferences.getBoolean("mIsFirstIn", true);
        if (isFirstIn) {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, ENTER_DURATION);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_HOME, ENTER_DURATION);
        }
    }
}
