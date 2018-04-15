package net.cs.appbaselibrary.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cs.appbaselibrary.base.mvp.BasePresenter;
import net.cs.appbaselibrary.base.mvp.MvpView;
import net.cs.appbaselibrary.utils.NetworkUtils;
import net.cs.appbaselibrary.utils.ToastUtils;
import net.cs.appbaselibrary.widget.dialog.DialogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cson 2016/6/22.
 */
public abstract class BaseFragment<V extends MvpView, P extends BasePresenter<V>> extends Fragment implements BaseViewInterface, MvpView {
    /**
     * P层引用
     */
    protected P mPresent;
    private Unbinder unbinder;
    private ViewDataBinding binding;

    protected abstract P getPresenter();

    protected abstract int getLayoutId();

    private DialogHelper dialogHelper;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return getView();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter层
        mPresent = (P) getPresenter();
        //做绑定
        if(mPresent != null){
            mPresent.attachView((V) this);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isUseButterKnife()){
            unbinder = ButterKnife.bind(this, getView());
        }

        initRecyclerView();
        init();
        initTabs();
    }

    @Override
    public boolean isUseButterKnife() {
        return true;
    }


    @Subscribe
    public void onEvent(String string) {

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
        if (NetworkUtils.isConnected(getContext())){
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
        ToastUtils.showToast( toast);
    }

    protected ViewDataBinding getBinding() {
        return binding;
    }

    @Override
    public View getView() {
        return binding.getRoot();
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

        if (dialogHelper != null) {
            dialogHelper.dismissProgressDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(mPresent!=null) {
            mPresent.detachView();
            mPresent = null;
        }
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    public void nextActivity(Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }

    public void nextActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
