package net.cs.androidbaseapplication;

import android.support.v4.app.FragmentTransaction;

import net.cs.appbaselibrary.base.BaseAppCompatActivity;
import net.cs.appbaselibrary.base.mvp.BasePresenter;

public class Test_BaseRecyclerViewFragment extends BaseAppCompatActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test__base_recycler_view_fragment;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void init() {
        super.init();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, new RecyclerViewFragment());
        transaction.commit();
    }
}
