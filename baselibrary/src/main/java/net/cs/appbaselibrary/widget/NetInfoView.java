package net.cs.appbaselibrary.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import net.cs.appbaselibrary.R;

/**
 * Created by Liang on 2017/4/7.
 */

public class NetInfoView extends FrameLayout{

    public NetInfoView(@NonNull Context context) {
        super(context);

        init(context);
    }

    public NetInfoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public NetInfoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_network_info, this);

        if (view.isInEditMode()) {
            return;
        }
    }

}
