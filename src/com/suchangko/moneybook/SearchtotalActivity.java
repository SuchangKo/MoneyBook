package com.suchangko.moneybook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.suchangko.moneybook.SearcheActivity.ListViewAdapter1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchtotalActivity extends Activity {
	Intent i;	
	ListView lv;
	ArrayList<String> dateArrayList;
	ArrayList<String> whereArrayList;
	ArrayList<String> priceArrayList;
	MoneyBookDB mdb;
	MoneyInputDB inputdb;
	int searchcnt=0;
	ListViewAdapter1 adapter1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchs);
        i=getIntent();
        String datetext = i.getStringExtra("datetext");
        int code = i.getIntExtra("code",0);
        
        dateArrayList=new ArrayList<String>();
        whereArrayList=new ArrayList<String>();
        priceArrayList=new ArrayList<String>();
       
        
        
        lv = (ListView)findViewById(R.id.listview1);
        
        madeAdapter(code,datetext);
        
        adapter1 = new ListViewAdapter1(getApplicationContext(), R.layout.listrow_search,dateArrayList,whereArrayList,priceArrayList);
        lv.setAdapter(adapter1);
        TextView tv = (TextView)findViewById(R.id.tv_title);
        tv.setText("지출 검색 결과 : "+searchcnt+"개");
        
    }
    public void madeAdapter(int code,String datetext){
		dateArrayList.clear();
		whereArrayList.clear();
		priceArrayList.clear();
		String[] datestrs = datetext.split("-");
		String year = datestrs[0];
		String month = datestrs[1];
		GregorianCalendar gregorianCalendar = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),1);
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(year),Integer.parseInt(month),1);
		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt(year),Integer.parseInt(month),gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		Date d1 = new Date(c1.get(Calendar.YEAR)-1900,c1.get(Calendar.MONTH)-1,1);
		Date d2 = new Date(c2.get(Calendar.YEAR)-1900,c2.get(Calendar.MONTH)-1,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Cursor c=null;
		
		if(code==1){
			String SQL = "SELECT * FROM "+mdb.SQL_DBname + " WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime();
			mdb=new MoneyBookDB(getApplicationContext(), mdb.SQL_Create_Moneybook,mdb.SQL_DBname);
			mdb.open();
			c =mdb.RawQueryString(SQL);
		}else if(code==2){
			String SQL = "SELECT * FROM "+inputdb.SQL_DBname + " WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime();
			inputdb=new MoneyInputDB(getApplicationContext(), inputdb.SQL_Create_Moneybook,inputdb.SQL_DBname);
			inputdb.open();
			c =inputdb.RawQueryString(SQL);
		}
		
		if(c.getCount()>0){
			if(c.moveToFirst()){
				do{
					Date d3 = new Date(Long.parseLong(c.getString(c.getColumnIndex("date"))));
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	        	String str_tmp_date = formatter.format(d3);
    	        	
    				dateArrayList.add(str_tmp_date);
        			whereArrayList.add(c.getString(c.getColumnIndex("memo")));
        			priceArrayList.add(c.getString(c.getColumnIndex("money")));
        			searchcnt++;
				}while(c.moveToNext());
			}
		}
    }


		
    class ListViewAdapter1 extends BaseAdapter{
    	private LayoutInflater Inflater;
    	private int _layout;
    	private ArrayList<String> a;
    	private ArrayList<String> b;
    	private ArrayList<String> d;
    	public ListViewAdapter1(Context c, int layout,ArrayList<String> a,ArrayList<String> b,ArrayList<String> d){
    		Inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		_layout=layout;
    		this.a=a;
    		this.b=b;
    		this.d=d;
    		
    	}
    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		return a.size();
    		//return 19;
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
    		TextView tv1 = (TextView)arg1.findViewById(R.id.searchrow_date);
    		TextView tv2 = (TextView)arg1.findViewById(R.id.searchrow_price);
    		TextView tv3 = (TextView)arg1.findViewById(R.id.searchrow_where);
    		
    		tv1.setText(a.get(arg0));
    		tv2.setText("￦"+d.get(arg0));
    		tv3.setText(b.get(arg0));
    		
    		return arg1;
    	}
    	
    }
 }
    

