package com.app.mohamedgomaa.arabic_books.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.app.mohamedgomaa.arabic_books.R;
import com.xenione.libs.swipemaker.AbsCoordinatorLayout;
import com.xenione.libs.swipemaker.SwipeLayout;

public class HaftRightCoordinatorLayout extends AbsCoordinatorLayout {
    private View mBackgroundView;
    private SwipeLayout mForegroundView;
    public HaftRightCoordinatorLayout(Context context) {
        super(context);
    }
    @Override
    public void doInitialViewsLocation() {
        mBackgroundView=findViewById(R.id.backgroundView);
        mForegroundView=findViewById(R.id.foregroundView);
        mForegroundView.anchor(mBackgroundView.getRight(),mBackgroundView.getLeft());
    }
    public HaftRightCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HaftRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HaftRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onTranslateChange(float globalPercent, int index, float relativePercent) {

    }
}
