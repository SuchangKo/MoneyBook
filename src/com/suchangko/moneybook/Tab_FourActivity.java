package com.suchangko.moneybook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
public class Tab_FourActivity extends Activity {
	Button bt_next;
	Button bt_pre;
	Button bt4;
	Button bt5;
	Button bt6;
	ListView listView;
	ListViewAdapter adapter;
	MoneyInputDB inputDB;
	MoneyBookDB moneyBookDB;
	Calendar c;
	GregorianCalendar grecal;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = Calendar.getInstance();
        inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
        moneyBookDB =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
        inputDB.open();
        moneyBookDB.open();
        setContentView(R.layout.activity_tab4);
        bt_next=(Button)findViewById(R.id.button1);
        bt_pre=(Button)findViewById(R.id.button3);
        bt4=(Button)findViewById(R.id.button4);
        bt5=(Button)findViewById(R.id.button5);
        bt6=(Button)findViewById(R.id.button6);
        listView = (ListView)findViewById(R.id.listview1);
        makeAdapter();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
    	menu.add(0,0,0,"예산 추가").setIcon(R.drawable.ic_menu_add);
    	menu.add(0,1,0,"예산 복사").setIcon(android.R.drawable.ic_menu_share);
    	menu.add(0,2,0,"예산 붙여넣기").setIcon(android.R.drawable.ic_menu_set_as);
    	menu.add(1,3,0,"상위 예산 분배").setIcon(android.R.drawable.ic_menu_search);
    	menu.add(1,4,0,"도움말").setIcon(android.R.drawable.ic_menu_help);
    	menu.add(1,5,0,"환경설정").setIcon(R.drawable.ic_menu_more);	
	return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int itemid =item.getItemId();
		switch (itemid) {
		case 0:
			Toast.makeText(this,"수입입력",100).show();
			break;
		case 1:
			Toast.makeText(this,"이전문자등록",100).show();
			break;
		case 2:
			Toast.makeText(this,"즐겨찾기편집",100).show();
			break;
		case 3:
			Toast.makeText(this,"지출내역검색",100).show();
			break;
		case 4:
			Toast.makeText(this,"회사소개",100).show();
			break;
		case 5:
			Toast.makeText(this,"더보기",100).show();
			break;
				}
		return super.onOptionsItemSelected(item);
	}
   void makeAdapter(){
	   int month = c.get(Calendar.MONTH);
	   for(int i=0;i<12;i++){
		   Calendar calendar;
		   calendar = Calendar.getInstance();
		   calendar.set(Calendar.MONTH,month-i);
		   
		   grecal = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		   int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
		   int ii=0;
		   
		   int tmp_moneyint=0;
		   int tmp_spendint=0;
		   
		   int year_ = calendar.get(Calendar.YEAR);
	       int month_ = calendar.get(Calendar.MONTH)+1;
	      
		   while(ii<Lastday){
			   int a = Lastday;
		       	
		       	//Log.d("",year_+"년"+month_+"월"+(a-ii)+"일");
		       	Date tmp_date = new Date(year_-1900, month_-1, a-ii);
		       	String[] columns={"content","memo","money","kindof","date"};
		       	String selection="date=?";
		       	String[] selectionArgs={
		       			String.valueOf(tmp_date.getTime())
		       			};    
		       	Cursor c = inputDB.selectTable(columns, selection, selectionArgs, null,null,null);
		       	Cursor c_money = moneyBookDB.selectTable(columns, selection, selectionArgs, null,null,null);
		       	int tmp_count=c.getCount();
		       	//Log.d("","count"+tmp_count);
		       	if(tmp_count>0){
		       		int tmp_money = 0;
		       		
		       		if(c.moveToFirst()){
		       			do{tmp_money += Integer.parseInt(c.getString(2));
		       			}while(c.moveToNext());
		       		}        		
		       		tmp_moneyint+=tmp_money;
		       	}else{
		       		
		       	}
		       	ii++;
		       	int tmp_spend=0;
		       	if(c_money.moveToFirst()){
		       		do{tmp_spend+=Integer.parseInt(c_money.getString(2));
		       		}while(c_money.moveToNext());
		       	}
		       	tmp_spendint+=tmp_spend;
		   }
		   Log.d("",calendar.get(Calendar.YEAR)+"년"+(calendar.get(Calendar.MONTH)+1)+"월 : 예산"+tmp_moneyint);
		   Log.d("",calendar.get(Calendar.YEAR)+"년"+(calendar.get(Calendar.MONTH)+1)+"월 : 지출"+tmp_spendint);
		   
		  
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
		TextView tv1 = (TextView)arg1.findViewById(R.id.textView1);
		TextView tv2 = (TextView)arg1.findViewById(R.id.textView2);
		TextView tv3 = (TextView)arg1.findViewById(R.id.textView3);
		
		tv1.setText(a.get(arg0));
		tv2.setText(b.get(arg0));
		tv3.setText(d.get(arg0));
		
		return arg1;
	}
	
}