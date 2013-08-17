package com.suchangko.moneybook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private boolean moneyview1,moneyview2;
	Calendar calendar;
	
	public TabthreeAdapter(Context c,Calendar calendar,int layout,ArrayList<Integer> wholemoneyarrayList,ArrayList<Integer> spendmonetArrayList,ArrayList<String> nameArrayList,ArrayList<Integer> budgetArrayList,boolean moneyview1,boolean moneyview2){
		Inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_layout=layout;
	
		this.calendar=calendar;
		this.wholemoneyarrayList = wholemoneyarrayList;
		this.spendmonetArrayList = spendmonetArrayList;
		this.nameArrayList = nameArrayList;
		this.budgetArrayList = budgetArrayList;
		this.moneyview1=moneyview1;
		this.moneyview2=moneyview2;
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
		grecal=new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		int Day = calendar.get(Calendar.DAY_OF_MONTH);
		int LastDay = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		Calendar cc = Calendar.getInstance();
		
		Date d1 = new Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		Date d2 = new Date(cc.get(Calendar.YEAR)-1900,cc.get(Calendar.MONTH),cc.get(Calendar.DAY_OF_MONTH));
		
		
		
		
		int budgeti = budgetArrayList.get(arg0);
		ProgressBar progressBar = (ProgressBar)arg1.findViewById(R.id.progressBar1);
		TextView tv_title = (TextView)arg1.findViewById(R.id.tv_title);
		TextView tv_money = (TextView)arg1.findViewById(R.id.textView1);
		TextView tv1 = (TextView)arg1.findViewById(R.id.tv1);
		TextView tv2 = (TextView)arg1.findViewById(R.id.tv2);
		TextView tv_1 = (TextView)arg1.findViewById(R.id.tv1_1);
		TextView tv_2 = (TextView)arg1.findViewById(R.id.tv2_1);
		TextView tv_3 = (TextView)arg1.findViewById(R.id.tv3_1);
		TextView tv3 = (TextView)arg1.findViewById(R.id.tv3);
		TextView tv4 = (TextView)arg1.findViewById(R.id.tv4);
		TextView tv_4 = (TextView)arg1.findViewById(R.id.tv4_1);
		int moneyperday = (int)budgeti/LastDay;		
		int days = LastDay-Day;
		if(days==0){
			days=1;
		}
		
		
		int budgetperday = (budgeti-spendmonetArrayList.get(arg0))/days;
		tv_money.setText(String.format("%,d",budgeti)+"원");
		tv_title.setText("| "+nameArrayList.get(arg0)+" |");
		tv_1.setText(String.format("%,d",moneyperday)+"원");
		tv_2.setText(spendmonetArrayList.get(arg0)+"원");
		tv_3.setText(String.format("%,d",(budgeti-spendmonetArrayList.get(arg0)))+"원");
		tv3.setText("이번 달 남은 예산(D-"+(LastDay-Day)+")");
		tv_4.setText(String.format("%,d",budgetperday)+"원");
		int spendmoney = spendmonetArrayList.get(arg0);
		Log.d("spendmoney",""+spendmoney);
		Log.d("bugeti",""+budgeti);
		if(budgeti==0){
			progressBar.setProgress(0);
		}else{
			long percentlong = spendmoney*100/budgeti;
			Log.d("",percentlong+"");
			int tmpt = (int)percentlong;
			int first=0;
			int second=0;
			first=tmpt;
			if(tmpt>101){
				second=tmpt-100;
				first=100;tv_1.setText(0+"원");
				tv_4.setText(0+"원");
			}
			progressBar.setSecondaryProgress(first);
			progressBar.setProgress(second);

		}
		if(d1.getTime()<d2.getTime()){
			tv3.setText("이번 달 남은 예산(D-0)");
		}
		if(!moneyview1){
			tv1.setVisibility(View.GONE);
			tv_1.setVisibility(View.GONE);
			tv2.setVisibility(View.GONE);
			tv_2.setVisibility(View.GONE);
		}
		if(!moneyview2){
			tv3.setVisibility(View.GONE);
			tv_3.setVisibility(View.GONE);
			tv4.setVisibility(View.GONE);
			tv_4.setVisibility(View.GONE);
		}

		return arg1;
	}

}