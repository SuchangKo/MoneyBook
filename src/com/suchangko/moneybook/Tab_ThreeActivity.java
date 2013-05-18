package com.suchangko.moneybook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * 
 * Date: 13. 4. 3
 * Time: 오후 8:55
 * To change this template use File | Settings | File Templates.
 */
public class Tab_ThreeActivity extends Activity {
	Button bt_date;
	TabthreeAdapter adapter;
	ListView listView;
	ArrayList<Integer> wholemoneyarrayList;
	ArrayList<Integer> spendmonetArrayList;
	ArrayList<Integer> budgetArrayList;
	ArrayList<String> nameArrayList;
	private LayoutInflater Inflater;
	Calendar c;
	MoneyInputDB inputDB;
	MoneyBookDB moneyBookDB;
	GregorianCalendar grecal;
	int tmp_moneyint=0;
	int tmp_spendint=0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
        c = Calendar.getInstance();
        inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
        moneyBookDB =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
        inputDB.open();
        moneyBookDB.open();
        /*
         * 
        */
        Startadapter();
        		
        listView = (ListView)findViewById(R.id.listview1);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if(pos==0){
					budgetArrayList.add(0,3000000);
					adapter.notifyDataSetChanged();
				}
			}
		});
        wholemoneyarrayList = new ArrayList<Integer>();
        spendmonetArrayList = new ArrayList<Integer>();
        nameArrayList = new ArrayList<String>();
        budgetArrayList = new ArrayList<Integer>();
        wholemoneyarrayList.add(tmp_moneyint);
        spendmonetArrayList.add(tmp_spendint);
        nameArrayList.add("전체 예산");
        budgetArrayList.add(0);
        adapter = new TabthreeAdapter(getApplicationContext(),c, R.layout.tab3_layout,wholemoneyarrayList,spendmonetArrayList,nameArrayList,budgetArrayList);
        listView.setAdapter(adapter);     
    }
   void Startadapter(){

       grecal=new GregorianCalendar();
       int i=0;
       int LastDay = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
       
       while(i<LastDay){
       	int a = LastDay;
       	int year_ = c.get(Calendar.YEAR);
       	int month_ = c.get(Calendar.MONTH)+1;
       	Date tmp_date = new Date(year_-1900, month_-1, a-i);
       	String[] columns={"content","memo","money","kindof","date"};
       	String selection="date=?";
       	String[] selectionArgs={
       			String.valueOf(tmp_date.getTime())
       			};    
       	Cursor c = inputDB.selectTable(columns, selection, selectionArgs, null,null,null);
       	Cursor c_money = moneyBookDB.selectTable(columns, selection, selectionArgs, null,null,null);
       	int tmp_count=c.getCount();
       	if(tmp_count>0){
       		int tmp_money = 0;
       		
       		if(c.moveToFirst()){
       			do{
       				tmp_money += Integer.parseInt(c.getString(2));
       				}while(c.moveToNext());
       		}        		
       		tmp_moneyint+=tmp_money;
       	}else{
       	}
       	i++;
       	int tmp_spend=0;
       	if(c_money.moveToFirst()){
       		do{	tmp_spend+=Integer.parseInt(c_money.getString(2));}while(c_money.moveToNext());
       	}
       	tmp_spendint+=tmp_spend;
       }
       
       Log.d("",""+tmp_moneyint);
       Log.d("",""+tmp_spendint);
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
    
}