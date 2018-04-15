package net.cs.appbaselibrary.base;

import android.content.ComponentName;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;


import com.dino.changeskin.SkinManager;

import net.cs.appbaselibrary.AppManager;
import net.cs.appbaselibrary.NetWorkStateReceiver;
import net.cs.appbaselibrary.base.mvp.BasePresenter;
import net.cs.appbaselibrary.base.mvp.MvpView;
import net.cs.appbaselibrary.utils.Immersive;
import net.cs.appbaselibrary.utils.NetworkUtils;
import net.cs.appbaselibrary.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cshuiyong@outlook.com on 2016/5/25.
 */
public abstract class BaseAppCompatActivity<V extends MvpView, P extends BasePresenter<V>> extends AppCompatActivity implements BaseViewInterface, MvpView {
    /**
     * P层引用
     */
    protected P mPresent;
    private Unbinder unbinder;

    protected abstract int getLayoutId();

    protected abstract P getPresenter();

    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
//是否允许全屏
        if (isAllowFullScreen()) {
            //隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        //判断是否允许全屏
        if (isOnlyShowStatusBar()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        //判断是否设置了沉浸式效果
        if (isSteepStatusBar()) {
            steepStatusBar();
        }
        //换肤注册
        SkinManager.getInstance().register(this);

        binding = DataBindingUtil.setContentView(this, getLayoutId());

        if (isUseButterKnife()){
            unbinder = ButterKnife.bind(this, getView());
        }
        EventBus.getDefault().register(this);

        //创建Presenter层
        mPresent = getPresenter();
        //做绑定
        if(mPresent != null){
            mPresent.attachView((V) this);
        }

        initRecyclerView();
        init();
        initTabs();

        //初始化网络状态
        initNetWorkInfo(NetworkUtils.isConnected(this));
    }

    @Override
    public boolean isUseButterKnife() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getPresenter() != null) {
            getPresenter().subscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getPresenter() != null) {
            getPresenter().unSubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresent!=null) {
            mPresent.detachView();
            mPresent = null;
        }
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().finishActivity(this);
        //换肤注册
        SkinManager.getInstance().unregister(this);
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    protected ViewDataBinding getBinding() {
        return binding;
    }

    public View getView() {
        return binding.getRoot();
    }

    /**
     * 是否允许全屏,此处全屏是指将状态栏都隐藏掉
     */
    public boolean isAllowFullScreen() {
        return false;
    }

    /**
     * 是否允许全屏,此处全屏是指将状态栏显示
     */
    public boolean isOnlyShowStatusBar() {
        return false;
    }
    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        Immersive.steepStatusBar(this);
    }
    /**
     * [是否设置沉浸状态栏]
     */
    public boolean isSteepStatusBar() {
        return true;
    }

    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar                 顶部导航
     * @param bottomNavigationBar     底部导航
     * @param translucentPrimaryColor 沉浸式效果的主题颜色
     */
    public void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar, int translucentPrimaryColor) {
        Immersive.setOrChangeTranslucentColor(toolbar, bottomNavigationBar, translucentPrimaryColor, this);
    }
    @Override
    public void init() {

    }

    @Override
    public void initRecyclerView() {

    }

    @Override
    public void initTabs() {

    }

    @Override
    public void showNetworkFail() {
        if (NetworkUtils.isConnected(getBaseContext())){
            showToast("加载失败!");
        }else {
            showToast("网络不给力，请检查网络设置!");
        }
    }

    @Override
    public void showNetworkFail(String err) {
        showToast(err);
    }

    @Override
    public void showToast(String toast) {
        ToastUtils.showToast(toast);
    }

    public void nextActivity(Class<?> firstCls, Class<?> SecondCls) {
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(this, SecondCls));
        intents[1] = new Intent(this, firstCls);
        startActivities(intents);
    }

    public void nextActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void nextActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    public void nextActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void nextActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    //网络状态监听
    @Subscribe
    public void onEvent(NetWorkStateReceiver.NetWorkState state) {

            switch (state){
                case CONNECTED:
                    initNetWorkInfo(true);
                    break;

                case DISCONNECTED:
                    initNetWorkInfo(false);
                    break;
            }
    }

    /**
     * 网络状态，可以复写来处理
     * @param isConnect
     */
    protected void initNetWorkInfo(boolean isConnect){

    }

}
