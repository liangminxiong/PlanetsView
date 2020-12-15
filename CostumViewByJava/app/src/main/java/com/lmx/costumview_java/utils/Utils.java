package com.lmx.costumview_java.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.lmx.costumview_java.App;

/**
 * Created by lmx on 2020/12/12
 * Describe: 工具类
 */
public class Utils {

    public static int dpToPx(int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, new App().getInstance().getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int getScreenWidth() {
        DisplayMetrics metrics = App.getInstance().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics metrics = App.getInstance().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
}
