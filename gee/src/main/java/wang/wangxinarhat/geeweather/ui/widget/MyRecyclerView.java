package wang.wangxinarhat.geeweather.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wang on 2016/4/8.
 */
public class MyRecyclerView extends RecyclerView{

    private static String TAG = MyRecyclerView.class.getSimpleName();


    private static final int INVALID_POINTER = -1;

    private int mTouchSlop;
    private int mActivePointerId = INVALID_POINTER;

    private NestedScrollingChildHelper mChildHelper;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        mChildHelper = new NestedScrollingChildHelper(this);
//        setNestedScrollingEnabled(true);
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    //    @Override
//    public void setNestedScrollingEnabled(boolean enabled) {
//        mChildHelper.setNestedScrollingEnabled(enabled);
//    }
//
//    @Override
//    public boolean isNestedScrollingEnabled() {
//        return mChildHelper.isNestedScrollingEnabled();
//    }
//
//    @Override
//    public boolean startNestedScroll(int axes) {
//        return mChildHelper.startNestedScroll(axes);
//    }
//
//    @Override
//    public void stopNestedScroll() {
//        mChildHelper.stopNestedScroll();
//    }
//
//    @Override
//    public boolean hasNestedScrollingParent() {
//        return mChildHelper.hasNestedScrollingParent();
//    }
//
//    @Override
//    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
//        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
//        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
//        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
//    }
//
//    @Override
//    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
//        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
//    }
//
//    private float mLastMotionY;
//    private final int[] mScrollOffset = new int[2];
//    private final int[] mScrollConsumed = new int[2];
//    private boolean mIsBeginDrag = false;
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final int action = MotionEventCompat.getActionMasked(ev);
//        switch (action) {
//            case MotionEvent.ACTION_DOWN: {
//                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
//                final float initialDownY = getMotionEventY(ev, mActivePointerId);
//                if (initialDownY == -1) {
//                    return false;
//                }
//                mLastMotionY = initialDownY;
//                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
//                super.onInterceptTouchEvent(ev);
//                mIsBeginDrag = false;
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                if (mActivePointerId == INVALID_POINTER) {
//                    return false;
//                }
//                final float y = getMotionEventY(ev, mActivePointerId);
//                if (y == -1) {
//                    return false;
//                }
//                int deltaY = (int) (mLastMotionY - y);
//                mLastMotionY = y;
//                if (Math.abs(deltaY) >= mTouchSlop) {
//                    mIsBeginDrag = true;
//                }
//                if (mIsBeginDrag && dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
//                    mLastMotionY -= mScrollOffset[1];
//                    deltaY -= mScrollConsumed[1];
//                    ev.offsetLocation(0, mScrollConsumed[1]);
//                    if (dispatchNestedScroll(0, 0, 0, deltaY, mScrollOffset)) {
//                        mLastMotionY -= mScrollOffset[1];
//                        ev.offsetLocation(0, mScrollOffset[1]);
//                    }
//                    return false;
//                } else {
//                    return super.onInterceptTouchEvent(ev);
//                }
//            }
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP: {
//                stopNestedScroll();
//                mActivePointerId = INVALID_POINTER;
//                mIsBeginDrag = false;
//                return super.onInterceptTouchEvent(ev);
//            }
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
//
//    private float getMotionEventY(MotionEvent ev, int activePointerId) {
//        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
//        if (index < 0) {
//            return -1;
//        }
//        return MotionEventCompat.getY(ev, index);
//    }



}
