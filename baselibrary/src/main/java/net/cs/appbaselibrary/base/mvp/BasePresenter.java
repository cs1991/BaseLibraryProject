package net.cs.appbaselibrary.base.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lenovo on 2017/1/4.
 */

public abstract class BasePresenter<V extends MvpView> {
    protected Reference<V> mViewRef;//View 接口类型的弱引用
    @NonNull
    protected CompositeDisposable disposables;

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
        unSubscribe();
        disposables = null;
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
    public void addDispose(Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);//将所有 Disposable 放入集中处理
    }
    /**
     * 为测试而写的方法，无需做任何操作
     * @return
     */
    public static Object getInstance(){
        return null;
    }

}
