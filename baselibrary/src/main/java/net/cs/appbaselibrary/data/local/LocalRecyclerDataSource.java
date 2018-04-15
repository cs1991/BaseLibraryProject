package net.cs.appbaselibrary.data.local;

import android.support.annotation.Nullable;

import net.cs.appbaselibrary.data.RecyclerDataSource;

import io.reactivex.Observable;

/**
 * Created by cs@outlook.com
 * Date on 2016/11/23
 */

public class LocalRecyclerDataSource<T> implements RecyclerDataSource<T> {
    @Nullable
    private static LocalRecyclerDataSource INSTANCE;


    public static  LocalRecyclerDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalRecyclerDataSource<>();
        }
        return INSTANCE;
    }

    @Override
    public Observable<T> onListGetData(int pageNo) {
        return null;
    }
}
