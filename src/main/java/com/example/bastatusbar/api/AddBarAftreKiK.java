package com.example.bastatusbar.api;

import android.annotation.TargetApi;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.bastatusbar.bar_stytle.StatusBarRequest;

/**
 * Created by BA on 2017/10/21 0021.
 *
 * @Function : 安卓4.4到5.0实现的方法是使用了Translucent System Bar这个功能，就是设置状态栏半透明，然后用
 * fitsSystemWindows设置这个属性，就可以使用状态栏部分，然后在状态栏后面加一个VIew，作为背景，更改这个View
 * 的颜色就好
 */

public class AddBarAftreKiK implements AddStatusBarApi {

    private StatusBarRequest request;

    public AddBarAftreKiK(StatusBarRequest request) {
        this.request = request;
    }

    /**
     * @return
     * @throws
     * @fuction 初始化并加载我们的状态栏背景，我们添加的这个背景，
     * 不是添加在Activit的布局中，而是Activity的contextFrameLayout中设置作为状态栏背景的View的属性
     * @parm
     */
    @TargetApi(19)
    @Override
    public void addStatusBar() {
        if((request.isTrans()==request.isImageAsBg())) {
            Window window = request.getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup.LayoutParams layoutParams =
                    new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            request.getStatusBarHeigth());
            addMyStatusView(layoutParams);
        }
    }

    /**
     * @return
     * @throws
     * @fuction 将状态栏背景添加到ContentFrameLayout中, 不过要判断一下是否是图片作为背景
     * @parm View的属性
     */
    private void addMyStatusView(ViewGroup.LayoutParams layoutParams) {
        //获取contentLayout
        request.initLayout();
        //向下偏移一个状态栏的位置
        request.getContentFrameLayout().getChildAt(0).setPaddingRelative(0,request.getStatusBarHeigth(),0,0);
        View view = new View(request.getActivity());
        view.setBackgroundColor(request.getColorsFromRes());
        request.getContentFrameLayout().addView(view, layoutParams);
    }
}
