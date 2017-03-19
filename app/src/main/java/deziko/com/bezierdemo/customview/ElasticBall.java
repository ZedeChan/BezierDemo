package deziko.com.bezierdemo.customview;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ElasticBall extends View {

    private Paint mPaint;

    private Path mPath;

    private float mTime = -1.0f;

    //屏幕高度
    private int mScreenHeight;
    //屏幕宽度
    private int mScreenWidth;

    private float c = 0.551915024494f;

    private float radius = 100;

    private HorizontalLine mTopLine,mBottomLine;

    private VerticalLine mLeftLine,mRightLine;

    public ElasticBall(Context context) {
        this(context,null);
    }

    public ElasticBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPoint();
    }

    private void initPaint() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#59c3e2"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 2.0f);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTime = (Float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    private void initPoint() {
        mTopLine = new HorizontalLine(0,-radius);
        mBottomLine = new HorizontalLine(0,radius);
        mLeftLine = new VerticalLine(-radius,0);
        mRightLine = new VerticalLine(radius,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        if(mTime < 0) {
            canvas.translate(mScreenWidth / 2, mScreenHeight / 2);
        } else if(mTime > 0 && mTime <= 1) {
            canvas.translate(mScreenWidth / 2,mScreenHeight/2 + (mScreenHeight/2-radius)*mTime);
        } else if(mTime > 1 && mTime <= 2) {
            canvas.translate(mScreenWidth/2,(mScreenHeight-radius)-((mScreenHeight/2 - radius)*(mTime-1)));
        }

        changeCircleCoordinate(mTime);
        mPath.moveTo(mTopLine.middle.x,mTopLine.middle.y);
        mPath.cubicTo(mTopLine.right.x,mTopLine.right.y,mRightLine.top.x,mRightLine.top.y,
                mRightLine.middle.x,mRightLine.middle.y);
        mPath.cubicTo(mRightLine.bottom.x,mRightLine.bottom.y,mBottomLine.right.x,mBottomLine.right.y,
                mBottomLine.middle.x,mBottomLine.middle.y);
        mPath.cubicTo(mBottomLine.left.x,mBottomLine.left.y,mLeftLine.bottom.x,mLeftLine.bottom.y,
                mLeftLine.middle.x,mLeftLine.middle.y);
        mPath.cubicTo(mLeftLine.top.x,mLeftLine.top.y,mTopLine.left.x,mTopLine.left.y,
                mTopLine.middle.x,mTopLine.middle.y);
        canvas.drawPath(mPath,mPaint);
    }

    class HorizontalLine {
        public PointF left = new PointF();
        public PointF middle = new PointF();
        public PointF right = new PointF();

        public HorizontalLine(float x,float y) {
            left.x = -radius*c;
            left.y = y;
            middle.x = x;
            middle.y = y;
            right.x = radius*c;
            right.y = y;
        }

        public void setY(float y) {
            left.y = y;
            middle.y = y;
            right.y = y;
        }

    }

    class VerticalLine {
        public PointF top = new PointF();
        public PointF middle = new PointF();
        public PointF bottom = new PointF();


        public VerticalLine(float x,float y) {
            top.x = x;
            top.y = -radius*c;
            middle.x = x;
            middle.y = y;
            bottom.x = x;
            bottom.y = radius*c;
        }


        public void setX(float x) {
            top.x = x;
            middle.x = x;
            bottom.x = x;
        }
    }

    private void changeCircleCoordinate(float mTime) {
        float mTempTime;

        if(mTime > 1) {
            mTempTime = mTime - 1;
        } else {
            mTempTime = mTime;
        }

        if(mTempTime > 0.1 && mTempTime <= 0.2) {

            if(mTime > 1) {
                mBottomLine.setY(radius - mTempTime*radius*5);
            } else {
                mTopLine.setY(-radius + mTempTime*radius*5);
            }

        } else {
            mTopLine.setY(-radius);
            mBottomLine.setY(radius);
            mTopLine.middle.x = mBottomLine.middle.x = 0;
            mTopLine.left.x = mBottomLine.left.x = -radius*c;
            mTopLine.right.x = mBottomLine.right.x = radius*c;
            mRightLine.setX(radius);
            mLeftLine.setX(-radius);
            mRightLine.middle.y = mLeftLine.middle.y = 0;
            mRightLine.top.y = mLeftLine.top.y = -radius*c;
            mRightLine.bottom.y = mLeftLine.bottom.y = radius*c;
        }
    }

}
