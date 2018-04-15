package net.cs.appbaselibrary.base.RecyclerView;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.cs.appbaselibrary.R;
import net.cs.appbaselibrary.base.BaseFragment;
import net.cs.appbaselibrary.data.RecyclerDataRepository;
import net.cs.appbaselibrary.data.RecyclerDataSource;
import net.cs.appbaselibrary.data.local.LocalRecyclerDataSource;


/**
 * Created on 2016/10/23.
 * By cs@outlook.com
 *
 * @param <T> 是获取过来的数据类型
 */

public abstract class BaseRecyclerViewFragment<T> extends BaseFragment implements BaseRecyclerViewContract.View<T>, RecyclerDataSource<T>, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    protected BaseRecyclerAdapter adapter;
    protected SwipeRefreshLayout swipeRefresh;
    protected RecyclerView recyclerView;
    private BaseRecyclerViewContract.Presenter recyclerPresenter;
    protected LinearLayoutManager mLinearLayoutManager;
    protected boolean refreshEveryTimes = true;

    @Override
    public void initRecyclerView() {
        recyclerPresenter = new BaseRecyclerViewPresenter(new RecyclerDataRepository(this, LocalRecyclerDataSource.getInstance()));
        recyclerPresenter.attachView(this);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.swiperefresh);

        adapter = adapter == null ? addListAdapter() : adapter;

        swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        swipeRefresh.setOnRefreshListener(this);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this);
        adapter.addOnRecyclerAdapterListener(() -> onRefresh());
    }

    @Override
    public void setListRefresh(boolean isShow) {
        swipeRefresh.setRefreshing(isShow);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (refreshEveryTimes) {
            recyclerPresenter.onListRefresh();
        }
    }

    @Override
    public void onRefresh() {
        recyclerPresenter.onListRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        recyclerPresenter.onListLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerPresenter.unSubscribe();
    }

    @Override
    public void onDestroy() {
        recyclerPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        refreshEveryTimes = isRefreshEvertTimes();
    }

    public boolean isRefreshEvertTimes() {
        return true;
    }

    @Override
    public void showNetworkFail() {
        adapter.showNetWorkErrorView();
    }

    @Override
    public void onListError(Throwable error) {

    }
}
