package net.cs.appbaselibrary.base.RecyclerView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.cs.appbaselibrary.R;
import net.cs.appbaselibrary.base.BindingViewHolder;
import net.cs.appbaselibrary.interf.OnRecyclerAdapterListener;

import java.util.List;

/**
 * Created by cs on 2016/9/20.
 * Description: RecyclerView Adapter
 */
public abstract class BaseRecyclerAdapter<T> extends BaseQuickAdapter<T, BindingViewHolder> {

    private OnRecyclerAdapterListener listener;
    private RecyclerView recyclerView;
    private Context context;
    private View netErrorView;
    private View notDataView;
    private int pageSize = 10;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public BaseRecyclerAdapter(Context context, RecyclerView recyclerView, List<T> data) {
        super(data);
        this.recyclerView = recyclerView;
        this.context = context;
        setBaseView();
    }

    public BaseRecyclerAdapter(Context context, RecyclerView recyclerView, int layoutIdRes, List<T> data) {
        super(layoutIdRes, data);
        this.recyclerView = recyclerView;
        this.context = context;
        setBaseView();
    }


    private void setBaseView() {
        netErrorView = LayoutInflater.from(context)
                .inflate(R.layout.recycler_item_neterror, (ViewGroup) recyclerView.getParent(), false);

        netErrorView.findViewById(R.id.net_error_layout).setOnClickListener(v -> {
            if (listener != null){
                listener.onListRefresh();
            }
        });

        notDataView = LayoutInflater.from(context)
                .inflate(R.layout.recycler_item_nodata, (ViewGroup) recyclerView.getParent(), false);

        BaseCustomLoadMoreView customLoadMoreView = new BaseCustomLoadMoreView();
        customLoadMoreView.setLoadMoreEndGone(false);
        setLoadMoreView(customLoadMoreView);
        setAutoLoadMoreSize(3);
    }

    public void showNoDataView() {
        if (isEmptyView(notDataView)) {
            setEmptyView(notDataView);
        }
    }

    public void showNetWorkErrorView() {
        if (isEmptyView(netErrorView)) {
            setEmptyView(netErrorView);
        }
    }

    @Override
    public void addData(List<T> newData) {
        if (newData != null) {
            super.addData(newData);
        }
    }

    /**
     * 单页面数据
     */
    public void showList(List<T> listData) {
        setNewData(listData);

        if (listData == null || listData.size() == 0) {
            showNoDataView();
            return;
        }

        loadMoreEnd();
    }

    @Override
    public void setNewData(List<T> data) {
        super.setNewData(data);
        disableLoadMoreIfNotFullPage(recyclerView);
    }

    /**
     * 多页面数据
     */
    public void showList(List<T> listData, int pageNo) {
        if (listData == null || listData.size() == 0) {
            if (pageNo == 1) {
                setNewData(null);
                showNoDataView();
            }
            return;
        }

        if (pageNo == 1) {
            setNewData(listData);
        } else {
            addData(listData);
            loadMoreComplete();
        }

        if (listData.size() < getPageSize()) {
            loadMoreEnd();
        }
    }

    private boolean isEmptyView(View view) {
        return getEmptyView() != view;
    }
    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) return;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1) == getItemCount()) {
                        setEnableLoadMore(false);
                    }
                }
            }, 50);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
                    int pos = getTheBiggestNumber(positions) + 1;
                    if (pos == getItemCount()) {
                        setEnableLoadMore(false);
                    }
                }
            }, 50);
        }
    }
    private int getTheBiggestNumber(int[] numbers) {
        int tmp = -1;
        if (numbers == null || numbers.length == 0) {
            return tmp;
        }
        for (int num : numbers) {
            if (num > tmp) {
                tmp = num;
            }
        }
        return tmp;
    }
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    @Override
    protected BindingViewHolder createBaseViewHolder(View view) {
        return new BindingViewHolder(view);
    }

    public void addOnRecyclerAdapterListener(OnRecyclerAdapterListener listener){
        this.listener = listener;
    }
}
