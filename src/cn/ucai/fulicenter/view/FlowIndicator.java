package cn.ucai.fulicenter.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import cn.ucai.fulicenter.R;

public class FlowIndicator extends View {

    int mCount;//指示器中实心圆数量
    int mRadius;//实心圆半径
    int mSpace;//实心圆之前的距离
    int mFocus;//当前实心圆的颜色
    int mNormalColor;//非焦点实心圆的颜色
    int mFocusColor;//焦点实心圆的颜色
    
    public FlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowIndicator);
        mNormalColor = array.getColor(R.styleable.FlowIndicator_normal_color,
                0xffffff);
        mFocusColor = array.getColor(R.styleable.FlowIndicator_focus_color,
                0xffff07);
        mSpace = array.getDimensionPixelOffset(R.styleable.FlowIndicator_space,
                6);
        mRadius = array.getDimensionPixelOffset(
                R.styleable.FlowIndicator_r, 9);
        mCount=array.getInt(R.styleable.FlowIndicator_count,1);
        mFocus=array.getInt(R.styleable.FlowIndicator_focus, 0);
        array.recycle();
    }

    /**
     * 返回指示器实心圆数量
     * @return
     */
    public int getCount(){
        return this.mCount;
    }
    
    /**
     * 设置指示器实心圆数量
     */
    public void setCount(int count){
        this.mCount=count;
        invalidate();
    }
    
    /**
     * 设置当前焦点的下标
     * @param focus
     */
    public void setFocus(int focus){
        this.mFocus=focus;
        invalidate();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }
    
    /**
     * 测量宽度
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec){
        int result=0;
        int mode= MeasureSpec.getMode(widthMeasureSpec);
        int spaceSize= MeasureSpec.getSize(widthMeasureSpec);
        if(mode== MeasureSpec.EXACTLY){
            result=spaceSize;
        }else{
            result=(int)(getLeftPaddingOffset()+getRightFadingEdgeStrength())
                +2*mRadius*mCount+(mCount-1)*mSpace;
            result= Math.min(result, spaceSize);
        }
        return result;
    }
    
    /**
     * 测量高度
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec){
        int result=0;
        int mode= MeasureSpec.getMode(heightMeasureSpec);
        int spaceSize= MeasureSpec.getSize(heightMeasureSpec);
        if(mode== MeasureSpec.EXACTLY){
            result=spaceSize;
//            result=285;
        }else{
            result=(int)(getTopPaddingOffset()+getBottomFadingEdgeStrength())+2*mRadius;
            result= Math.min(result, spaceSize);
        }
        return result;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int leftSpace=(getWidth()-(mSpace+mRadius)*2*mCount-(mCount-1)*mSpace)/2;
        for(int i=0;i<mCount;i++){
            int x=getLeftPaddingOffset()+leftSpace+i*(2*mRadius+mSpace)+mRadius;
            int color=mFocus==i?mFocusColor:mNormalColor;
            paint.setColor(color);
            canvas.drawCircle(x, getHeight()/2, mRadius, paint);
        }
    }

}

/**
 * 标识轮播图片的实心圆
 * @author yao
 */
/*public class FlowIndicator extends View {
    int mCount;
    int mNormalColor;
    int mFocusColor;
    int mSpace;
    int mRadius;
    int mFocus;

    public FlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.FlowIndicator);
        mNormalColor = array.getColor(R.styleable.FlowIndicator_normal_color,
                0xffffff);
        mFocusColor = array.getColor(R.styleable.FlowIndicator_focus_color,
                0xffff07);
        mSpace = array.getDimensionPixelOffset(R.styleable.FlowIndicator_space,
                6);
        mRadius = array.getDimensionPixelOffset(
                R.styleable.FlowIndicator_r, 9);
        mCount=array.getIndex(R.styleable.FlowIndicator_count);
        mFocus=array.getInt(R.styleable.FlowIndicator_focus, 0);
        array.recycle();
    }

    public void setCount(int count) {
        mCount = count;
        invalidate();
    }

    public int getCount() {
        return mCount;
    }

    public void setFocus(int focus) {
        mFocus = focus;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpce) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpce);
        int specSize = MeasureSpec.getSize(widthMeasureSpce);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (getLeftPaddingOffset()+ getRightFadingEdgeStrength() 
                + mCount * 2 * mRadius + (mCount - 1) * mSpace);
            result = Math.min(specSize, result);
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (getTopPaddingOffset()
                    + getBottomFadingEdgeStrength() + 2 * mRadius);
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int leftSpace = (getWidth() - mCount * 2 * (mRadius+mSpace) - (mCount - 1)
                * mSpace) / 2;
        for (int i = 0; i < mCount; i++) {
            int color = i == mFocus ? mFocusColor : mNormalColor;
            paint.setColor(color);
            int x = getLeftPaddingOffset() + leftSpace + i
                    * (2 * mRadius + mSpace) + mRadius;
            canvas.drawCircle(x, getHeight() / 2, mRadius, paint);
        }
    }
}*/