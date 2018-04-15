package net.cs.appbaselibrary.widget.dialog.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.cs.appbaselibrary.R;
import net.cs.appbaselibrary.widget.dialog.dialogbean.DialogBean;

import java.util.List;

/**
 * Created by cs
 * Date on 2016/11/21
 */

public class RecyclerViewAdapter extends BaseQuickAdapter<DialogBean, BaseViewHolder> {
    public RecyclerViewAdapter(int layoutResId, List<DialogBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DialogBean dialogBean) {
        baseViewHolder.setText(R.id.text, dialogBean.getText());
    }
}
