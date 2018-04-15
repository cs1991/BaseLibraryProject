package net.cs.androidbaseapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import net.cs.appbaselibrary.base.BindingViewHolder;
import net.cs.appbaselibrary.base.RecyclerView.BaseRecyclerAdapter;
import net.cs.appbaselibrary.base.RecyclerView.BaseRecyclerViewActivity;
import net.cs.appbaselibrary.base.mvp.BasePresenter;
import net.cs.appbaselibrary.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

public class Test_BaseRecyclerViewActivity extends BaseRecyclerViewActivity<List<String>> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void init() {
        super.init();

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemClick(adapter, view, position);
                String string = (String) baseQuickAdapter.getData().get(position);
                ToastUtils.showSnackbar(getView(), string);
                ToastUtils.showToast(string);
            }

            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_recycler_view;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    /**
     * 列表适配器
     * 默认一页页数为10，可使用setPageSize(int pageSize)修改
     */
    @Override
    public BaseRecyclerAdapter addListAdapter() {
        return new RecyclerAdapter(this, recyclerView, null);
    }

    /**
     * 请求成功时的回调方法;
     * onSuccess返回的数据为完整的请求数据，需要自己拆解列表数据，添加到适配器里
     */
    @Override
    public void onListSuccess(List<String> strings, int pageNo) {

        //单页使用
        //adapter.showList(strings);

        //多页使用
        List<String> myStrings  = strings.subList(0,5);
        adapter.showList(myStrings);
    }


    /**
     * 获取网络数据接口，注意返回的是被观察者对象
     */
    @Override
    public Observable<List<String>> onListGetData(int pageNo) {

        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 10; i++) {
            String string = sdf.format(new Date());
            list.add(string);
        }
        return Observable.just(list);
    }

    /**
     * 带bind的适配器
     */
    class RecyclerAdapter extends BaseRecyclerAdapter<String> {

        public RecyclerAdapter(Context context, RecyclerView recyclerView, List data) {
            super(context, recyclerView, R.layout.item_base_recyclerview_layout, data);
        }

        @Override
        protected void convert(BindingViewHolder bindingViewHolder, String s) {
            bindingViewHolder.setText(R.id.data, s);
        }
    }

}
