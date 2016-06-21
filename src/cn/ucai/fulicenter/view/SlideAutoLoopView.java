package cn.ucai.fulicenter.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.ImageLoader.OnImageLoadListener;
import cn.ucai.fulicenter.utils.Utils;

/**
 * 图片轮播
 * @author yao
 */
public class SlideAutoLoopView extends ViewPager {
    Context mContext;
    /** 自动播放的标识符*/
    final int ACTION_PLAY=1;
    /**定义FlowIndicator:图片指示器view*/
    FlowIndicator mFlowIndicator;
    /** 轮播图片的适配器*/
    SlideAutoLooopAdapter mAdapter;
    /** 图片数量*/
    int mCount;
    /** 图片轮播间隔时间*/
    int mDuration=2000;
    /** 相册的图片下载地址数组*/
    String[] mAlbumImgUrl;
    Timer mTimer;
    Handler mHandler;
    boolean mAutoSwitch=false;
    
    public SlideAutoLoopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initHandler();
        setListener();
    }

    private void setListener() {
        setOnPageChangeListener();
        setOnTouchListener();
    }

    /**
     * 设置触摸页面的事件监听
     */
    private void setOnTouchListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()== MotionEvent.ACTION_DOWN
                    || event.getAction()== MotionEvent.ACTION_MOVE){
                    mAutoSwitch=false;
                }
                return false;
            }
        });
    }

    /**
     * 监听ViewPager页面改变
     */
    private void setOnPageChangeListener() {
        this.setOnPageChangeListener(new OnPageChangeListener() {
            
            @Override
            public void onPageSelected(int position) {
                //设置指示器中实心圆的切换
                mFlowIndicator.setFocus(position%mCount);
            }
            
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                
            }
        });
    }

    private void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==ACTION_PLAY){//若是播放操作
                    if(!mAutoSwitch){//若不是自动播放状态
                        mAutoSwitch=true;//设置为自动播放状态
                    }else{//设置为下一个item
                        //取出当前item的下标
                        int currentItem = SlideAutoLoopView.this.getCurrentItem();
                        currentItem++;//递增
                        //设置当前item为下一个
                        SlideAutoLoopView.this.setCurrentItem(currentItem);
                    }
                }
            }
       };
    }

    /**
     * 轮播图片的适配器
     * @author yao
     *
     */
    class SlideAutoLooopAdapter extends PagerAdapter {
        Context context;
        String[] albumImgUrl;
        int count;
        ImageLoader imageLoader;
        
        public SlideAutoLooopAdapter(Context context, String[] albumImgUrl,
                                     int count) {
            super();
            this.context = context;
            this.albumImgUrl = albumImgUrl;
            this.count = count;
            imageLoader= ImageLoader.getInstance(context);
        }

        @Override
        public int getCount() {//支持无限轮播
            if(count==0){
                return 0;
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            
            return arg0==arg1;
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ImageView iv=new ImageView(context);
            LayoutParams params=new LayoutParams();
            iv.setLayoutParams(params);
            String imgUrl=albumImgUrl[position%count];
            String imgName="images/"+imgUrl;
            String url= I.DOWNLOAD_ALBUM_IMG_URL+imgUrl;
            Bitmap bitmap = imageLoader.displayImage(url, imgName, Utils.px2dp(context, 260), Utils.px2dp(context, 200), new OnImageLoadListener() {
                @Override
                public void onSuccess(String path, Bitmap bitmap) {
                    iv.setImageBitmap(bitmap);
                }
                
                @Override
                public void error(String errorMsg) {
                    // TODO Auto-generated method stub
                    
                }
            });
            if(bitmap==null){
                iv.setImageResource(R.drawable.nopic);
            }else{
                iv.setImageBitmap(bitmap);
            }
            container.addView(iv);
            return iv;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
    
    /**
     * 开始图片的轮播
     */
    public void startPlayLoop(FlowIndicator flowIndicator, String[] albumImgUrl, int count){
        if(mAdapter==null){
            mCount=count;
            this.mFlowIndicator=flowIndicator;
            mFlowIndicator.setCount(count);
            mFlowIndicator.setFocus(0);
            this.mAlbumImgUrl=albumImgUrl;
            mAdapter=new SlideAutoLooopAdapter(mContext, mAlbumImgUrl, count);
            this.setAdapter(mAdapter);
            
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                MyScroller scroller=new MyScroller(mContext, new LinearInterpolator());
                scroller.setDuration(500);
                scroller.startScroll(0, 0, 50, 0);
                field.set(this, scroller);
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(mTimer==null){
            mTimer=new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(ACTION_PLAY);
            }
        }, 1,mDuration);
    }
    
    /**
     * 停止图片轮播
     */
    public void stopPlayLoop(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
    }
    
    /**
     * ViewPager列表项滚动的距离、时间间隔的设置
     * @author yao
     *
     */
    class MyScroller extends Scroller {
        public MyScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
            // TODO Auto-generated constructor stub
        }

        int duration;//图片移动的时间间隔
        public void setDuration(int duration){
            this.duration=duration;
        }
        
        @Override
        public void startScroll(int startX, int startY, int dx, int dy,
                int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }
}
