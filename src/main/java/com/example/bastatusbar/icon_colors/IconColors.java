package com.example.bastatusbar.icon_colors;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by BA on 2018/2/1 0001.
 *
 * @Function : 让状态了为白底，图标为暗色图标
 */

public class IconColors {

    /**
     * 设置为沉浸式状态栏，设置了状态栏颜色及字体颜色
     *
     * @param activity
     * @return
     * @throws
     * @author CNT on 2018/1/28.
     */

    public static void setStatusBarLightMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //先判断是否为小米或魅族手机，如果是则将状态栏文字改为黑色
            if (MIUISetStatusBarLightMode(activity, true) || FlymeSetStatusBarLightMode(activity, true)) {
                //设置状态栏为透明颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上
                    //activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上
                    //Window window = activity.getWindow();
                    //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            } else if (Build.VERSION.SDK_INT >= 21) {
                //activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        }
    }
    public static void setStatusBarDarkMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //先判断是否为小米或魅族手机，如果是则将状态栏文字改为白色
            if (MIUISetStatusBarDarkMode(activity, false) || FlymeSetStatusBarLightMode(activity, false)) {
                //设置状态栏为透明颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上
                    //activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上
                    //Window window = activity.getWindow();
                    //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            } else if (Build.VERSION.SDK_INT >= 21) {
                //activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //如果是6.0以上将状态栏文字改为白色，并设置状态栏颜色
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.ACCESSIBILITY_LIVE_REGION_NONE);
                }
            }
        }
    }

    /**
     * 判断是否为MIUI，如果是，则修改其状态栏字体颜色为黑色；否则return false
     *
     * @param activity, boolean darkmode
     * @return boolean
     * @throws
     * @author CNT on 2018/1/28.
     */

    private static boolean MIUISetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            //适配MIUI9的系统
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean MIUISetStatusBarDarkMode(Activity activity, boolean darkmode) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            //适配MIUI9的系统
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.ACCESSIBILITY_LIVE_REGION_NONE);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否为Flyme，如果是，则修改其状态栏字体颜色为黑色；否则return false
     *
     * @param activity, boolean darkmode
     * @return boolean
     * @throws
     * @author CNT on 2018/1/28.
     */
    private static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
