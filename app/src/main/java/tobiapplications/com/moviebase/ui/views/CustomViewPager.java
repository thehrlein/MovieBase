package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Tobias on 22.06.2017.
 */

public class CustomViewPager extends ViewPager {

    private boolean isSwipeDisabled = false;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !isSwipeDisabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return !isSwipeDisabled && super.canScrollHorizontally(direction);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !isSwipeDisabled && super.performClick();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void disableSwipe(boolean disable) {
        isSwipeDisabled = disable;
    }
}
