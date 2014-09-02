package com.wang.ad;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyToggleButton extends View implements View.OnClickListener{

	private static final String TAG = "MyToggleButton";
	private Bitmap backgroundBitmap;
	private Bitmap slideBtnBitmap;
	private Paint paint;
	private float slideBtnLeft = 0;
	private boolean currentOnState = false;//当前开状态 false=关

	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MyToggleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 该对象在布局文件中声明的view，创建时由系统自动调用
	 * @param context
	 * @param attrs 自定义参数
	 */
	public MyToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initView();
		
		for(int i=0,len=attrs.getAttributeCount();i<len;i++){
			Log.i(TAG, "attr  name="+attrs.getAttributeName(i)+",value="+attrs.getAttributeValue(i)
					);
		}
		
		TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.MyToggleButton);
		for(int i=0,len=ta.getIndexCount();i<len;i++){
			switch(ta.getIndex(i)){
			case R.styleable.MyToggleButton_test_bitmap:
				Log.i(TAG, "MyToggleButton_test_bitmap="+ta.getDrawable(i));
				break;
			case R.styleable.MyToggleButton_test_id:
				Log.i(TAG, "MyToggleButton_test_msg="+ta.getInt(i,0));
				break;
			case R.styleable.MyToggleButton_test_msg:
				Log.i(TAG, "MyToggleButton_test_msg="+ta.getString(i));
				break;
			}
		}
	}

	private void initView() {
		//初始化图片
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
		slideBtnBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
		
		//初始化画笔
		paint=new Paint();
		paint.setAntiAlias(true);//打开抗锯齿
		
		//添加onClick事件监听
		setOnClickListener(this);
	}

	/**
	 * 在代码里面创建对象，使用这个构造方法
	 * @param context
	 */
	public MyToggleButton(Context context) {
		super(context);
	}
	
	/**
	 * 测量尺寸的时候回调
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/**
		 * 设置当前view的大小，
		 * width：view的宽度
		 * height：view的高度：单位像素
		 */
		setMeasuredDimension(backgroundBitmap.getWidth(),backgroundBitmap.getHeight());
	}
	
	/**
	 * 自定义view中作用不大
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,int bottom) {
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//绘制背景
		canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
		//绘制可滑动按钮
		canvas.drawBitmap(slideBtnBitmap, slideBtnLeft, 0,paint);
	}

	private boolean isDrag=false;//有没有拖动
	private float dragDis=10;
	@Override
	public void onClick(View v) {
		if(isDrag) return;//如果拖动了，就直接退出
		//1.点击之后要切换当前状态
		currentOnState = !currentOnState;
		flushState();
	}

	private void flushState() {
		if(currentOnState){
			slideBtnLeft = backgroundBitmap.getWidth() - slideBtnBitmap.getWidth();
		}else{
			slideBtnLeft = 0;
		}
		//刷当前视图新
		invalidate();//然后会调用onDraw方法
	}
	
	private float firstX;
	private float lastX;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			firstX=lastX= event.getX();
			break;
		case MotionEvent.ACTION_UP:
			lastX =  event.getX();
			//判断是否拖动
			if(isDrag = Math.abs(lastX-firstX) > dragDis){
				//如果拖动了，那么判断 是否拖过一半，来更改状态
				currentOnState = slideBtnLeft>(backgroundBitmap.getWidth() - slideBtnBitmap.getWidth())/2;
				flushState();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			//移动的时候
			float dis = event.getX()-lastX;
			lastX =  event.getX();
			float slideLeftTemp=slideBtnLeft+dis;
			slideBtnLeft =(slideLeftTemp>0 && slideLeftTemp <backgroundBitmap.getWidth() - slideBtnBitmap.getWidth())?slideLeftTemp:slideBtnLeft;
			break;
		}
		invalidate();//刷新视图
		return true;
	}
	
}
