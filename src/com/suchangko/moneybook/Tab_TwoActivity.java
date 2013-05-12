package com.suchangko.moneybook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 4. 3
 * Time: 오후 8:54
 * To change this template use File | Settings | File Templates.
 */
public class Tab_TwoActivity extends Activity implements OnClickListener {
	ListView lv;
	EditText edt_date;
	int tmp_moneyint=0;
	int tmp_spendint=0;
	Button bt_datepick;
	InputAdapter adapter;
	TextView tv_middle;
	MoneyBookDB mdb;
	private static final int DIALOG_DATE = 0;
	private static final int DIALOG_DATE_edt = 1;
	private static final int DIALOG_TIME_edt = 2;
	MoneyInputDB inputDB;
	GregorianCalendar grecal;
	Calendar c;
	int LastDay=0;
	Button bt_money;
	ArrayList<String> tmp_Content = new ArrayList<String>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        lv = (ListView)findViewById(R.id.listview1);
        bt_money=(Button)findViewById(R.id.button2);
        bt_datepick=(Button)findViewById(R.id.button1);
        bt_datepick.setOnClickListener(this);
        tv_middle=(TextView)findViewById(R.id.tv_middletv);
       
        grecal=new GregorianCalendar();
        c=Calendar.getInstance();
        bt_datepick.setText(c.get(Calendar.YEAR)+"년 "+(c.get(Calendar.MONTH)+1)+"월");
        inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
        inputDB.open();
        mdb =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
        mdb.open();
        madeAdapter();
        lv.setAdapter(adapter);
        Log.d("money",""+tmp_moneyint);
        Log.d("spend",tmp_spendint+"");
        String tmp_plus="";
        if((tmp_moneyint-tmp_spendint)>0){
        	tmp_plus="+";
        }else{
        }
        tv_middle.setText("이번 달 잔여금(수입-지출):"+tmp_plus+(tmp_moneyint-tmp_spendint)+"원");
        
    }
    @Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.button1){
			showDialog(DIALOG_DATE);
		}
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	menu.add(0,0,0,"수입입력").setIcon(R.drawable.ic_menu_add);
	menu.add(0,1,0,"이전문자등록").setIcon(R.drawable.ic_menu_copy);
	menu.add(0,2,0,"즐겨찾기편집").setIcon(R.drawable.btn_star_off_disabled_holo_light);
	menu.add(1,3,0,"수입내역검색").setIcon(R.drawable.ic_menu_search);
	menu.add(1,4,0,"회사소개").setIcon(R.drawable.ic_menu_notifications);
	menu.add(1,5,0,"더보기").setIcon(R.drawable.ic_menu_more);	
	return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int itemid =item.getItemId();
		switch (itemid) {
		case 0:
			Toast.makeText(this,"수입입력",100).show();
			AlertDialog a = dialog_add_spend();
			a.show();
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
    
    private DatePickerDialog.OnDateSetListener dateListener = 
	        new DatePickerDialog.OnDateSetListener() {
	         
	        @Override
	        public void onDateSet(DatePicker view, int year, int monthOfYear,
	                int dayOfMonth) {
	        	
	        	bt_datepick.setText(year+"년 "+(monthOfYear+1)+"월");
	        	grecal = new GregorianCalendar(year,monthOfYear,dayOfMonth);
	        	c.set(year, monthOfYear, dayOfMonth);
	        	adapter=null;
            	tmp_Content.clear();
            	madeAdapter();
            	adapter.notifyDataSetChanged();
            	lv.setAdapter(adapter);
	        	
	        }
	    };
	 private DatePickerDialog.OnDateSetListener edt_dateListener = 
		        new DatePickerDialog.OnDateSetListener() {
		         
		        @Override
		        public void onDateSet(DatePicker view, int year, int monthOfYear,
		                int dayOfMonth) {
		        	//bt_datepick.setText(year+"년 "+(monthOfYear+1)+"월");
		        	Date tmp_date = new Date(year-1900,monthOfYear,dayOfMonth);
		        	SimpleDateFormat dateformat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		        	edt_date.setText(dateformat.format(tmp_date));
		        }
		    };

		
 @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
        case DIALOG_DATE:
        	Calendar c1 = Calendar.getInstance();
            return new DatePickerDialog(this, dateListener,c1.get(Calendar.YEAR),c1.get(Calendar.MONTH),c1.get(Calendar.DAY_OF_MONTH));
        case DIALOG_DATE_edt:
        	Calendar c2 = Calendar.getInstance();
        	return new DatePickerDialog(this, edt_dateListener,c2.get(Calendar.YEAR),c2.get(Calendar.MONTH),c2.get(Calendar.DAY_OF_MONTH));
     
    }
        return null;
 }
    
	 private AlertDialog dialog_add_spend() {
	        final View innerView = getLayoutInflater().inflate(R.layout.dialog_add_input, null);
	        AlertDialog.Builder ab = new AlertDialog.Builder(this);
	        ab.setTitle("수입내역");
	        ab.setView(innerView);
	        edt_date = (EditText)innerView.findViewById(R.id.dialog_edit_date);
	        final EditText edt_money =(EditText)innerView.findViewById(R.id.dialog_edit_money);
	        final EditText edt_content = (EditText)innerView.findViewById(R.id.dialog_edit_content);
	        final EditText edt_memo = (EditText)innerView.findViewById(R.id.dialog_edit_memo);
	        final EditText edt_middle = (EditText)innerView.findViewById(R.id.dialog_edit_middle);
	        
	        edt_middle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
						

					AlertDialog.Builder builder = new AlertDialog.Builder(Tab_TwoActivity.this);
					builder.setTitle("수입 분류 선택");
					builder.setItems(util.Middleitems_input, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_middle.setText(util.Middleitems_input[item]);
					    	//Toast.makeText(getApplicationContext(), util.Middleitems[item], Toast.LENGTH_SHORT).show();
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
			});
	        //Calendar cc = Calendar.getInstance();
	        //String tmp_date = cc.get(Calendar.YEAR)+"-"+(cc.get(Calendar.MONTH)+1)+"-"+cc.get(Calendar.DAY_OF_MONTH);
	        //String tmp_time =  cc.get(Calendar.HOUR_OF_DAY)+":"+cc.get(Calendar.MINUTE);
	        
	        
	        SimpleDateFormat formatter1 = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
	        final Date currentDate = new Date ( );
	        String dDate = formatter1.format ( currentDate );
	        SimpleDateFormat formatter2 = new SimpleDateFormat ( "HH:mm", Locale.KOREA );
	        Date currentTime = new Date ( );
	        String dTime = formatter2.format ( currentTime );
	        
	        edt_date.setText(dDate);
	        
	        edt_date.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showDialog(DIALOG_DATE_edt);
				}
			});
	        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface arg0, int arg1) {
	            	Log.d("edt_date",edt_date.getText().toString());
	            	String[] tmp_str_date = edt_date.getText().toString().split("-");
	            	Log.d("Good",tmp_str_date[0]);
	            	Log.d("Good",tmp_str_date[1]);
	            	Log.d("Good",tmp_str_date[2]);
	            	Log.d("Hello", "Good");
	            	
	            
	            	Date tmp_date = new Date(Integer.parseInt(tmp_str_date[0])-1900,Integer.parseInt(tmp_str_date[1])-1,Integer.parseInt(tmp_str_date[2]));
	            	String aaa = String.valueOf(currentDate.getTime());
	            	aaa = ""+tmp_date.getTime();
	            	Log.d("",aaa);
	            	//Date tmp_date_sql = new Date(edt_date.getText().toString());
	            	Log.d("", "1");
	            	ContentValues val = new ContentValues();
	            	val.put("content",edt_content.getText().toString());
	            	val.put("memo",edt_memo.getText().toString());
	            	val.put("money",Integer.parseInt(edt_money.getText().toString()));
	            
	            	val.put("date",aaa);
	            	val.put("kindof",edt_middle.getText().toString());
	            
	     
	            	inputDB.insertTable(val);
	            	adapter=null;
	            	tmp_Content.clear();
	            	madeAdapter();
	            	adapter.notifyDataSetChanged();
	            	lv.setAdapter(adapter);
	            }
	        });
	         ab.setNeutralButton("즐겨찾기",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
	        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface arg0, int arg1) {
	              //  setDismiss(mDialog);
	            }
	        });
	          
	        return ab.create();
	    }
	 public void madeAdapter(){
			tmp_moneyint=0;
			tmp_spendint=0;
		        LastDay = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
		        int i=0;
		        while(i<LastDay){
		        	int a = LastDay;
		        	int year_ = c.get(Calendar.YEAR);
		        	int month_ = c.get(Calendar.MONTH)+1;
		        	
		          	Date tmp_date = new Date(year_-1900, month_-1, a-i);
		        	
		        	SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		        	String str_tmp_date = formatter.format(tmp_date);
		        	
		        	
		        	//mGroupList.add(year_+"."+month_+"."+(a-i));
		        
		        	//mChildListContent.clear();
		        	String[] columns={"content","memo","money","kindof","date"};
		        	String selection="date=?";
		        	String[] selectionArgs={
		        			//"1366708459052"
		        			String.valueOf(tmp_date.getTime())
		        			};
		        	//Log.d("Timestamp",String.valueOf(tmp_date.getTime()));
		        	Cursor c = inputDB.selectTable(columns, selection, selectionArgs, null,null,null);
		        	Cursor c_money = mdb.selectTable(columns, selection, selectionArgs, null,null,null);
		        	
		        	
		        	int tmp_count=c.getCount();
		        	Log.d("count",""+tmp_count);
		        	if(tmp_count>0){
		        		int tmp_money = 0;
		        		
		        		if(c.moveToFirst()){
		        			do{
		        				
		        				tmp_money += Integer.parseInt(c.getString(2));
		        			
		        				String tmp_contentString =str_tmp_date+"#"+c.getString(3)+"#"+c.getString(0)+"#"+c.getString(2)+"원";
		        				tmp_Content.add(tmp_contentString);	    	  
				        	}while(c.moveToNext());
		        		}
		        		
		        		tmp_moneyint+=tmp_money;
		        		
		        		Log.d("",""+tmp_moneyint);
		        	}else{
		        	}
		        	adapter = new InputAdapter(getApplicationContext(), R.layout.list_input,tmp_Content);
		        	i++;
		        	int tmp_spend=0;
		        	if(c_money.moveToFirst()){
		        		do{	tmp_spend+=Integer.parseInt(c_money.getString(2));}while(c_money.moveToNext());
		        	}
		        	tmp_spendint+=tmp_spend;
		        }

	        	bt_money.setText(""+tmp_moneyint+"원");
		}

	
}