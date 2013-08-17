package com.suchangko.moneybook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
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
public class Tab_ThreeActivity extends Activity implements android.view.View.OnClickListener {
	Button bt_date;
	Button bt4;
	Button bt5;
	Button bt6;
	Button bt_next;
	 DetailKindofDB detailKindofDB;
	Button bt_pre;
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
	Tabthreesqlite tabthreesqlite;
	GregorianCalendar grecal;
	int tmp_moneyint=0;
	int tmp_spendint=0;
	boolean moneyview1 = true;
	boolean moneyview2 = true;
	Calendar calendar_1_bt;
	Calendar calendar_2_bt;
	 GregorianCalendar gregorianCalendar;

	boolean allspendviewed = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
       gregorianCalendar = new GregorianCalendar();
        calendar_1_bt =  Calendar.getInstance();
        calendar_1_bt.set(Calendar.DAY_OF_MONTH,1);
        calendar_2_bt =  Calendar.getInstance();
        calendar_2_bt.set(Calendar.DAY_OF_MONTH,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        detailKindofDB=new DetailKindofDB(getApplicationContext(), DetailKindofDB.SQL_Create_detailkindofdb,DetailKindofDB.SQL_DBname);
        detailKindofDB.open();
        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
        
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
        bt_date = (Button)findViewById(R.id.button2);
        bt_date.setText(dateString);
        bt_pre = (Button)findViewById(R.id.button1);
        bt_next=(Button)findViewById(R.id.button3);
        
        bt_pre.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				wholemoneyarrayList.clear();
				spendmonetArrayList.clear();
				nameArrayList.clear();
				budgetArrayList.clear();
				calendar_1_bt.set(Calendar.MONTH,calendar_1_bt.get(Calendar.MONTH)-1);
				calendar_2_bt.set(calendar_1_bt.get(Calendar.YEAR),calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
				gregorianCalendar.set(Calendar.MONTH,calendar_1_bt.get(Calendar.MONTH));
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
				Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));		        
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			    String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);

			    bt_date.setText(dateString);


		        Cursor arraycCursor = tabthreesqlite.RawQueryString("SELECT * FROM "+tabthreesqlite.SQL_DBname +" WHERE date= '"+ddate1.getTime()+"'");
		        Log.d("",""+arraycCursor.getCount());
		        if(arraycCursor.getCount()>0){
		        	if(arraycCursor.moveToFirst()){
		        		do{
		        			wholemoneyarrayList.add(0);
		                	spendmonetArrayList.add(arraycCursor.getInt(1));
		                	nameArrayList.add(arraycCursor.getString(2));
		                	if(arraycCursor.getString(2).equals("전체 예산")){
		                		allspendviewed=true;
		                	}
		                	budgetArrayList.add(arraycCursor.getInt(3));
		        		}while(arraycCursor.moveToNext());
		        	}	
		        }
		        listView.setAdapter(null);
		        Startadapter();
		        adapter = new TabthreeAdapter(getApplicationContext(),calendar_1_bt, R.layout.tab3_layout,wholemoneyarrayList,spendmonetArrayList,nameArrayList,budgetArrayList,moneyview1,moneyview2);
		        listView.setAdapter(adapter);  

			}
		});
        
        bt_next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				wholemoneyarrayList.clear();
				spendmonetArrayList.clear();
				nameArrayList.clear();
				budgetArrayList.clear();
				calendar_1_bt.set(Calendar.MONTH,calendar_1_bt.get(Calendar.MONTH)+1);
				calendar_2_bt.set(calendar_1_bt.get(Calendar.YEAR),calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
				gregorianCalendar.set(Calendar.MONTH,calendar_1_bt.get(Calendar.MONTH));
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
				Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));		        
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			    String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);

			    bt_date.setText(dateString);


		        Cursor arraycCursor = tabthreesqlite.RawQueryString("SELECT * FROM "+tabthreesqlite.SQL_DBname +" WHERE date= '"+ddate1.getTime()+"'");
		        Log.d("",""+arraycCursor.getCount());
		        if(arraycCursor.getCount()>0){
		        	if(arraycCursor.moveToFirst()){
		        		do{
		        			wholemoneyarrayList.add(0);
		                	spendmonetArrayList.add(arraycCursor.getInt(1));
		                	nameArrayList.add(arraycCursor.getString(2));
		                	if(arraycCursor.getString(2).equals("전체 예산")){
		                		allspendviewed=true;
		                	}
		                	budgetArrayList.add(arraycCursor.getInt(3));
		        		}while(arraycCursor.moveToNext());
		        	}	
		        }
		        Startadapter();
		        listView.setAdapter(null);
		        adapter = new TabthreeAdapter(getApplicationContext(),calendar_1_bt, R.layout.tab3_layout,wholemoneyarrayList,spendmonetArrayList,nameArrayList,budgetArrayList,moneyview1,moneyview2);
		        listView.setAdapter(adapter);  
			}
		});
        bt4 = (Button)findViewById(R.id.button4);
       // bt4.setText(dateString);
        bt5 = (Button)findViewById(R.id.button5);
        bt5.setOnClickListener(this);
        bt6 = (Button)findViewById(R.id.button6);
        bt6.setOnClickListener(this);
        c = Calendar.getInstance();
        inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
        moneyBookDB =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
        inputDB.open();
        moneyBookDB.open();
        tabthreesqlite = new Tabthreesqlite(getApplicationContext(),Tabthreesqlite.SQL_Create_tabthreeview,Tabthreesqlite.SQL_DBname);
        tabthreesqlite.open();
        Startadapter();
        	
        listView = (ListView)findViewById(R.id.listview1);
        /*
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
		*/
        wholemoneyarrayList = new ArrayList<Integer>();
        spendmonetArrayList = new ArrayList<Integer>();
        nameArrayList = new ArrayList<String>();
        budgetArrayList = new ArrayList<Integer>();
        
        
        Cursor arraycCursor = tabthreesqlite.RawQueryString("SELECT * FROM "+tabthreesqlite.SQL_DBname +" WHERE date= '"+ddate1.getTime()+"'");
        Log.d("",""+arraycCursor.getCount());
        if(arraycCursor.getCount()>0){
        	if(arraycCursor.moveToFirst()){
        		do{
        			wholemoneyarrayList.add(0);
                	spendmonetArrayList.add(arraycCursor.getInt(1));
                	nameArrayList.add(arraycCursor.getString(2));
                	budgetArrayList.add(arraycCursor.getInt(3));
        		}while(arraycCursor.moveToNext());
        	}	
        }
        
        adapter = new TabthreeAdapter(getApplicationContext(),calendar_1_bt, R.layout.tab3_layout,wholemoneyarrayList,spendmonetArrayList,nameArrayList,budgetArrayList,moneyview1,moneyview2);
        listView.setAdapter(adapter);  
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(Tab_ThreeActivity.this);
				builder.setTitle("예산 메뉴");				
				builder.setItems(util.fixdel1, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
				   	if(util.fixdel1[item].equals("예산 삭제")){
				    		dellist(arg2);
				   		}else if(util.fixdel1[item].equals("예산 수정")){
				   			AlertDialog editAlertDialog = dialog_editbudget(arg2);
				   			editAlertDialog.show();
					    	}
					    }
				
					});
				AlertDialog alert = builder.create();
				alert.show();
				return false;
			}
		});
        
    }
   void Startadapter(){

       grecal=new GregorianCalendar( calendar_1_bt.get(Calendar.YEAR), calendar_1_bt.get(Calendar.MONTH), calendar_1_bt.get(Calendar.DAY_OF_MONTH));
       int i=0;
       int LastDay = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
       
       while(i<LastDay){
       	int a = LastDay;
       	int year_ =  calendar_1_bt.get(Calendar.YEAR);
       	int month_ =  calendar_1_bt.get(Calendar.MONTH)+1;
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
       			do{tmp_money += Integer.parseInt(c.getString(2));
       			}while(c.moveToNext());
       		}        		
       		tmp_moneyint+=tmp_money;
       	}else{
       		
       	}
       	i++;
       	int tmp_spend=0;
       	if(c_money.moveToFirst()){
       		do{tmp_spend+=Integer.parseInt(c_money.getString(2));
       		}while(c_money.moveToNext());
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
    	menu.add(0,1,0,"더보기").setIcon(R.drawable.ic_menu_more);	
	return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int itemid =item.getItemId();
		switch (itemid) {
		case 0:
			AlertDialog.Builder builder = new AlertDialog.Builder(Tab_ThreeActivity.this);
			builder.setTitle("예산 카테고리 추가");
			builder.setItems(util.yeosan_category, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	if(util.yeosan_category[item].equals("전체 예산")){
			    		if(allspendviewed){
			    			ShowToast("전체 예산이 이미 등록되어 있습니다.");
			    		}else{
			    			allspendviewed=true;
			    			tmp_moneyint=0;
			    			tmp_spendint=0;
			    			Startadapter();
			    			addlist(tmp_moneyint,tmp_spendint,"전체 예산",0);
			    	        adapter.notifyDataSetChanged();
			    		}

			    	}else if(util.yeosan_category[item].equals("카테고리 기준")){
			    		AlertDialog alertDialog = dialog_catecory();
			    		 alertDialog.show();
			    	}else if(util.yeosan_category[item].equals("세부 카테고리 기준")){
			    		AlertDialog alertDialog = dialog_catecory_detail();
			    		alertDialog.show();
			    	}

			    }			    
			});
			AlertDialog alert = builder.create();

			alert.show();
			break;

		case 1:
			AlertDialog.Builder builder1 = new AlertDialog.Builder(Tab_ThreeActivity.this);
			builder1.setTitle("카테고리선택");
			String[] aaa ={"카테고리편집","환경설정"};
			builder1.setItems(aaa, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(which==0){
						Intent ddd = new Intent(getApplicationContext(),EditCategory.class);
						startActivity(ddd);
					}else{
						Toast.makeText(getApplicationContext(), "추후 업데이트 예정입니다.",1000).show();
					}
				}
			});
			AlertDialog alert1 = builder1.create();
			alert1.show();
			break;
				}
		return super.onOptionsItemSelected(item);
	}
    void ShowToast(String toast){
    	Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    } 
    void editlist(int index,int obj){
    	ContentValues values = new ContentValues();
    	values.put("budget",obj);
    	Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
    	tabthreesqlite.updateTable(values,"name",nameArrayList.get(index),"date",ddate1.getTime()+"");
    	
    	budgetArrayList.set(index, obj);
    	adapter.notifyDataSetChanged();
    }
    void dellist(int num){
    	tabthreesqlite.datadel(String.valueOf(spendmonetArrayList.get(num)),nameArrayList.get(num),String.valueOf(budgetArrayList.get(num)));
    	wholemoneyarrayList.remove(num);
    	spendmonetArrayList.remove(num);
    	if(nameArrayList.get(num).equals("전체 예산")){
    		allspendviewed=false;
    	}
    	nameArrayList.remove(num);
    	
    	budgetArrayList.remove(num);
    	
    	adapter.notifyDataSetChanged();
    }
    void addlist(int wholemoney,int spendmoney,String name,int budget){
    	adapter.notifyDataSetChanged();
    	Log.d("name",name);
    	wholemoneyarrayList.add(wholemoney);
        spendmonetArrayList.add(spendmoney);
        nameArrayList.add(name);
        budgetArrayList.add(budget);
        
        Log.d("spendmoney",spendmoney+"");
        Log.d("name",name);
        Log.d("budget",budget+"");
        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
        ContentValues val = new ContentValues();
        val.put("date",ddate1.getTime());
        val.put("spendmoney",spendmoney);
        val.put("name",name);
        val.put("budget",budget);
        tabthreesqlite.insertTable(val);
        
        adapter.notifyDataSetChanged();
    }
    void DialogShow(){
		AlertDialog.Builder builder = new AlertDialog.Builder(Tab_ThreeActivity.this);
		builder.setTitle("예산 카테고리 추가");
		builder.setItems(util.yeosan_category, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,int which) {

			}							
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    private AlertDialog dialog_editbudget(final int index){
    	final View innerView = getLayoutInflater().inflate(R.layout.dialog_edit_budget, null);
    	 AlertDialog.Builder ab = new AlertDialog.Builder(this);
	        ab.setTitle("예산 설정");
	        ab.setView(innerView);
	        final EditText edt = (EditText)innerView.findViewById(R.id.editText2);

	        ab.setPositiveButton("설정",new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(!edt.getText().toString().equals("")){
						editlist(index, Integer.parseInt(edt.getText().toString()));
					}else{
						ShowToast("카테고리를 선택해 주세요.");
						}
				}
			});
	        ab.setNegativeButton("취소",new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
	        return ab.create();
    }
    
    private AlertDialog dialog_catecory_detail(){
    	final View innerView = getLayoutInflater().inflate(R.layout.dialog_category_detail, null);
    	 AlertDialog.Builder ab = new AlertDialog.Builder(this);
	        ab.setTitle("세부 카테고리");
	        final EditText ed1 = (EditText)innerView.findViewById(R.id.editText1);
	        final EditText ed2 = (EditText)innerView.findViewById(R.id.editText2);

	        ed1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(Tab_ThreeActivity.this);
					builder.setTitle("예산 설정");
					builder.setItems(util.Middleitems, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,int which) {
							ed1.setText(util.Middleitems[which]);
						}							
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
			});

	        ed2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub


					String tmp_str_edt = ed1.getText().toString();
					if(tmp_str_edt.equals("")){
						Toast.makeText(getApplicationContext(), "카테고리를 먼저 선택해 주세요.", Toast.LENGTH_SHORT).show();
					}else{
						AlertDialog.Builder builder = new AlertDialog.Builder(Tab_ThreeActivity.this);
						builder.setTitle("카테고리선택");
						Cursor ccc = detailKindofDB.RawQueryString("SELECT * FROM "+detailKindofDB.SQL_DBname+" WHERE kindof='"+tmp_str_edt+"'");
						
						ArrayList<String> aaarrayList = new ArrayList<String>();
					    if(ccc.moveToFirst()){
					    	do{
					    		aaarrayList.add(ccc.getString(2));
					    	}while(ccc.moveToNext());
					    }
					    final String[] argss = aaarrayList.toArray(new String[aaarrayList.size()]);
					    builder.setItems(argss, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								ed2.setText(argss[which]);
							}							
						});
						AlertDialog alert = builder.create();
						alert.show();
					}


				}
			});
	        ab.setView(innerView);
	        ab.setPositiveButton("추가",new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(!ed1.getText().toString().equals("")&&!ed2.getText().toString().equals("")){

						GregorianCalendar grecal=new GregorianCalendar( calendar_1_bt.get(Calendar.YEAR), calendar_1_bt.get(Calendar.MONTH), calendar_1_bt.get(Calendar.DAY_OF_MONTH));
						int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
						int Firstday = grecal.getActualMinimum(Calendar.DAY_OF_MONTH);
						Date d1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
						d1.setDate(Firstday);
						Date d2 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
						d2.setDate(Lastday);
						int spendmoney_MONTH=0;

						String SQLstr = "SELECT money FROM "+moneyBookDB.SQL_DBname+" WHERE kindof LIKE '"+ed1.getText().toString()+"+"+ed2.getText().toString()+"' AND date BETWEEN "+String.valueOf(d1.getTime())+" AND "+String.valueOf(d2.getTime());
						//Log.d("",SQLstr);
						Cursor c = moneyBookDB.RawQueryString(SQLstr);

						if(c.getCount()!=0){
							if(c.moveToFirst()){
							do{
								spendmoney_MONTH+=Integer.parseInt(c.getString(0));
							}while(c.moveToNext());
							}
						}



					addlist(0,spendmoney_MONTH,ed1.getText().toString()+"-"+ed2.getText().toString(),0);
					}else{
						ShowToast("카테고리를 선택 해주세요.");
						}
				}
			});
	        ab.setNegativeButton("취소",new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
	        return ab.create();
    } 
    private AlertDialog dialog_catecory(){
    	final View innerView = getLayoutInflater().inflate(R.layout.dialog_category, null);
    	 AlertDialog.Builder ab = new AlertDialog.Builder(this);
	        ab.setTitle("예산 설정");
	        ab.setView(innerView);
	        final EditText edt = (EditText)innerView.findViewById(R.id.editText2);
	        edt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(Tab_ThreeActivity.this);
					builder.setTitle("카테고리선택");
					builder.setItems(util.Middleitems, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,int which) {
							edt.setText(util.Middleitems[which]);
						}							
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
			});
	        ab.setPositiveButton("추가",new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(!edt.getText().toString().equals("")){

						GregorianCalendar grecal=new GregorianCalendar( calendar_1_bt.get(Calendar.YEAR), calendar_1_bt.get(Calendar.MONTH), calendar_1_bt.get(Calendar.DAY_OF_MONTH));
						int Lastday = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
						int Firstday = grecal.getActualMinimum(Calendar.DAY_OF_MONTH);
						Date d1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
						d1.setDate(Firstday);
						Date d2 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
						d2.setDate(Lastday);
						int spendmoney_MONTH=0;
						String SQLstr = "SELECT money FROM "+moneyBookDB.SQL_DBname+" WHERE date BETWEEN "+String.valueOf(d1.getTime())+" AND "+String.valueOf(d2.getTime())+" AND kindof LIKE '"+edt.getText().toString()+"%'";
						Log.d("",SQLstr);

						Cursor c = moneyBookDB.RawQueryString(SQLstr);
						Log.d("",c.getCount()+"");

						if(c.getCount()>0){
							if(c.moveToFirst()){
							do{
								//Log.d("ds",""+c.getString(0));
								spendmoney_MONTH+=Integer.parseInt(c.getString(0));
							}while(c.moveToNext());
							}
						}


					addlist(0,spendmoney_MONTH,edt.getText().toString(),0);}else{ShowToast("카테고리를 선택해 주세요.");}
				}
			});
	        ab.setNegativeButton("취소",new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
	        return ab.create();
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.button5){
			if(moneyview1){
				bt5.setText("금액 숨김");
				moneyview1=false; 
				adapter = new TabthreeAdapter(getApplicationContext(),calendar_1_bt, R.layout.tab3_layout,wholemoneyarrayList,spendmonetArrayList,nameArrayList,budgetArrayList,moneyview1,moneyview2);
		        listView.setAdapter(adapter);     
			}else{
				bt5.setText("금액 표시");
				moneyview1=true;
				 adapter = new TabthreeAdapter(getApplicationContext(),calendar_1_bt, R.layout.tab3_layout,wholemoneyarrayList,spendmonetArrayList,nameArrayList,budgetArrayList,moneyview1,moneyview2);
			        listView.setAdapter(adapter);     
				}
		}else if(v.getId()==R.id.button6){
			if(moneyview2){
				bt6.setText("가능액 숨김");
				moneyview2=false;
				 adapter = new TabthreeAdapter(getApplicationContext(),calendar_1_bt, R.layout.tab3_layout,wholemoneyarrayList,spendmonetArrayList,nameArrayList,budgetArrayList,moneyview1,moneyview2);
			     listView.setAdapter(adapter);     
			}else{
				bt6.setText("가능액 표시");
				moneyview2=true;
				 adapter = new TabthreeAdapter(getApplicationContext(),calendar_1_bt, R.layout.tab3_layout,wholemoneyarrayList,spendmonetArrayList,nameArrayList,budgetArrayList,moneyview1,moneyview2);
			     listView.setAdapter(adapter);     
			}
		}
	}


}