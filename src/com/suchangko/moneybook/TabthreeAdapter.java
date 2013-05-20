package com.suchangko.moneybook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TabthreeAdapter extends BaseAdapter {
	private LayoutInflater Inflater;
	private int _layout;
	private ArrayList<Integer> wholemoneyarrayList;
	private ArrayList<Integer> spendmonetArrayList;
	private ArrayList<String> nameArrayList;
	private ArrayList<Integer> budgetArrayList;
	Calendar calendar;
	public TabthreeAdapter(Context c,Calendar calendar,int layout,ArrayList<Integer> wholemoneyarrayList,ArrayList<Integer> spendmonetArrayList,ArrayList<String> nameArrayList,ArrayList<Integer> budgetArrayList){
		Inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_layout=layout;
		this.calendar=calendar;
		this.wholemoneyarrayList = wholemoneyarrayList;
		this.spendmonetArrayList = spendmonetArrayList;
		this.nameArrayList = nameArrayList;
		this.budgetArrayList = budgetArrayList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return wholemoneyarrayList.size();
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
		GregorianCalendar grecal;
		grecal=new GregorianCalendar();
		int Day = calendar.get(Calendar.DAY_OF_MONTH);
		int LastDay = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int budgeti = budgetArrayList.get(arg0);
		ProgressBar progressBar = (ProgressBar)arg1.findViewById(R.id.progressBar1);
		TextView tv_title = (TextView)arg1.findViewById(R.id.tv_title);
		TextView tv_money = (TextView)arg1.findViewById(R.id.textView1);
		TextView tv_1 = (TextView)arg1.findViewById(R.id.tv1_1);
		TextView tv_2 = (TextView)arg1.findViewById(R.id.tv2_1);
		TextView tv_3 = (TextView)arg1.findViewById(R.id.tv3_1);
		TextView tv3_3 = (TextView)arg1.findViewById(R.id.tv3);
		TextView tv_4 = (TextView)arg1.findViewById(R.id.tv4_1);
		int moneyperday = (int)budgeti/LastDay;
		int budgetperday = (budgeti-spendmonetArrayList.get(arg0))/(LastDay-Day);
		tv_money.setText(budgeti+"원");
		tv_title.setText("| "+nameArrayList.get(arg0)+" |");
		tv_1.setText(moneyperday+"원");
		tv_2.setText(spendmonetArrayList.get(arg0)+"원");
		tv_3.setText((budgeti-spendmonetArrayList.get(arg0))+"원");
		tv3_3.setText("이번 달 남은 예산(D-"+(LastDay-Day)+")");
		tv_4.setText(budgetperday+"원");
		
		
		int spendmoney = spendmonetArrayList.get(arg0);
		Log.d("spendmoney",""+spendmoney);
		Log.d("bugeti",""+budgeti);
		if(budgeti==0){
			progressBar.setProgress(0);
		}else{
			long percentlong = spendmoney*100/budgeti;
			Log.d("",percentlong+"");
			progressBar.setProgress((int)percentlong);
		}
		//long percentlong = spendmoney/budgeti*100;
		
	//	long percentlong = spendmonetArrayList.get(arg0)/budgeti;
	//	Log.d("",""+percentlong);
		
		/*
		if(budgetArrayList.get(arg0)==0){
			progressBar.setProgress(0);
		}else{
			progressBar.setProgress(percent);
		}
		*/
		return arg1;
	}

}
