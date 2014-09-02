package com.wang.ad;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class GroupViewDemoActivity extends Activity {

	private MyScrollView msv;
	
	private int[]ids={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_view_demo);
		
		msv=(MyScrollView) findViewById(R.id.my_scroll_view); 
		
		for(int i=0;i<ids.length;i++){
			ImageView img=new ImageView(this);
			img.setBackgroundResource(ids[i]);
			msv.addView(img);
		}
		
	}
}
