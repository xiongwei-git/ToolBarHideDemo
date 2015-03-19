package com.tekinarslan.material.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/*
 * ScrollView并没有实现滚动监听，所以我们必须自行实现对ScrollView的监听
 */
public class CustomScrollView extends ScrollView {

    private int initialPosition;
    private int newCheck = 100;

    private ScrollViewListener onScrollListener;

    public CustomScrollView(Context context) {
        super(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


    }

    private Runnable scrollerTask = new Runnable() {
        public void run() {
            int newPosition = getScrollY();
            if (initialPosition - newPosition == 0) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStopped();
                }
            } else {
                initialPosition = getScrollY();
                CustomScrollView.this.postDelayed(scrollerTask, newCheck);
            }
        }
    };
    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(ScrollViewListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScrollListener != null) {
            onScrollListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void onScrollerStopListen(){
        initialPosition = getScrollY();
        CustomScrollView.this.postDelayed(scrollerTask, newCheck);
    }

    public interface ScrollViewListener {

        void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy);

        void onScrollStopped();

    }
}
