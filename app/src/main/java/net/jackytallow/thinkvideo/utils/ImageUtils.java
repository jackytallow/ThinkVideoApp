package net.jackytallow.thinkvideo.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.jackytallow.thinkvideo.R;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/8/23
 */
public class ImageUtils {


    private static final float VER_POSTER_RATIO = 0.73f;
    private static final float HOR_POSTER_RATIO = 1.5f;

    public static void displayImage(ImageView imageView, String url, int width, int height) {

        if (imageView != null && url != null && width > 0 && height > 0) {
            if (width > height) {
                Glide.with(imageView.getContext())
                        .load(url)
                        .error(R.mipmap.ic_loading_hor)
                        .fitCenter()
                        .override(height, width)
                        .into(imageView);
            } else {
                Glide.with(imageView.getContext())
                        .load(url)
                        .error(R.mipmap.ic_loading_hor)
                        .centerCrop()
                        .override(width, height)
                        .into(imageView);
            }
        }
    }


    /**
     * 图片获取最佳比例
     * @param context
     * @param columns
     * @return
     */
    public static Point getVerPostSize(Context context , int columns) {
         int width = getScreenWidthPixel(context) / columns;
         width = (int) (width - context.getResources().getDimension(R.dimen.dimen_8dp));
         int height = Math.round((float)width/VER_POSTER_RATIO);
         Point point = new Point();
         point.x = width;
         point.y = height;
         return point;
    }

    private static int getScreenWidthPixel(Context context) {
        WindowManager  wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        return width;
    }
}
