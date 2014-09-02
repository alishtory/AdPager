package com.wang.ad;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager mViewPager;

	private LinearLayout pointGroup;

	private TextView imgDesc;
	
	private static final int[] imgIds={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
	
	private static final String[]adStrs={"字幕111","字幕222222","字幕33333333","字幕4444444","字幕555555"};

	private static final int SCOLL_VIEW_PAGER = 0;
	
	private List<ImageView> imgViewList;

	private int lastPositiion=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = (ViewPager) findViewById(R.id.viewPager);

		pointGroup = (LinearLayout) findViewById(R.id.point_group);

		imgDesc = (TextView) findViewById(R.id.img_desc);
		imgDesc.setText(adStrs[0]);//初始化文字内容
		
		imgViewList=new ArrayList<ImageView>();
		
		int i=0;
		for(int id:imgIds){
			ImageView img=new ImageView(this);
			img.setBackgroundResource(id);
			imgViewList.add(img);
			
			//添加指示点
			ImageView point=new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			params.rightMargin = 20;
			point.setLayoutParams(params);
			point.setBackgroundResource(R.drawable.point_bg);
			
			if(i++==0){
				point.setEnabled(false);
			}else{
				point.setEnabled(true);
			}
			pointGroup.addView(point);
		}
		
		try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true); 
            Interpolator sInterpolator = new AccelerateDecelerateInterpolator();
            MyScroller scroller = new MyScroller(this, sInterpolator);
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

		mViewPager.setAdapter(new MyPagerAdapter());
		//初始化
		mViewPager.setCurrentItem(Integer.MAX_VALUE/2/imgViewList.size()*imgViewList.size());
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * 页面切换的时候调用
			 */
			@Override
			public void onPageSelected(int position) {
				position = position % imgViewList.size();
				imgDesc.setText(adStrs[position]);
				//把上一个页面的位置的点设置成
				pointGroup.getChildAt(position).setEnabled(false);
				//
				pointGroup.getChildAt(lastPositiion).setEnabled(true);
				lastPositiion=position;
			}
			/**
			 * 页面正在滑动的时候回调
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			/**
			 * 页面滑动状态改变的时候
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		isAutoScoll=true;
		handler.sendEmptyMessageDelayed(SCOLL_VIEW_PAGER, 2000);
	}
	/**判断是否自动滚动**/
	private boolean isAutoScoll=false;
	
	@Override
	protected void onDestroy() {
		isAutoScoll=false;
		super.onDestroy();
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case SCOLL_VIEW_PAGER:
				mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
				if(isAutoScoll)handler.sendEmptyMessageDelayed(SCOLL_VIEW_PAGER, 2000);
				break;
			}
		};
	};

	private class MyPagerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			//return imgViewList.size();
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//ImageView img=imgViewList.get(position);
			ImageView img=imgViewList.get(position % imgViewList.size());
			container.addView(img);
			return img;
		}
		
		/**
		 * 销毁对应位置上面的object
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
			object = null;
		}
		
	}
	
	public void goPop(View v){
		startActivity(new Intent(this, PopActivity.class));
	}
	public void goCustomer(View v){
		startActivity(new Intent(this, CustomerActivity.class));
	}
	public void goGroupViewDemo(View v){
		startActivity(new Intent(this, GroupViewDemoActivity.class));
	}
}
