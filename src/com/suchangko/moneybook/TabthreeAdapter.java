package com.suchangko.moneybook;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TabthreeAdapter extends BaseAdapter {
	private LayoutInflater Inflater;
	private int _layout;
	private int wholemoney=0;
	private int spendmoney=0;
	public TabthreeAdapter(Context c,int layout,int wholemoney,int spendmoney ){
		Inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_layout=layout;
		this.wholemoney = wholemoney;
		this.spendmoney = spendmoney;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1==null){
			arg1=Inflater.inflate(_layout, arg2,false);
		}
		
		return arg1;
	}

}
