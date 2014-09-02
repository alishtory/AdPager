package com.wang.ad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;

public class MyScrollView extends ViewGroup {

	private Context mContext;
	
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
		initView();
	}

	private void initView() {
		this.mDetector=new GestureDetector(mContext, new OnGestureListener(){
			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			/**
			 * 手指滑动
			 */
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				//移动屏幕  参考当前位置 ，移动
				scrollBy((int)distanceX, 0);
				//scrollTo  移动到指定位置
				
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				return false;
			}});
	}

	/**
	 * 对view进行布局，确定子view的位置
	 * @param changed true->说明布局发生了变化 This is a new size or position for this view
     * @param left Left position, relative to parent
     * @param top Top position, relative to parent
     * @param right Right position, relative to parent
     * @param bottom Bottom position, relative to parent
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for(int i=0;i<getChildCount();i++){
			View view=getChildAt(i);//取第i个子view
			//左上右下位置
			view.layout(0+i*getWidth(), 0, getWidth()+i*getWidth(),getHeight() );
		}
	}
	
	private GestureDetector mDetector;
	
	 
	private float beginX = 0;
	private int currId=0;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		//使用工具类
		//mDetector.
		mDetector.onTouchEvent(event);
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			beginX=event.getX();
			break;
		case MotionEvent.ACTION_UP:
			if(beginX-event.getX()>getWidth()*2/5){
				currId=(++currId>=getChildCount())?--currId:currId;
			}else if(event.getX()-beginX>getWidth()*2/5){
				currId=(--currId<0)?0:currId;
			}
			flushViewLocation();
			break;
		case MotionEvent.ACTION_MOVE:
			
			break;
		}
		
		return true;
	}

	private void flushViewLocation(){
		scrollTo(currId*getWidth(), 0);
	}

}
