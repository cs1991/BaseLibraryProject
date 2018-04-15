package net.cs.appbaselibrary.base.RecyclerView;

import android.support.annotation.NonNull;

import net.cs.appbaselibrary.data.RecyclerDataRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cs@outlook.com
 * Date on 2016/11/25
 */

public class BaseRecyclerViewPresenter<T> extends BaseRecyclerViewContract.Presenter {

    private int pageNo = 1;

    @NonNull
    private RecyclerDataRepository<T> repository;

    public BaseRecyclerViewPresenter(RecyclerDataRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    protected void initPresenter() {

    }

    public int getListPageNo() {
        return pageNo;
    }

    public void setListPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void onListRefresh() {
        if (getView() != null) {
            getView().setListRefresh(true);
        }
        setListPageNo(1);
        onListUpData(getListPageNo());

    }

    public void onListLoadMore() {
        setListPageNo(getListPageNo() + 1);
        onListUpData(getListPageNo());
    }

    public void onListUpData(final int pageNo) {
        Disposable disposable = repository
                .onListGetData(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<T>() {
                    @Override
                    public void onNext(T value) {
                        if(getView()!=null){
                            getView().setListRefresh(false);
                            getView().onListSuccess(value, pageNo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(getView() != null) {
                            getView().setListRefresh(false);
                            if (getListPageNo() == 1) {
                                getView().showNetworkFail();
                            }
                            getView().onListError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void onDestroy() {

    }
}
