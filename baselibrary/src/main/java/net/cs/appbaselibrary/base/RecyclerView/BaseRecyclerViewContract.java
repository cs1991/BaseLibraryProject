package net.cs.appbaselibrary.base.RecyclerView;

import net.cs.appbaselibrary.base.mvp.BasePresenter;
import net.cs.appbaselibrary.base.mvp.MvpPresenter;
import net.cs.appbaselibrary.base.mvp.MvpView;

/**
 * Created by cs@outlook.com
 * Date on 2016/11/25
 */

public interface BaseRecyclerViewContract {
    /**
     * view接口层  处理界面
     */
    public interface View<T> extends MvpView {

        void setListRefresh(boolean isShow);

        void onListSuccess(T t, int pageNo);

        void onListError(Throwable error);

        BaseRecyclerAdapter addListAdapter();
    }

    /**
     * Presenter接口层 处理业务
     */
    interface Presenter extends MvpPresenter{
         int getListPageNo();

         void setListPageNo(int pageNo);

         void onListRefresh();

         void onListLoadMore();

         void onListUpData(int pageNo);
    }
}
