package net.cs.appbaselibrary.data;

import io.reactivex.Observable;

/**
 * Created by cs@outlook.com
 * Date on 2016/12/23
 */

public interface RecyclerDataSource<T> {
    Observable<T> onListGetData(int pageNo);
}
