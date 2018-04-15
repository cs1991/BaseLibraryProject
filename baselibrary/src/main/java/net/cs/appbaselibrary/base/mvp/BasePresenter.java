package net.cs.appbaselibrary.base.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by lenovo on 2017/1/4.
 */

public abstract class BasePresenter<V extends MvpView> {
    protected Reference<V> mViewRef;//View 接口类型的弱引用
    @NonNull
    protected CompositeDisposable disposables = new CompositeDisposable();

    protected abstract void initPresenter();
    public void attachView(MvpView view) {
        mViewRef = new WeakReference<V>((V) view);
    }

    protected V getView() {
        if(mViewRef != null){
            return mViewRef.get();
        }else {
            return null;
        }

    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        onDestroy();
    }

    //释放资源处理
    public abstract void onDestroy();

    public void subscribe() {

    }

    public void unSubscribe() {
        if (disposables != null && disposables.size() > 0){
            disposables.clear();
        }
    }

    /**
     * 为测试而写的方法，无需做任何操作
     * @return
     */
    public static Object getInstance(){
        return null;
    }

}
