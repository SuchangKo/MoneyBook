package com.suchangko.moneybook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 4. 3
 * Time: 오후 8:55
 * To change this template use File | Settings | File Templates.
 */
public class Tab_FourActivity extends Activity implements OnClickListener {
	
	Button bt_next;
	
	Button bt_pre;
	Button btdate;
	Button bt4;
	Button bt5;
	Button bt6;
	TextView tv_title2,tv_title3;
	ListView listView;
	ListViewAdapter adapter;
	MoneyInputDB inputDB;
	MoneyBookDB moneyBookDB;
	Calendar c;
	GregorianCalendar grecal;
	ArrayList<String> arrayList1;
	ArrayList<String> arrayList2;
	ArrayList<String> arrayList3;
	int lastday_1=0;
	int lastday_2=0;
	int statisticscode=1;
	Calendar calendar_1_bt;
	Calendar calendar_2_bt;
	boolean kind_month = true;
	SimpleDateFormat simpleDateFormat;
	//1 = 지출/전체  2=수입/전체
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = Calendar.getInstance();
        inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
        moneyBookDB =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
        inputDB.open();
        moneyBookDB.open();
        setContentView(R.layout.activity_tab4);
        bt_next=(Button)findViewById(R.id.button2);
        bt_pre=(Button)findViewById(R.id.button1);
        bt_next.setOnClickListener(this);
        bt_pre.setOnClickListener(this);
        tv_title2 = (TextView)findViewById(R.id.tv_title_2);
        tv_title3 = (TextView)findViewById(R.id.tv_title_3);
        tv_title2.setText("지출 금액");
        btdate = (Button)findViewById(R.id.button3);
        
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        calendar_1_bt =  Calendar.getInstance();
        calendar_1_bt.set(Calendar.MONTH,calendar_1_bt.get(Calendar.MONTH)-11);
        calendar_1_bt.set(Calendar.DAY_OF_MONTH,1);
        calendar_2_bt =  Calendar.getInstance();
        calendar_2_bt.set(Calendar.DAY_OF_MONTH,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        
      
        
        
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
      
        btdate.setText(dateString);
        
        btdate.setOnClickListener(this);
        bt4=(Button)findViewById(R.id.button4);
        bt5=(Button)findViewById(R.id.button5);
        bt6=(Button)findViewById(R.id.button6);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        listView = (ListView)findViewById(R.id.listview1);
        makeAdapter(statisticscode);
        adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(statisticscode!=3){
				String datetext = adapter.getDateString(arg2);
				Intent intent = new Intent(getApplicationContext(),SearchtotalActivity.class);
				intent.putExtra("datetext",datetext);
				intent.putExtra("code",statisticscode);
				startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), "합산통계에서는 상세보기를 지원하지 않습니다",Toast.LENGTH_SHORT).show();
				}
			}
        	
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
    	menu.add(0,0,0,"GPS").setIcon(android.R.drawable.ic_menu_compass);
    	menu.add(0,1,0,"도움말").setIcon(android.R.drawable.ic_menu_help);
    	
	return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int itemid =item.getItemId();
		switch (itemid) {
		case 0:
			//Toast.makeText(this,"수입입력",100).show();
			break;
		case 1:
			//Toast.makeText(this,"이전문자등록",100).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
   void makeAdapter(int code){
	   arrayList1 = new ArrayList<String>();
	   arrayList2 = new ArrayList<String>();
	   arrayList3 = new ArrayList<String>();
	   int allllllll=0;
	   if(statisticscode==1){
		   int month = c.get(Calendar.MONTH);
		   for(int i=0;i<12;i++){
			   Calendar calendar;
			   calendar = (Calendar) calendar_2_bt.clone();
			   calendar.set(Calendar.MONTH,month-i);
			   
			   grecal = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
			   int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
			   int ii=0;
			   
			 
			   int tmp_spendint=0;
			   
			   int year_ = calendar.get(Calendar.YEAR);
		       int month_ = calendar.get(Calendar.MONTH)+1;
		       lastday_1=0;
			  
			   
			  
			 //  while(ii<Lastday){
				   int a = Lastday;
				   Date d1= new Date(year_-1900, month_-1,1);
				   Date d2= new Date(year_-1900, month_-1,Lastday);
				   String QUERYSTR = "SELECT * FROM "+moneyBookDB.SQL_DBname+" WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime()+" ORDER BY date ASC";
			       	//Log.d("",year_+"년"+month_+"월"+(a-ii)+"일");
			       	Date tmp_date = new Date(year_-1900, month_-1, a-ii);
			       	String[] columns={"content","memo","money","kindof","date"};
			       	String selection="date=?";
			       	String[] selectionArgs={
			       			String.valueOf(tmp_date.getTime())
			       			};    
			       	
			       	//Cursor c_money = moneyBookDB.selectTable(columns, selection, selectionArgs, null,null,null);
			       	Cursor c_money = moneyBookDB.RawQueryString(QUERYSTR);
			       	//Log.d("","count"+tmp_count);
			       
			  
			       	int tmp_spend=0;
			       	String date = "";
			       	if(c_money.getCount()>0){
				       	if(c_money.moveToFirst()){
				       		do{//tmp_spend+=Integer.parseInt(c_money.getString(2));
				       			tmp_spend+=Integer.parseInt(c_money.getString(c_money.getColumnIndex("money")));
				       		date=c_money.getString(c_money.getColumnIndex("date"));
				       		}while(c_money.moveToNext());
				       	}
				       	tmp_spendint+=tmp_spend;
			       	}else{
			       		Log.d("x","x");
			       	}
			       	
			       	
			       	
			       	
			       	/*
			       	if(tmp_spend>0){
			       		if(lastday_1<(a-ii))
			       		lastday_1=(a-ii);
			       		Log.d("date : "+(a-ii),""+tmp_spend);
			       	}
			       	 */
			       //	ii++;
			 //  }
			   //Log.d("",lastday_2+"fd");
			   
			   if(tmp_spendint>0){
				   Date tmp_d = new Date(Long.parseLong(date));
				    Log.d("",simpleDateFormat.format(tmp_d));
				    
				   arrayList1.add(simpleDateFormat.format(tmp_d));
				   //arrayList1.add(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+lastday_1);
				   arrayList2.add(String.format("￦%,d",tmp_spendint));
				   arrayList3.add(String.format("￦%,d",tmp_spendint));
			   }
			   
			   lastday_1=0;
			  
			  
		   }
	   }else if(statisticscode==2){
		   int month = c.get(Calendar.MONTH);
		   for(int i=0;i<12;i++){
			   Calendar calendar;
			   calendar = (Calendar) calendar_2_bt.clone();
			   calendar.set(Calendar.MONTH,month-i);
			   
			   grecal = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
			   int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
			   int ii=0;
			   
			   int tmp_moneyint=0;
			   
			   
			   int year_ = calendar.get(Calendar.YEAR);
		       int month_ = calendar.get(Calendar.MONTH)+1;
		      
			   lastday_2=0;
			   Log.d("Start","day1="+lastday_1+" and day2="+lastday_2);
			   
			  
			  // while(ii<Lastday){
				   int a = Lastday;
				   Date d1= new Date(year_-1900, month_-1,1);
				   Date d2= new Date(year_-1900, month_-1,Lastday);
				   String QUERYSTR = "SELECT * FROM "+inputDB.SQL_DBname+" WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime()+" ORDER BY date ASC";
			       	//Log.d("",year_+"년"+month_+"월"+(a-ii)+"일");
			       	Date tmp_date = new Date(year_-1900, month_-1, a-ii);
			       	String[] columns={"content","memo","money","kindof","date"};
			       	String selection="date=?";
			       	String[] selectionArgs={
			       			String.valueOf(tmp_date.getTime())
			       			};    
			       	//Cursor c = inputDB.selectTable(columns, selection, selectionArgs, null,null,null);
			       	Cursor c = inputDB.RawQueryString(QUERYSTR);
			       	int tmp_count=c.getCount();
			       	//Log.d("","count"+tmp_count);
			       	int tmp_money = 0;
			       	String date ="";
			       	if(tmp_count>0){
			       		
			       		
			       		if(c.moveToFirst()){
			       			do{tmp_money += Integer.parseInt(c.getString(c.getColumnIndex("money")));
			       			date=c.getString(c.getColumnIndex("date"));
			       			}while(c.moveToNext());
			       		}        		
			       		tmp_moneyint+=tmp_money;
			       		
			       		
				       	
			       	}
			/*
			       	if(tmp_money>0){
			       		if(lastday_2<(a-ii))
			       		lastday_2=(a-ii);
			       		Log.d("date : "+(a-ii),""+tmp_money);
			       	}
			  */     	
			       	
			       	//ii++;
			 //  }
			   Log.d("",lastday_2+"fd");
			   if(tmp_moneyint>0){
				   Date ddd = new Date(Long.parseLong(date));
				   arrayList1.add(simpleDateFormat.format(ddd));
				   //arrayList1.add(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+lastday_2);
				   arrayList2.add(String.format("￦%,d",tmp_moneyint));
				   arrayList3.add(String.format("￦%,d",tmp_moneyint));
			   }
			   
			   lastday_2=0;
			  
		   }
	   }else if(statisticscode==3){
		   ArrayList<Integer> tmpaArrayList = new ArrayList<Integer>();
		   ArrayList<Integer> tmpaArrayList1 = new ArrayList<Integer>();
		   int month = c.get(Calendar.MONTH);
		   for(int i=0;i<12;i++){
			   Calendar calendar;
			   calendar = (Calendar) calendar_2_bt.clone();
			   calendar.set(Calendar.MONTH,month-i);
			   
			   grecal = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
			   int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
			   int ii=0;
			   
			 
			   int tmp_spendint=0;
			   int tmp_moneyint=0;
			   
			   int year_ = calendar.get(Calendar.YEAR);
		       int month_ = calendar.get(Calendar.MONTH)+1;
		       lastday_1=0;
		       lastday_2=0;
			   
			  
			  // while(ii<Lastday){
				   int a = Lastday;
				   Date d1= new Date(year_-1900, month_-1,1);
				   Date d2= new Date(year_-1900, month_-1,Lastday);
			       	//Log.d("",year_+"년"+month_+"월"+(a-ii)+"일");
			       	Date tmp_date = new Date(year_-1900, month_-1, a-ii);
			       	String[] columns={"content","memo","money","kindof","date"};
			       	String selection="date=?";
			       	String[] selectionArgs={
			       			String.valueOf(tmp_date.getTime())
			       			};    
			       	
			       	Cursor c_money = moneyBookDB.RawQueryString("SELECT * FROM "+moneyBookDB.SQL_DBname+" WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime()+" ORDER BY date ASC");//selectTable(columns, selection, selectionArgs, null,null,null);
			      	Cursor c = inputDB.RawQueryString("SELECT * FROM "+inputDB.SQL_DBname+" WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime()+" ORDER BY date ASC");//selectTable(columns, selection, selectionArgs, null,null,null);
			       	//Log.d("","count"+tmp_count);
			       
			      	String date1="0";
			      	String date2="0";
			       	int tmp_spend=0;
			       	if(c_money.moveToFirst()){
			       		do{tmp_spend+=Integer.parseInt(c_money.getString(c_money.getColumnIndex("money")));
			       		date1=c_money.getString(c_money.getColumnIndex("date"));
			       		}while(c_money.moveToNext());
			       	}
			       	
			       	
			       	tmp_spendint+=tmp_spend;
			       	
			       	
/*
			       	if(tmp_spend>0){
			       		if(lastday_1<(a-ii))
			       		lastday_1=(a-ii);
			       		Log.d("date : "+(a-ii),""+tmp_spend);
			       	}
	*/		      
			       	int tmp_count=c.getCount();
			       	//Log.d("","count"+tmp_count);
			       	int tmp_money = 0;
			       	if(tmp_count>0){
			       		
			       		
			       		if(c.moveToFirst()){
			       			do{tmp_money += Integer.parseInt(c.getString(c.getColumnIndex("money")));
			       			date2=c.getString(c.getColumnIndex("date"));
			       			}while(c.moveToNext());
			       		}        		
			       		tmp_moneyint+=tmp_money;
			       		
			       		
				       	
			       	}
			
			       	if(tmp_money>0){
			       		if(lastday_2<(a-ii))
			       		lastday_2=(a-ii);
			       		Log.d("date : "+(a-ii),""+tmp_money);
			       	}
			       	Log.d("spend",""+tmp_spendint);
			       	Log.d("money",""+tmp_moneyint);
			       	ii++;
			  // }
			   //Log.d("",lastday_2+"fd");
			   if(tmp_spendint>0 || tmp_moneyint>0){
				   if(date1.length() > date2.length()){
					   Date dddd = new Date(Long.parseLong(date1));
					   arrayList1.add(simpleDateFormat.format(dddd));
					   Log.d("","x1");
				   }else if(date1.length() <  date2.length() ){
					   Date dddd = new Date(Long.parseLong(date2));
					   arrayList1.add(simpleDateFormat.format(dddd));
					   Log.d("","x2");
				   }else{
					   Long long1 = Long.parseLong(date1);
					   Long long2 = Long.parseLong(date2);
					   Log.d("","x3"+" ::"+long1+"::"+long2 );
					   if(long1>long2){
						   Date dddd = new Date(Long.parseLong(date1));
						   arrayList1.add(simpleDateFormat.format(dddd));
						   Log.d("","x4");
					   }else if(long1<long2){
						   Date dddd = new Date(Long.parseLong(date2));
						   arrayList1.add(simpleDateFormat.format(dddd));
						   Log.d("","x5");
					   }else{
						   Date dddd = new Date(Long.parseLong(date1));
						   arrayList1.add(simpleDateFormat.format(dddd));
						   Log.d("","x6");
					   }
				   }
				   /*
				   if(lastday_1<=lastday_2){
					   arrayList1.add(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+lastday_2);
				   }else{
					   arrayList1.add(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+lastday_1);
				   }*/
				   int ems = tmp_moneyint-tmp_spendint;
				   allllllll+=ems;
				   arrayList2.add(String.format("￦%,d",ems));
				   tmpaArrayList.add(allllllll);
				   tmpaArrayList1.add(ems);
				   arrayList3.add(String.format("￦%,d",allllllll));
				   
			   }
			   /*
			   for(int aaa=0;aaa<tmpaArrayList.size()-1;aaa++){
				   allllllll-=tmpaArrayList.get(tmpaArrayList.size()-2-aaa);
				   arrayList3.add(String.format("￦%,d",allllllll));
			   }
			   */
			   lastday_2=0;
			   lastday_1=0;
			  
			  
		   }
	   }
	   
	   
   }
   void makeAdapter1(int code){
	   arrayList1 = new ArrayList<String>();
	   arrayList2 = new ArrayList<String>();
	   arrayList3 = new ArrayList<String>();
	   int allllllll=0;
	   if(statisticscode==1){
		   int year = c.get(Calendar.YEAR);
		   for(int i=0;i<12;i++){
			   Calendar calendar;
			   calendar = Calendar.getInstance();
			   calendar.set(Calendar.YEAR,year-i);
			   
			   grecal = new GregorianCalendar(calendar.get(Calendar.YEAR),11,calendar.get(Calendar.DAY_OF_MONTH));
			   int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
			   int ii=0;
			   
			 
			   int tmp_spendint=0;
			   
			   int year_ = calendar.get(Calendar.YEAR);
		       int month_ = calendar.get(Calendar.MONTH)+1;
		       lastday_1=0;
			  
			   
			  
			 //  while(ii<Lastday){
				   int a = Lastday;
				   Date d1= new Date(year_-1900,0,1);
				   Date d2= new Date(year_-1900,11,Lastday);
				   String QUERYSTR = "SELECT * FROM "+moneyBookDB.SQL_DBname+" WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime()+" ORDER BY date ASC";
			       	//Log.d("",year_+"년"+month_+"월"+(a-ii)+"일");
			       	Date tmp_date = new Date(year_-1900, month_-1, a-ii);
			       	String[] columns={"content","memo","money","kindof","date"};
			       	String selection="date=?";
			       	String[] selectionArgs={
			       			String.valueOf(tmp_date.getTime())
			       			};    
			       	
			       	//Cursor c_money = moneyBookDB.selectTable(columns, selection, selectionArgs, null,null,null);
			       	Cursor c_money = moneyBookDB.RawQueryString(QUERYSTR);
			       	//Log.d("","count"+tmp_count);
			       
			  
			       	int tmp_spend=0;
			       	String date = "";
			       	if(c_money.getCount()>0){
				       	if(c_money.moveToFirst()){
				       		do{//tmp_spend+=Integer.parseInt(c_money.getString(2));
				       			tmp_spend+=Integer.parseInt(c_money.getString(c_money.getColumnIndex("money")));
				       		date=c_money.getString(c_money.getColumnIndex("date"));
				       		}while(c_money.moveToNext());
				       	}
				       	tmp_spendint+=tmp_spend;
			       	}else{
			       		Log.d("x","x");
			       	}
			       	
			       	
			       	
			       	
			       	/*
			       	if(tmp_spend>0){
			       		if(lastday_1<(a-ii))
			       		lastday_1=(a-ii);
			       		Log.d("date : "+(a-ii),""+tmp_spend);
			       	}
			       	 */
			       //	ii++;
			 //  }
			   //Log.d("",lastday_2+"fd");
			   
			   if(tmp_spendint>0){
				   Date tmp_d = new Date(Long.parseLong(date));
				    Log.d("",simpleDateFormat.format(tmp_d));
				    
				   arrayList1.add(simpleDateFormat.format(tmp_d));
				   //arrayList1.add(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+lastday_1);
				   arrayList2.add(String.format("￦%,d",tmp_spendint));
				   arrayList3.add(String.format("￦%,d",tmp_spendint));
			   }
			   
			   lastday_1=0;
			  
			  
		   }
	   }else if(statisticscode==2){
		   int year = c.get(Calendar.YEAR);
		   for(int i=0;i<12;i++){
			   Calendar calendar;
			   calendar = Calendar.getInstance();
calendar.set(Calendar.YEAR,year-i);
			   
			   grecal = new GregorianCalendar(calendar.get(Calendar.YEAR),11,calendar.get(Calendar.DAY_OF_MONTH));
			   int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
			   int ii=0;
			   
			   int tmp_moneyint=0;
			   
			   
			   int year_ = calendar.get(Calendar.YEAR);
		       int month_ = calendar.get(Calendar.MONTH)+1;
		      
			   lastday_2=0;
			   Log.d("Start","day1="+lastday_1+" and day2="+lastday_2);
			   
			  
			  // while(ii<Lastday){
				   int a = Lastday;
				   Date d1= new Date(year_-1900,0,1);
				   Date d2= new Date(year_-1900,11,Lastday);
				   String QUERYSTR = "SELECT * FROM "+inputDB.SQL_DBname+" WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime()+" ORDER BY date ASC";
			       	//Log.d("",year_+"년"+month_+"월"+(a-ii)+"일");
			       	Date tmp_date = new Date(year_-1900, month_-1, a-ii);
			       	String[] columns={"content","memo","money","kindof","date"};
			       	String selection="date=?";
			       	String[] selectionArgs={
			       			String.valueOf(tmp_date.getTime())
			       			};    
			       	//Cursor c = inputDB.selectTable(columns, selection, selectionArgs, null,null,null);
			       	Cursor c = inputDB.RawQueryString(QUERYSTR);
			       	int tmp_count=c.getCount();
			       	//Log.d("","count"+tmp_count);
			       	int tmp_money = 0;
			       	String date ="";
			       	if(tmp_count>0){
			       		
			       		
			       		if(c.moveToFirst()){
			       			do{tmp_money += Integer.parseInt(c.getString(c.getColumnIndex("money")));
			       			date=c.getString(c.getColumnIndex("date"));
			       			}while(c.moveToNext());
			       		}        		
			       		tmp_moneyint+=tmp_money;
			       		
			       		
				       	
			       	}
			/*
			       	if(tmp_money>0){
			       		if(lastday_2<(a-ii))
			       		lastday_2=(a-ii);
			       		Log.d("date : "+(a-ii),""+tmp_money);
			       	}
			  */     	
			       	
			       	//ii++;
			 //  }
			   Log.d("",lastday_2+"fd");
			   if(tmp_moneyint>0){
				   Date ddd = new Date(Long.parseLong(date));
				   arrayList1.add(simpleDateFormat.format(ddd));
				   //arrayList1.add(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+lastday_2);
				   arrayList2.add(String.format("￦%,d",tmp_moneyint));
				   arrayList3.add(String.format("￦%,d",tmp_moneyint));
			   }
			   
			   lastday_2=0;
			  
		   }
	   }else if(statisticscode==3){
		   ArrayList<Integer> tmpaArrayList = new ArrayList<Integer>();
		   ArrayList<Integer> tmpaArrayList1 = new ArrayList<Integer>();
		   int year = c.get(Calendar.YEAR);
		   for(int i=0;i<12;i++){
			   Calendar calendar;
			   calendar = Calendar.getInstance();
calendar.set(Calendar.YEAR,year-i);
			   
			   grecal = new GregorianCalendar(calendar.get(Calendar.YEAR),11,calendar.get(Calendar.DAY_OF_MONTH));
			   int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
			   int ii=0;
			   
			 
			   int tmp_spendint=0;
			   int tmp_moneyint=0;
			   
			   int year_ = calendar.get(Calendar.YEAR);
		       int month_ = calendar.get(Calendar.MONTH)+1;
		       lastday_1=0;
		       lastday_2=0;
			   
			  
			  // while(ii<Lastday){
				   int a = Lastday;
				   Date d1= new Date(year_-1900,0,1);
				   Date d2= new Date(year_-1900,11,Lastday);
			       	//Log.d("",year_+"년"+month_+"월"+(a-ii)+"일");
			       	Date tmp_date = new Date(year_-1900, month_-1, a-ii);
			       	String[] columns={"content","memo","money","kindof","date"};
			       	String selection="date=?";
			       	String[] selectionArgs={
			       			String.valueOf(tmp_date.getTime())
			       			};    
			       	
			       	Cursor c_money = moneyBookDB.RawQueryString("SELECT * FROM "+moneyBookDB.SQL_DBname+" WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime()+" ORDER BY date ASC");//selectTable(columns, selection, selectionArgs, null,null,null);
			      	Cursor c = inputDB.RawQueryString("SELECT * FROM "+inputDB.SQL_DBname+" WHERE date BETWEEN "+d1.getTime()+" AND "+d2.getTime()+" ORDER BY date ASC");//selectTable(columns, selection, selectionArgs, null,null,null);
			       	//Log.d("","count"+tmp_count);
			       
			      	String date1="0";
			      	String date2="0";
			       	int tmp_spend=0;
			       	if(c_money.moveToFirst()){
			       		do{tmp_spend+=Integer.parseInt(c_money.getString(c_money.getColumnIndex("money")));
			       		date1=c_money.getString(c_money.getColumnIndex("date"));
			       		}while(c_money.moveToNext());
			       	}
			       	
			       	
			       	tmp_spendint+=tmp_spend;
			       	
			       	
/*
			       	if(tmp_spend>0){
			       		if(lastday_1<(a-ii))
			       		lastday_1=(a-ii);
			       		Log.d("date : "+(a-ii),""+tmp_spend);
			       	}
	*/		      
			       	int tmp_count=c.getCount();
			       	//Log.d("","count"+tmp_count);
			       	int tmp_money = 0;
			       	if(tmp_count>0){
			       		
			       		
			       		if(c.moveToFirst()){
			       			do{tmp_money += Integer.parseInt(c.getString(c.getColumnIndex("money")));
			       			date2=c.getString(c.getColumnIndex("date"));
			       			}while(c.moveToNext());
			       		}        		
			       		tmp_moneyint+=tmp_money;
			       		
			       		
				       	
			       	}
			
			       	if(tmp_money>0){
			       		if(lastday_2<(a-ii))
			       		lastday_2=(a-ii);
			       		Log.d("date : "+(a-ii),""+tmp_money);
			       	}
			       	Log.d("spend",""+tmp_spendint);
			       	Log.d("money",""+tmp_moneyint);
			       	ii++;
			  // }
			   //Log.d("",lastday_2+"fd");
			   if(tmp_spendint>0 || tmp_moneyint>0){
				   if(date1.length() > date2.length()){
					   Date dddd = new Date(Long.parseLong(date1));
					   arrayList1.add(simpleDateFormat.format(dddd));
					   Log.d("","x1");
				   }else if(date1.length() <  date2.length() ){
					   Date dddd = new Date(Long.parseLong(date2));
					   arrayList1.add(simpleDateFormat.format(dddd));
					   Log.d("","x2");
				   }else{
					   Long long1 = Long.parseLong(date1);
					   Long long2 = Long.parseLong(date2);
					   Log.d("","x3"+" ::"+long1+"::"+long2 );
					   if(long1>long2){
						   Date dddd = new Date(Long.parseLong(date1));
						   arrayList1.add(simpleDateFormat.format(dddd));
						   Log.d("","x4");
					   }else if(long1<long2){
						   Date dddd = new Date(Long.parseLong(date2));
						   arrayList1.add(simpleDateFormat.format(dddd));
						   Log.d("","x5");
					   }else{
						   Date dddd = new Date(Long.parseLong(date1));
						   arrayList1.add(simpleDateFormat.format(dddd));
						   Log.d("","x6");
					   }
				   }
				   /*
				   if(lastday_1<=lastday_2){
					   arrayList1.add(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+lastday_2);
				   }else{
					   arrayList1.add(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+lastday_1);
				   }*/
				   int ems = tmp_moneyint-tmp_spendint;
				   allllllll+=ems;
				   arrayList2.add(String.format("￦%,d",ems));
				   tmpaArrayList.add(allllllll);
				   tmpaArrayList1.add(ems);
				   arrayList3.add(String.format("￦%,d",allllllll));
				   
			   }
			   /*
			   for(int aaa=0;aaa<tmpaArrayList.size()-1;aaa++){
				   allllllll-=tmpaArrayList.get(tmpaArrayList.size()-2-aaa);
				   arrayList3.add(String.format("￦%,d",allllllll));
			   }
			   */
			   lastday_2=0;
			   lastday_1=0;
			  
			  
		   }
	   }
	   
	   
   }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button4){
		AlertDialog.Builder builder = new AlertDialog.Builder(Tab_FourActivity.this);
		builder.setTitle("통계 종류 선택");
		builder.setItems(util.statistics1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	bt4.setText(util.statistics1[item]);
		    	if(util.statistics1[item].equals("지출/전체")){
		    		statisticscode=1;
		    		tv_title2.setText("지출 금액");
		    		if(kind_month){
		    			makeAdapter(statisticscode);
		    		}else{
		    			makeAdapter1(statisticscode);
		    		}
		    		
		    		adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
			        listView.setAdapter(adapter);
		    	}else if(util.statistics1[item].equals("수입/전체")){
		    		statisticscode=2;
		    		tv_title2.setText("수입 금액");
		    		if(kind_month){
		    			makeAdapter(statisticscode);
		    		}else{
		    			makeAdapter1(statisticscode);
		    		}
		    		adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
			        listView.setAdapter(adapter);
		    	}else{
		    		//Toast.makeText(getApplicationContext(), "'"+util.statistics1[item]+"'는 미구현 기능입니다.", Toast.LENGTH_SHORT).show();
		    		tv_title2.setText("수입-지출");
		    		tv_title3.setText("누적 금액");
		    		statisticscode=3;
		    		if(kind_month){
		    			makeAdapter(statisticscode);
		    		}else{
		    			makeAdapter1(statisticscode);
		    		}
		    		adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
			        listView.setAdapter(adapter);
		    		
		    	}
		    	
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		}else if(v.getId()==R.id.button6){
			AlertDialog.Builder builder = new AlertDialog.Builder(Tab_FourActivity.this);
			builder.setTitle("통계 단위 선택");
			builder.setItems(util.monthyear, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	bt6.setText(util.monthyear[item]);
			    	if(util.monthyear[item].equals("월 단위")){
			    		kind_month=true;
			    		 GregorianCalendar gregorianCalendar = new GregorianCalendar();
			    	        calendar_1_bt =  Calendar.getInstance();
			    	        calendar_1_bt.set(Calendar.MONTH,calendar_1_bt.get(Calendar.MONTH)-11);
			    	        calendar_1_bt.set(Calendar.DAY_OF_MONTH,1);
			    	        calendar_2_bt =  Calendar.getInstance();
			    	        calendar_2_bt.set(Calendar.DAY_OF_MONTH,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			    	        
			    	      
			    	        
			    	        
			    	        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			    	        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
			    	        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
			    	        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
			    	      
			    	        btdate.setText(dateString);
			    		makeAdapter(statisticscode);
			    	}else if(util.monthyear[item].equals("년 단위")){
			    		kind_month=false;
			    		calendar_1_bt =  Calendar.getInstance();
			            calendar_1_bt.set(Calendar.YEAR,calendar_1_bt.get(Calendar.YEAR)-11);
			            calendar_1_bt.set(Calendar.DAY_OF_MONTH,1);
			            calendar_2_bt =  Calendar.getInstance();
			            calendar_2_bt.set(Calendar.DAY_OF_MONTH,calendar_2_bt.getActualMaximum(Calendar.DAY_OF_MONTH));
			            
			          
			            
			            
			            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			            Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
			            Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
			            String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
			          
			            btdate.setText(dateString);
			    		
			    		makeAdapter1(statisticscode);
			    	}
			    	
		    		adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
			        listView.setAdapter(adapter);
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();	
		}else if(v.getId()==R.id.button5){
			AlertDialog.Builder builder = new AlertDialog.Builder(Tab_FourActivity.this);
			builder.setTitle("통계 분류 선택");
			builder.setItems(util.allspend, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	bt5.setText(util.allspend[item]);
			    	Toast.makeText(getApplicationContext(),"미구현 기능입니다.",Toast.LENGTH_SHORT).show();
			    }
			});
			AlertDialog alert = builder.create();
			//alert.show();
		}else if(v.getId()==R.id.button3){
			Toast.makeText(getApplicationContext(), "새로고침",Toast.LENGTH_SHORT).show();
			if(kind_month){
    			makeAdapter(statisticscode);
    		}else{
    			makeAdapter1(statisticscode);
    		}
			adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
	        listView.setAdapter(adapter);
		}else if(v.getId()==R.id.button1){
			if (kind_month) {
				
				calendar_1_bt.set(Calendar.YEAR,calendar_1_bt.get(Calendar.YEAR)-1);
				//calendar_2_bt = (Calendar) calendar_1_bt.clone();
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,1);
				calendar_2_bt.set(Calendar.YEAR,calendar_2_bt.get(Calendar.YEAR)-1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,calendar_2_bt.getActualMaximum(Calendar.DAY_OF_MONTH));
				makeAdapter(statisticscode);
				adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
		        listView.setAdapter(adapter);
		        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
		        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
		        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
		      
		        btdate.setText(dateString);
			}else{
				Toast.makeText(getApplicationContext(), "년 단위에서는 지원하지 않습니다.",Toast.LENGTH_SHORT).show();
			}
		}else if(v.getId()==R.id.button2){
			
			if (kind_month) {
				calendar_1_bt.set(Calendar.YEAR,calendar_1_bt.get(Calendar.YEAR)+1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,1);
				calendar_2_bt.set(Calendar.YEAR,calendar_2_bt.get(Calendar.YEAR)+1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,calendar_2_bt.getActualMaximum(Calendar.DAY_OF_MONTH));
				makeAdapter(statisticscode);
				adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
		        listView.setAdapter(adapter);
		        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
		        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
		        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
		      
		        btdate.setText(dateString);
			}else{
				Toast.makeText(getApplicationContext(), "년 단위에서는 지원하지 않습니다.",Toast.LENGTH_SHORT).show();
			}
			
		}
	}
    
}

class ListViewAdapter extends BaseAdapter{
	private LayoutInflater Inflater;
	private int _layout;
	private ArrayList<String> a;
	private ArrayList<String> b;
	private ArrayList<String> d;
	public ListViewAdapter(Context c, int layout,ArrayList<String> a,ArrayList<String> b,ArrayList<String> d){
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
	public String getDateString(int arg0){
		return a.get(arg0);
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
		TextView tv1 = (TextView)arg1.findViewById(R.id.textView1);
		TextView tv2 = (TextView)arg1.findViewById(R.id.textView2);
		TextView tv3 = (TextView)arg1.findViewById(R.id.textView3);
		
		tv1.setText(a.get(arg0));
		tv2.setText(b.get(arg0));
		tv3.setText(d.get(arg0));
		
		return arg1;
	}
	
}