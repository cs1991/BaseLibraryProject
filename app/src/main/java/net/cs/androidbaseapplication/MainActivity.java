package net.cs.androidbaseapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.socks.library.KLog;

import net.cs.appbaselibrary.NetWorkStateReceiver;
import net.cs.appbaselibrary.base.BaseAppCompatActivity;
import net.cs.appbaselibrary.base.mvp.BasePresenter;
import net.cs.appbaselibrary.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.verticaltablayoutdemo.MainActivity_Tab;

public class MainActivity extends BaseAppCompatActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void init() {
        ToastUtils.showSuccessToast("Success");
        ToastUtils.showErrorToast("Error");
        ToastUtils.showToast("Toast");
        KLog.d("abcde","11111111111111111111111");
        KLog.d("abcde","11111111111111111111111");
        KLog.d("abcde","11111111111111111111111");
        KLog.d("abcde","11111111111111111111111");
        KLog.d("abcde","11111111111111111111111");
        KLog.d("abcde","11111111111111111111111");
        KLog.d("abcde","11111111111111111111111");
        KLog.d("abcde","11111111111111111111111");
        KLog.d("11111111111111111111111");
        KLog.d("11111111111111111111111");
        KLog.d("11111111111111111111111");
        KLog.d("11111111111111111111111");
    }

    @OnClick({R.id.baseRecyclerViewActivity, R.id.tablayout, R.id.baseRecyclerViewFragment, R.id.net_RecyclerViewActivity})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.baseRecyclerViewActivity:
                intent.setClass(this, Test_BaseRecyclerViewActivity.class);
                startActivity(intent);
                break;

            case R.id.baseRecyclerViewFragment:
                intent.setClass(this, Test_BaseRecyclerViewFragment.class);
                startActivity(intent);
                break;

            case R.id.net_RecyclerViewActivity:
                intent.setClass(this, NetRecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tablayout:
                intent.setClass(this, MainActivity_Tab.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        startNetReciver();
    }

    //动态启动网络监听广播
    private void startNetReciver(){
        IntentFilter mFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetWorkStateReceiver mReceiver = new NetWorkStateReceiver();
        registerReceiver(mReceiver, mFilter);
    }
}
