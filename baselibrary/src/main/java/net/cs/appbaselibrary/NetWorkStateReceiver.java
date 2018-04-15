package net.cs.appbaselibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.cs.appbaselibrary.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

import static net.cs.appbaselibrary.NetWorkStateReceiver.NetWorkState.CONNECTED;
import static net.cs.appbaselibrary.NetWorkStateReceiver.NetWorkState.DISCONNECTED;

/**
 * Created by cs on 2017/4/5.
 */
public class NetWorkStateReceiver extends BroadcastReceiver {

    public enum NetWorkState{
        CONNECTED,
        DISCONNECTED;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (NetworkUtils.isConnected(context)){
            EventBus.getDefault().post(CONNECTED);
        }else {
            EventBus.getDefault().post(DISCONNECTED);
        }
    }
}
