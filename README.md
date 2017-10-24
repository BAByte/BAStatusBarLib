# BAStatusBarLib

## 将状态栏的一些基本操作都封装了，谁叫我懒

 @Function : 实现每个Activity可定制化3种沉浸状态栏的功能封装类

1. 安卓4.4：实现纯色状态栏，注意！当在安卓4.4实现侧滑菜单时，如果用到了CoordinatorLayout，要给该布局设置android:fitsSystemWindows="true"属性
2. 安卓4.4：实现activity背景延伸到状态栏显示
3. 安卓5.0以上：实现纯色状态栏
4. 都实现了实现activity背景延伸到状态栏显示
5. 封装了在将控件延伸到状态栏显示时需要设置的主题状态栏透明设置
6. 隐藏状态栏

## 提供的调用接口

```java
/**
 * @return
 * @throws
 * @fuction 设置纯色状态栏
 * @parm 将要设置的颜色资源Id
 */
public  void setColorToBar(AppCompatActivity activity, int colorResId) {}

/**
 * @return
 * @throws
 * @fuction 设置半透明状态栏，一般是在将Activity的背景设置成图片时使用
 * @parm
 */
public  void setTranslucentBar(AppCompatActivity activity) {}

/**
 * @return
 * @throws
 * @fuction 扩展到状态栏显示只能在安卓5.0以上，能用的布局也有限
 * coordinatorLayout,AppBarLayout,CollapsingToolbarLayout这种布局才有效
 * @parm
 */
@TargetApi(21)
public  void setfitsSystemWindowsBar(AppCompatActivity activity) {}

/**
 *@fuction 隐藏状态栏
 *@parm activity 用来获取Window
 *@return
 *@exception
 */
public void setFullScreen(AppCompatActivity activity){}
```

---

## 用法

~~~java
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
      
      //全屏
        new BAStatusBar().setFullScreen(this);
      
      //设置纯色
        new BAStatusBar().setColorToBar(this,R.color.colorPrimaryDark);
      
      //将Activity背景延伸到状态栏显示
      	new BAStatusBar().setTranslucentBar(this);

      //将控件延伸到状态栏显示，需要将被延伸的控件以及父布局设置下面的属性，根布局可以不设置这个属性
      //android:fitsSystemWindows="true"
        new BAStatusBar().setfitsSystemWindowsBar(this);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
~~~

---

##导入方法

Add it in your root build.gradle at the end of repositories:

~~~java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
~~~

**Step 2.** Add the dependency

~~~java
dependencies {
  		...
		compile 'com.github.ljh998:BAStatusBarLib:1.0.1'
	}
~~~

