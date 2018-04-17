package net.cs.appbaselibrary.base.RecyclerView;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.cs.appbaselibrary.R;
import net.cs.appbaselibrary.base.BaseAppCompatActivity;
import net.cs.appbaselibrary.data.RecyclerDataRepository;
import net.cs.appbaselibrary.data.RecyclerDataSource;
import net.cs.appbaselibrary.data.local.LocalRecyclerDataSource;

/**
 * Created on 2016/10/23.
 * By cs@outlook.com
 *
 * @param <T> 是获取的数据类型
 */

public abstract class BaseRecyclerViewActivity<T> extends BaseAppCompatActivity implements BaseRecyclerViewContract.View<T>, RecyclerDataSource<T>, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    protected BaseRecyclerAdapter adapter;
    protected SwipeRefreshLayout swipeRefresh;
    protected RecyclerView recyclerView;
    private BaseRecyclerViewContract.Presenter recyclerPresenter;
    protected LinearLayoutManager mLinearLayoutManager;

    protected boolean refreshEveryTimes = true;

    @Override
    public void initRecyclerView() {
        recyclerPresenter = new BaseRecyclerViewPresenter(this,new RecyclerDataRepository(this, LocalRecyclerDataSource.getInstance()));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        adapter = adapter == null ? addListAdapter() : adapter;

        swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        swipeRefresh.setOnRefreshListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this);
        adapter.addOnRecyclerAdapterListener(() -> onRefresh());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (refreshEveryTimes) {
            recyclerPresenter.onListRefresh();
        }
    }

    @Override
    public void setListRefresh(boolean isShow) {
        swipeRefresh.setRefreshing(isShow);
    }

    @Override
    public void showNetworkFail() {
        adapter.showNetWorkErrorView();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerPresenter.subscribe();
    }

    @Override
    public void onListError(Throwable error) {

    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerPresenter.unSubscribe();
    }

    @Override
    protected void onDestroy() {
        recyclerPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        refreshEveryTimes = isRefreshEvertTimes();
    }

    public boolean isRefreshEvertTimes() {
        return true;
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
    public void onClick(View v) {
        onRefresh();
    }
}
