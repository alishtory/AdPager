package com.wang.ad;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class MyScroller extends Scroller{
	
	private int mDuration = 500;
	
	public MyScroller(Context context) {
		super(context);
	}
	
	public MyScroller(Context context,Interpolator interpolator) {
		super(context,interpolator);
	}
	
	
	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		super.startScroll(startX, startY, dx, dy, mDuration);
	}

}
