package net.cs.appbaselibrary.base;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import net.cs.appbaselibrary.R;

/**
 * Created by Dino on 12/20 0020.
 */

public class BindingViewHolder extends BaseViewHolder {

    public BindingViewHolder(View view) {
        super(view);
        AutoUtils.autoSize(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding)getConvertView().getTag(R.id.BaseQuickAdapter_databinding_support);
    }

}
