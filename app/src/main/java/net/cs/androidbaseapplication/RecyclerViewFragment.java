package net.cs.androidbaseapplication;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.cs.appbaselibrary.base.BindingViewHolder;
import net.cs.appbaselibrary.base.RecyclerView.BaseRecyclerAdapter;
import net.cs.appbaselibrary.base.RecyclerView.BaseRecyclerViewFragment;
import net.cs.appbaselibrary.base.mvp.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;


/**
 * A simple {@link BaseRecyclerViewFragment} subclass.
 */
public class RecyclerViewFragment extends BaseRecyclerViewFragment<List<String>> {

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    public void onListSuccess(List<String> strings, int pageNo) {
        adapter.showList(strings, pageNo);
    }

    @Override
    public BaseRecyclerAdapter addListAdapter() {
        return new RecyclerAdapter(getContext(), recyclerView, null);
    }

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
