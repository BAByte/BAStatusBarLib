package com.example.bastatusbar;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.bastatusbar.api.AddBarApiFactory;
import com.example.bastatusbar.api.AddStatusBarApi;
import com.example.bastatusbar.bar_stytle.BarStytleBulider;
import com.example.bastatusbar.bar_stytle.StatusBarRequest;
import com.example.bastatusbar.icon_colors.IconColors;

/**
 * Created by BA on 2017/10/23 0023.
 *
 * @Function : 实现每个Activity可定制化3种沉浸状态栏的功能封装类
 * 1.安卓4.4：实现纯色状态栏，注意！当在安卓4.4实现侧滑菜单时，
 * 如果用到了CoordinatorLayout，要给该布局设置android:fitsSystemWindows="true"属性
 * 2.安卓4.4：实现activity背景延伸到状态栏显示
 * 3.安卓5.0以上：实现纯色状态栏
 * 4.都实现了实现activity背景延伸到状态栏显示
 * 5.封装了在将控件延伸到状态栏显示时需要设置的主题状态栏透明设置
 */

public class BAStatusBar {

    private StatusBarRequest request;

    private AddStatusBarApi addStatusBarApi;

    private AppCompatActivity activity;

    /**
     * @return
     * @throws
     * @fuction 设置纯色状态栏
     * @parm 将要设置的颜色资源Id
     */
    public  void setColorToBar(AppCompatActivity activity, int colorResId) {
        this.activity=activity;
        request= BarStytleBulider.getStytleBuilder().setColorResId(colorResId).build(activity);
        addBar();
    }

    /**
     * @return
     * @throws
     * @fuction 设置半透明状态栏，一般是在将Activity的背景设置成图片时使用
     * @parm
     */
    public  void setTranslucentBar(AppCompatActivity activity) {
        this.activity=activity;
        request=BarStytleBulider.getStytleBuilder().setImageAsBg().setTrans().build(activity);
        addBar();
    }

    /**
     * @return
     * @throws
     * @fuction 扩展到状态栏显示只能在安卓5.0以上，能用的布局也有限
     * coordinatorLayout,AppBarLayout,CollapsingToolbarLayout这种布局才有效
     * @parm
     */
    @TargetApi(21)
    public  void setfitsSystemWindowsBar(AppCompatActivity activity) {
        this.activity=activity;
        if (Build.VERSION.SDK_INT >= 21) {
            request = BarStytleBulider.getStytleBuilder().setTrans().build(activity);
            addBar();
        }
    }

    /**
     *@fuction 隐藏状态栏
     *@parm activity 用来获取Window
     *@return
     *@exception
     */
    public void setFullScreen(AppCompatActivity activity){
        this.activity=activity;
        activity. getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     *@fuction 针对不同安卓版本使用不同的方式实现沉浸式状态栏
     *@parm
     *@return
     *@exception
     */
    private void chargeVersion() {
        if (request.judgmentVersion(21)) {
            addStatusBarApi= AddBarApiFactory.forLollipop(request);
        } else if (request.judgmentVersion(19)) {
            addStatusBarApi=AddBarApiFactory.forKik(request);
        }
    }

    protected void addBar(){
        chargeVersion();
        if(addStatusBarApi!=null){
            addStatusBarApi.addStatusBar();
        }
    }

    public void setLightBarIconColors(){
        IconColors.setStatusBarLightMode(activity);
    }
    public void setDarkBarIconColors(){
        IconColors.setStatusBarLightMode(activity);
    }
}
