package com.suchangko.moneybook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

public class SearchsActivity extends Activity {
	Intent i;
	int[] cs;
	int[] cf;
	String searchstr;
	String kind;
	ListView lv;
	ArrayList<String> dateArrayList;
	ArrayList<String> whereArrayList;
	ArrayList<String> priceArrayList;
	MoneyInputDB mdb;
	int searchcnt=0;
	ListViewAdapter1 adapter1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchs);
        i=getIntent();
        cs = i.getIntArrayExtra("cs");
        cf = i.getIntArrayExtra("cf");
        searchstr = i.getStringExtra("str");
        kind =i.getStringExtra("kind"); 
        
        dateArrayList=new ArrayList<String>();
        whereArrayList=new ArrayList<String>();
        priceArrayList=new ArrayList<String>();
       
        
        mdb =  new MoneyInputDB(this,MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
        mdb.open();
        lv = (ListView)findViewById(R.id.listview1);
        Log.d("cs",cs[0]+"년"+cs[1]+"월"+cs[2]+"일");
        Log.d("cf",cf[0]+"년"+cf[1]+"월"+cf[2]+"일");
        Log.d("str",searchstr);
        madeAdapter();
        
        adapter1 = new ListViewAdapter1(getApplicationContext(), R.layout.listrow_search,dateArrayList,whereArrayList,priceArrayList);
        lv.setAdapter(adapter1);
        TextView tv = (TextView)findViewById(R.id.tv_title);
        tv.setText("지출 검색 결과 : "+searchcnt+"개");
    }
    public void madeAdapter(){
		dateArrayList.clear();
		whereArrayList.clear();
		//Log.d("Error","Error2");
		if(cs[0]==0){
			String SQLstr = "SELECT * FROM "+mdb.SQL_DBname+" WHERE memo LIKE '%"+searchstr+"%'";
			Cursor c = mdb.RawQueryString(SQLstr+" ORDER BY date DESC");
			if(c.getCount()!=0){
				if(c.moveToFirst()){
        			do{
        				Date a = new Date(Long.parseLong(c.getString(4)));
        				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	        	String str_tmp_date = formatter.format(a);
        	        	
        				dateArrayList.add(str_tmp_date);
	        			whereArrayList.add(c.getString(2));
	        			priceArrayList.add(c.getString(3));
	        			searchcnt++;
	        				    	  
		        	}while(c.moveToNext());
        			
        		}
			}
		}else{
		//	Log.d("Error","Error3");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.set(cs[0],cs[1]-1,cs[2]);
			c2.set(cf[0],cf[1]-1,cf[2]+1);
		//	Log.d("Error","Error4");
			
			int a1 = c1.get(Calendar.DAY_OF_MONTH);
        	int year_1 = c1.get(Calendar.YEAR);
        	int month_1 = c1.get(Calendar.MONTH)+1;
        	Date tmp_date1 = new Date(year_1-1900, month_1-1, a1);
        	
        	
        	int a2 = c2.get(Calendar.DAY_OF_MONTH);
        	int year_2 = c2.get(Calendar.YEAR);
        	int month_2 = c2.get(Calendar.MONTH)+1;
        	Date tmp_date2 = new Date(year_2-1900, month_2-1, a2);
        	
        	
        	String SQLstr = "SELECT * FROM "+mdb.SQL_DBname+" WHERE memo LIKE '%"+searchstr+"%' AND date BETWEEN "+String.valueOf(tmp_date1.getTime())+" AND "+String.valueOf(tmp_date2.getTime());
      
        	
        		if(kind.equals("전체")){
        			
        		}else{
        			SQLstr+=" AND kindof LIKE "+kind+"%";
        		}
        	
        	
    		
        	Cursor c = mdb.RawQueryString(SQLstr+" ORDER BY date DESC");
			if(c.getCount()!=0){
				if(c.moveToFirst()){
        			do{
        				Date a = new Date(Long.parseLong(c.getString(4)));
        				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	        	String str_tmp_date = formatter.format(a);
        	        	
        				dateArrayList.add(str_tmp_date);
	        			whereArrayList.add(c.getString(2));
	        			priceArrayList.add(c.getString(3));
	        			searchcnt++;
	        				    	  
		        	}while(c.moveToNext());
        			
        		}
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
    

