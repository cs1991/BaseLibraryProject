package net.cs.appbaselibrary.widget.dialog;

/**
 * Created by cs@outlook.com
 * Date on 2016/11/17
 */

public class DialogCallback {

    // todo 列表型dialog回调方法
    OnDialogItemListener onDialogItemListener;

    public void addOnDialogItemListener(OnDialogItemListener onDialogItemListener) {
        this.onDialogItemListener = onDialogItemListener;
    }

    public interface OnDialogItemListener <T> {
        void onClick(int position, T itemBean);
    }

}
