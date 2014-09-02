package com.wang.ad;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopActivity extends Activity {
	
	private EditText et_content;
	
	private ListView lv_emps;
	
	private PopupWindow popWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pop);
		
		et_content=(EditText) findViewById(R.id.et_content);
		
		initListView();
		
		popWindow = new PopupWindow(this);
		popWindow.setContentView(lv_emps);
		
	}
	
	public void showPop(View v){
		
		//popWindow;
		//popWindow.setContentView(lv_emps);
		popWindow.setWidth(et_content.getWidth());
		popWindow.setHeight(600);
		popWindow.showAsDropDown(et_content);
	}

	/**
	 * 初始化listview
	 */
	private void initListView() {
		final List<String>names=new ArrayList<String>();
		for(int i=0;i<100;i++){
			names.add("name--"+i);
		}
		
		lv_emps=new ListView(this);
		lv_emps.setAdapter(new BaseAdapter() {
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				ViewHolder holder;
				if(convertView==null){
					convertView=View.inflate(getApplicationContext(), R.layout.emp_list_item, null);
					holder=new ViewHolder();
					holder.tv_emp_name=(TextView) convertView.findViewById(R.id.tv_emp_name);
					holder.iv_delete=(ImageView) convertView.findViewById(R.id.iv_delete);
					
					convertView.setTag(holder);
				}else{
					holder=(ViewHolder) convertView.getTag();
				}
				holder.tv_emp_name.setText(names.get(position));
				holder.tv_emp_name.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						popWindow.dismiss();
						et_content.setText(names.get(position));
					}
					
				});
				final BaseAdapter ba=this;
				holder.iv_delete.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						names.remove(position);
						ba.notifyDataSetChanged();
					}
				});
				
				return convertView;
			}
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return names.get(position);
			}
			
			@Override
			public int getCount() {
				return names.size();
			}
		});
	}
	
	private class ViewHolder{
		TextView tv_emp_name;
		ImageView iv_delete;
	}
	
	
	
}
