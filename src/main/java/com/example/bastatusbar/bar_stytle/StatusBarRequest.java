package com.example.bastatusbar.bar_stytle;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by BA on 2017/10/23 0023.
 *
 * @Function : 用来设置Flag，还有提供一些通用的方法
 */

public class StatusBarRequest {

    //状态栏颜色值Id
    private int colorResId;

    //用了确定状态是有颜色还是透明
    private boolean isTrans;

    //判断是否是将图片作为背景，不过在安卓5.0以上会以半透明状态栏显示
    private boolean isImageAsBg;

    //content布局
    private ViewGroup contentFrameLayout;

    //activity
    private AppCompatActivity activity;

    public void setColorResId(int colorResId) {
        this.colorResId = colorResId;
    }

    public boolean isTrans() {
        return isTrans;
    }

    public boolean isImageAsBg() {
        return isImageAsBg;
    }

    public ViewGroup getContentFrameLayout() {
        return contentFrameLayout;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    protected StatusBarRequest(AppCompatActivity activity,boolean isImageAsBg,boolean isTrans,int colorResId){
        this.activity=activity;
        this.isImageAsBg=isImageAsBg;
        this.isTrans=isTrans;
        this.colorResId=colorResId;
    }

    public void initLayout(){
        contentFrameLayout = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
    }

    /**
     * @return
     * @throws
     * @fuction 实现全局设置FitsSystemWindows属性,设置这个属性后，Activity布局大小会延伸到状态栏，
     * 而且会自动将Activity的布局向下预留一个状态栏大小的位置
     * 目的就是为了不发生状态栏与ToolBar遮挡
     * @parm Activity的布局文件的Id
     */
    public void setFitsSystemWindows() {
        //获取Activity的布局
        View rootView = contentFrameLayout.getChildAt(0);
        //启用Translucent System Bar，必须要设置的属性
        if (rootView != null && judgmentVersion(19)) {
            rootView.setFitsSystemWindows(true);
        }
    }

    /**
     *@fuction 根据颜色Id，获取颜色对象
     *@parm
     *@return
     *@exception
     */
    public int getColorsFromRes() {
        if (isTrans) {
            return Color.TRANSPARENT;
        }
        return ContextCompat.getColor(activity, colorResId);
    }

    /**
     * function : 获取状态栏高度
     * param :
     * return : 返回获取到的状态栏高度，没有获取到就返回-1
     * exception :
     */
    public int getStatusBarHeigth() {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    //用来判断安卓版本
    public boolean judgmentVersion(int version){
            return Build.VERSION.SDK_INT>=version;
    }
}
