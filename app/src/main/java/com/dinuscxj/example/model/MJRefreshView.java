package com.dinuscxj.example.model;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dinuscxj.example.R;
import com.dinuscxj.refresh.IRefreshStatus;


/**
 * Created by zhangyang131 on 2017/8/21.
 */

public class MJRefreshView extends View implements IRefreshStatus {
  private static final int ANIMATION_DURATION = 888;
  private static final int DEFAULT_STROKE_WIDTH = 3;

  private final Paint mPaint = new Paint();

  // private float mStartDegrees;
  // private float mSwipeDegrees;
  int color = Color.parseColor("#FF979899");
  int count = 12; // 进度条线数量
  int w, h;
  // 大园
  float radius;
  float centerX;
  float centerY;
  int yr; // 大园初始半径；
  float swipeProgress, pullDistance;
  private Path path = new Path();
  Drawable drawable;
  int animPointer;// 1~count

  private boolean mHasTriggeredRefresh;
  private boolean mHasPullOff;

  private ValueAnimator mRotateAnimator;

  public MJRefreshView(Context context) {
    this(context, null);
  }

  public MJRefreshView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MJRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initPaint();
  }

  private void initPaint() {
    mPaint.setAntiAlias(true);
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
    mPaint.setStrokeCap(Paint.Cap.ROUND);
  }

  private void startAnimator() {
    mRotateAnimator = ValueAnimator.ofInt(0, count);
    mRotateAnimator.setInterpolator(new LinearInterpolator());
    mRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int animPointer = (int) animation.getAnimatedValue();
        setanimPointer(animPointer);
      }
    });
    mRotateAnimator.setRepeatMode(ValueAnimator.RESTART);
    mRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
    mRotateAnimator.setDuration(ANIMATION_DURATION);
    mRotateAnimator.start();
  }

  private void setanimPointer(int animPointer) {
    if(this.animPointer != animPointer){
      this.animPointer = animPointer;
      postInvalidate();
    }
  }

  public int getRefreshTargetOffset() {
    final DisplayMetrics metrics = getResources().getDisplayMetrics();
    return (int) (70 * metrics.density);
  }

  private void resetAnimator() {
    if (mRotateAnimator != null) {
      mRotateAnimator.cancel();
      mRotateAnimator.removeAllUpdateListeners();
      mRotateAnimator = null;
      animPointer = 0;
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawArc(canvas);
  }

  @Override
  protected void onDetachedFromWindow() {
    resetAnimator();
    super.onDetachedFromWindow();
  }


  private void setPullProgress(float pullDistance, float swipeProgress) {
    this.swipeProgress = swipeProgress;
    this.pullDistance = pullDistance;
    postInvalidate();
  }


  private void drawArc(Canvas canvas) {
    // canvas.drawArc(mArcBounds, mStartDegrees, mSwipeDegrees, false, mPaint);
    if (mHasTriggeredRefresh || mHasPullOff) {
      float x = w / 2f;
      float y = h * 3f / 4;
      float r = yr / 1.3f;
      for (int i = 0; i < count; i++) {
        // 绘制下层菊花
        float a = ((count-i + animPointer) % (count + 1f)) / count;
        int alpha = (int) (0xff - (0xc0 * a));
        Log.i("alpha", alpha+"");
        mPaint.setColor(Color.argb(alpha, 0x00, 0x00, 0x00));
        canvas.drawLine(x, y - r, x, y - 0.65f * r, mPaint);
        canvas.rotate(360 / count, x, y);

      }
    } else {
      mPaint.setColor(color);
      int yrd;
      if (swipeProgress < 0.5) {
        yrd = yr;
      } else {
        yrd = (int) (yr * (1.15 - 0.3 * swipeProgress));
      }
      Circle c1 = new Circle(w / 2, h * 3 / 4, yrd);
      Circle c2 =
          new Circle(w / 2, h * 5.7f / 5 - pullDistance, yrd / 1.6f * (1.6f - swipeProgress));

      path.reset();
      float deltaX = c2.x - c1.x;
      float deltaY = -(c2.y - c1.y);
      double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
      double sin = deltaY / distance;
      double cos = deltaX / distance;

      path.moveTo((float) (c1.x - c1.r * sin), (float) (c1.y - c1.r * cos));
      path.lineTo((float) (c1.x + c1.r * sin), (float) (c1.y + c1.r * cos));
      path.quadTo((c1.x + c2.x + c1.r) / 2, (c1.y + c2.y) / 2, (float) (c2.x + c2.r * sin),
          (float) (c2.y + c2.r
              * cos));
      path.lineTo((float) (c2.x - c2.r * sin), (float) (c2.y - c2.r * cos));
      path.quadTo((c1.x + c2.x - c1.r) / 2, (c1.y + c2.y) / 2, (float) (c1.x - c1.r * sin),
          (float) (c1.y - c1.r
              * cos));

      if (swipeProgress >= 0.4 && swipeProgress < 1) {
        canvas.drawCircle(c2.x, c2.y, c2.r, mPaint);
        canvas.drawPath(path, mPaint);
      }
      canvas.drawCircle(c1.x, c1.y, c1.r, mPaint);
      if (drawable == null) {
        drawable = getResources().getDrawable(R.drawable.ic_refresh);
      }
      Rect src = new Rect();
      src.left = (int) (c1.x - c1.r) + 8;
      src.top = (int) (c1.y - c1.r) + 8;
      src.right = (int) (c1.x + c1.r - 8);
      src.bottom = (int) (c1.y + c1.r - 8);
      drawable.setBounds(src);
      drawable.draw(canvas);
    }

  }

  class Circle {
    float x;
    float y;
    float r;

    public Circle(float x, float y, float r) {
      this.x = x;
      this.y = y;
      this.r = r;
    }

    public double getDistance(Circle c) {
      float deltaX = x - c.x;
      float deltaY = y - c.y;
      double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
      return distance;
    }
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    this.w = w;
    this.h = h;
    radius = Math.min(w, h) / 2.0f;
    centerX = w / 2.0f;
    centerY = h / 2.0f;
    yr = h / 5;
  }

  @Override
  public void reset() {
    resetAnimator();
    mHasTriggeredRefresh = false;
    mHasPullOff = false;
  }

  @Override
  public void refreshing() {
    mHasTriggeredRefresh = true;
    startAnimator();
  }

  @Override
  public void refreshComplete() {

  }

  @Override
  public void pullToRefresh() {

  }

  @Override
  public void releaseToRefresh() {}

  @Override
  public void pullProgress(float pullDistance, float pullProgress) {
    if(pullProgress>1){
      mHasPullOff = true;
    }
    if (!mHasTriggeredRefresh) {
      float swipeProgress = Math.min(1.0f, pullProgress);
      setPullProgress(pullDistance, swipeProgress);
    }
  }
}
