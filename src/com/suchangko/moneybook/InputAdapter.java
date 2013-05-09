package com.suchangko.moneybook;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InputAdapter extends BaseAdapter {
	private LayoutInflater Inflater;
	private ArrayList<String> arrayList;
	private int _layout;
	
	public InputAdapter(Context c, int layout, ArrayList<String> list){
		Inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_layout=layout;
		arrayList=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
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
		String[] tmp_Strings = arrayList.get(arg0).split("#");
		TextView tv1 = (TextView)arg1.findViewById(R.id.list_tv_date);
		TextView tv2 = (TextView)arg1.findViewById(R.id.list_tv_kind);
		TextView tv3 = (TextView)arg1.findViewById(R.id.list_tv_memo);
		TextView tv4 = (TextView)arg1.findViewById(R.id.list_tv_money);
		tv1.setText(tmp_Strings[0]);
		tv2.setText(tmp_Strings[1]);
		tv3.setText(tmp_Strings[2]);
		tv4.setText(tmp_Strings[3]);
		return arg1;
	}

}
