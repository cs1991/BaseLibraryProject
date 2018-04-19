package q.rorbin.verticaltablayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

import q.rorbin.badgeview.Badge;

/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public abstract class TabView extends FrameLayout implements Checkable, ITabView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
    public TabView(Context context) {
        super(context);
    }

    @Override
    public TabView getTabView() {
        return this;
    }

    @Deprecated
    public abstract ImageView getIconView();

    public abstract TextView getTitleView();

    public abstract Badge getBadgeView();
    @Override
    public AutoFrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new AutoFrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
        {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
