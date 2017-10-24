package com.example.bastatusbar.api;

import android.annotation.TargetApi;
import android.support.design.widget.CoordinatorLayout;
import android.view.Window;
import android.view.WindowManager;

import com.example.bastatusbar.bar_stytle.StatusBarRequest;

/**
 * Created by BA on 2017/10/21 0021.
 *
 * @Function : 实现安卓5.0以上沉浸式状态栏,主要是更改状态栏颜色，还有将安卓4.4Activity添加的半透明状态栏功能
 * 也实现了，实现扩展到状态栏显示控件的基础设置
 */

public class AddBarAfterLollipop implements AddStatusBarApi {

    private StatusBarRequest request;

    protected AddBarAfterLollipop(StatusBarRequest request) {
        this.request = request;
    }


    /**
     * @return
     * @throws
     * @fuction 在安卓21后设置的状态栏透明，不是全透明的，只能手动直接设置颜色了，从而实现全透明的效果咯
     * 如果没有要求将状态栏设置成全透明，取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏，手动设置状态栏颜色
     * @parm
     */
    @TargetApi(21)
    @Override
    public void addStatusBar() {

        request.initLayout();

        //如果是将Activity的背景作为状态栏背景就要对CoordinatorLayout进行特殊处理
        if (request.isImageAsBg()&&request.getContentFrameLayout().getChildAt(0) instanceof CoordinatorLayout) {
            request.getContentFrameLayout().getChildAt(0).setPaddingRelative(0,request.getStatusBarHeigth(),0,0);
        }

        //如果是扩展状态栏显示就设置这个属性
       if((!request.isImageAsBg()&&request.isTrans()))
            request.setFitsSystemWindows();

        Window window = request.getActivity().getWindow();

        //去除半透明状态栏属性,注意，这里的清除是为了防止手动在Values的Stytle设置了半透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //这里设置半透明是因为有时候需要将Activity的背景作为状态栏背景
        if (request.isImageAsBg()){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        window.setStatusBarColor(request.getColorsFromRes());
    }
}
