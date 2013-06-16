package com.suchangko.moneybook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import com.suchangko.moneybook.Tab_OneActivity.dialogAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	ArrayList<HashMap<String,String>> list;
	ListView lv;
	EditText edt_date;
	int tmp_moneyint=0;
	int tmp_spendint=0;
	Button bt_datepick;
	InputAdapter adapter;
	TextView tv_middle;
	MoneyBookDB mdb;
	AlertDialog favordiallist;
	FavorInputDB favorInputDB;
	private static final int DIALOG_DATE = 0;
	private static final int DIALOG_DATE_edt = 1;
	private static final int DIALOG_TIME_edt = 2;
	private static final int DIALOG_DATE_start = 3;
	private static final int DIALOG_DATE_fin = 4;
	MoneyInputDB inputDB;
	Button Search_date_start;
	Button Search_date_fin;
	GregorianCalendar grecal;
	Calendar c;
	int LastDay=0;
	AlertDialog dd;
	Button bt_money;
	ArrayList<String> tmp_Content = new ArrayList<String>();
	AlertDialog favorlistdialog;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        lv = (ListView)findViewById(R.id.listview1);
        bt_money=(Button)findViewById(R.id.button2);
        bt_datepick=(Button)findViewById(R.id.button1);
        bt_datepick.setOnClickListener(this);
        tv_middle=(TextView)findViewById(R.id.tv_middletv);
        AlertDialog dd;
        grecal=new GregorianCalendar();
        c=Calendar.getInstance();
        bt_datepick.setText(c.get(Calendar.YEAR)+"년 "+(c.get(Calendar.MONTH)+1)+"월");
        inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
        inputDB.open();
        mdb =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
        mdb.open();
        favorInputDB = new FavorInputDB(this,FavorInputDB.SQL_Create_favorinputdb,FavorInputDB.SQL_DBname);
        favorInputDB.open();
        madeAdapter();
        lv.setAdapter(adapter);
        Log.d("money",""+tmp_moneyint);
        Log.d("spend",tmp_spendint+"");
        lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(Tab_TwoActivity.this);
				builder.setTitle("지출 세부 분류 선택");
				
					builder.setItems(util.fixdel, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    
					    	if(util.fixdel[item].equals("삭제")){
					    		inputDB.datadel(""+adapter.getid(arg2));					    		
					    		Toast.makeText(getApplicationContext(), "삭제되었습니다.",Toast.LENGTH_SHORT).show();
					    		madeAdapter();
					    		lv.setAdapter(adapter);
					    	}else{
					    		favordiallist = dialog_editing(adapter.getid(arg2));
					    		favordiallist.show();
					    	}
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				return false;
			}
        	
		}); 
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
			favorlistdialog= dialog_list_favor();
			favorlistdialog.show();
			break;
		case 3:
			AlertDialog a2 = dialog_search();
			a2.show();
			break;
		case 4:
			Intent i = new Intent(this,CompanyIntro.class);
			startActivity(i);
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
			private DatePickerDialog.OnDateSetListener dateListenerfinish = 
			        new DatePickerDialog.OnDateSetListener() {
			         
			        @Override
			        public void onDateSet(DatePicker view, int year, int monthOfYear,
			                int dayOfMonth) {
			        	Search_date_fin.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
			        	grecal = new GregorianCalendar(year,monthOfYear,dayOfMonth);
			        	c.set(year, monthOfYear, dayOfMonth);
			        	/*
			        	baseadapter=null;
		            	
		            	madeAdapter();
		            	baseadapter.notifyDataSetChanged();
		            	mListView.setAdapter(baseadapter);
		            	*/
			        	
			        }
			    };
			    
				 private DatePickerDialog.OnDateSetListener dateListenerstart = 
					        new DatePickerDialog.OnDateSetListener() {
					         
					        @Override
					        public void onDateSet(DatePicker view, int year, int monthOfYear,
					                int dayOfMonth) {
					        	Search_date_start.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
					        	grecal = new GregorianCalendar(year,monthOfYear,dayOfMonth);
					        	c.set(year, monthOfYear, dayOfMonth);
					        	/*
					        	baseadapter=null;
				            	
				            	madeAdapter();
				            	baseadapter.notifyDataSetChanged();
				            	mListView.setAdapter(baseadapter);
				            	*/
					        	
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
        case DIALOG_DATE_start:
        	Calendar c4 = Calendar.getInstance();
        	return new DatePickerDialog(this, dateListenerstart,c4.get(Calendar.YEAR),c4.get(Calendar.MONTH),c4.get(Calendar.DAY_OF_MONTH));
        case DIALOG_DATE_fin:
        	Calendar c5 = Calendar.getInstance();
        	return new DatePickerDialog(this, dateListenerfinish,c5.get(Calendar.YEAR),c5.get(Calendar.MONTH),c5.get(Calendar.DAY_OF_MONTH));
    }
        return null;
 }
 private AlertDialog dialog_editing(final int num){
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

		String[] columns={"content","memo","money","kindof","date","_id"};
		String selection="_id=?";
		String[] selectionArgs={
				        			//"1366708459052"
				        			String.valueOf(num)
				        			};
       
       Cursor cursor = inputDB.selectTable(columns, selection, selectionArgs, null, null, null);
       cursor.moveToNext();
       Date datedd = new Date(Long.parseLong(cursor.getString(4)));
       
       edt_content.setText(cursor.getString(0));
       edt_memo.setText(cursor.getString(1));
       edt_middle.setText(cursor.getString(3));
       edt_money.setText(cursor.getString(2));
       edt_date.setText(formatter1.format(datedd));
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
           
           	inputDB.datadel(""+num);
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
 private AlertDialog dialog_list_favor(){
	 final View innerView = getLayoutInflater().inflate(R.layout.dialog_list_favor, null);
	 final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("수입 즐겨찾기 편집");
        LinearLayout textView =(LinearLayout) innerView.findViewById(R.id.shortcutrow_nodata);
        ListView lview =(ListView)innerView.findViewById(R.id.dial_list);	        
        
        
        
        String[] columns={"content","memo","money","kindof","_id"};
    	String selection="date=?";
    	
    	String selectionArgs="";
    	
        Cursor tmpc = favorInputDB.selectTable(columns,null,null,null,null,null);
        if(tmpc.getCount()==0){
        	lview.setVisibility(View.GONE);
        	textView.setVisibility(View.VISIBLE);
        }else{
        	
        	textView.setVisibility(View.GONE);
        	list = new ArrayList<HashMap<String,String>>();
        	if(tmpc.moveToNext()){
        		do{
        			HashMap<String,String> map = new HashMap<String, String>();
        			map.put("0",tmpc.getString(0));
        			map.put("1",tmpc.getString(1));
        			map.put("2",tmpc.getString(2));
        			map.put("3",tmpc.getString(3));
        			map.put("4",tmpc.getString(4));
        		//	map.put("5",tmpc.getString(5));
        			Log.d("0",tmpc.getString(0));
        			Log.d("1",tmpc.getString(1));
        			Log.d("2",tmpc.getString(2));
        			Log.d("3",tmpc.getString(3));
        			Log.d("4",tmpc.getString(4));
        			//Log.d("5",tmpc.getString(5));
        			list.add(map);
        		}while(tmpc.moveToNext());
        	}
        	dialogAdapter dAdapter = new dialogAdapter(getApplicationContext(), R.layout.listrow_shortcut,list);
        	lview.setAdapter(dAdapter);
        	lview.setOnItemClickListener(new OnItemClickListener() {
        		
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					//Toast.makeText(getApplicationContext(), list.size() +" dsa"+ arg2,1000).show();
					//AlertDialog dialog_ = dialog_edit_favor_edit(list.get(arg2));
					
					favorlistdialog.dismiss();
					HashMap<String,String> h = list.get(arg2);
					AlertDialog dialog_ = dialog_edit_favor_edit(h);
					dialog_.show();
					
					
				}
			});
        	lview.setVisibility(View.VISIBLE);
        }
        
        
        
        ab.setView(innerView);
        ab.setPositiveButton("추가",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				AlertDialog alertDialog = dialog_edit_favor();
				alertDialog.show();
			}
		});
        ab.setNegativeButton("나가기",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
        return ab.create();	
 } private AlertDialog dialog_list_favor_input(){
	 final View innerView = getLayoutInflater().inflate(R.layout.dialog_list_favor, null);
	 final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("수입 즐겨찾기 편집");
        LinearLayout textView =(LinearLayout) innerView.findViewById(R.id.shortcutrow_nodata);
        ListView lview =(ListView)innerView.findViewById(R.id.dial_list);	        
        
        
        
        String[] columns={"content","memo","money","kindof","_id"};
    	String selection="date=?";
    	
    	String selectionArgs="";
    	
        Cursor tmpc = favorInputDB.selectTable(columns,null,null,null,null,null);
        if(tmpc.getCount()==0){
        	lview.setVisibility(View.GONE);
        	textView.setVisibility(View.VISIBLE);
        }else{
        	
        	textView.setVisibility(View.GONE);
        	list = new ArrayList<HashMap<String,String>>();
        	if(tmpc.moveToNext()){
        		do{
        			HashMap<String,String> map = new HashMap<String, String>();
        			map.put("0",tmpc.getString(0));
        			map.put("1",tmpc.getString(1));
        			map.put("2",tmpc.getString(2));
        			map.put("3",tmpc.getString(3));
        			map.put("4",tmpc.getString(4));
        		//	map.put("5",tmpc.getString(5));
        			Log.d("0",tmpc.getString(0));
        			Log.d("1",tmpc.getString(1));
        			Log.d("2",tmpc.getString(2));
        			Log.d("3",tmpc.getString(3));
        			Log.d("4",tmpc.getString(4));
        			//Log.d("5",tmpc.getString(5));
        			list.add(map);
        		}while(tmpc.moveToNext());
        	}
        	dialogAdapter dAdapter = new dialogAdapter(getApplicationContext(), R.layout.listrow_shortcut,list);
        	lview.setAdapter(dAdapter);
        	lview.setOnItemClickListener(new OnItemClickListener() {
        		
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					//Toast.makeText(getApplicationContext(), list.size() +" dsa"+ arg2,1000).show();
					//AlertDialog dialog_ = dialog_edit_favor_edit(list.get(arg2));
					
					//favorlistdialog.dismiss();
				dd.dismiss();
					HashMap<String,String> h = list.get(arg2);
					AlertDialog dialog_ = dialog_add_spend(h);
					dialog_.show();
					
					
				}
			});
        	lview.setVisibility(View.VISIBLE);
        }
        
        
        
        ab.setView(innerView);
   
        ab.setNegativeButton("나가기",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
        return ab.create();	
 }
 private AlertDialog dialog_edit_favor_edit(final HashMap<String,String> map){
	 final View innerView = getLayoutInflater().inflate(R.layout.dialog_add_input, null);
     AlertDialog.Builder ab = new AlertDialog.Builder(this);
     ab.setTitle("수입내역");
     ab.setView(innerView);
     edt_date = (EditText)innerView.findViewById(R.id.dialog_edit_date);
     final EditText edt_money =(EditText)innerView.findViewById(R.id.dialog_edit_money);
     final EditText edt_content = (EditText)innerView.findViewById(R.id.dialog_edit_content);
     final EditText edt_memo = (EditText)innerView.findViewById(R.id.dialog_edit_memo);
     final EditText edt_middle = (EditText)innerView.findViewById(R.id.dialog_edit_middle);
     edt_money.setText(map.get("2"));
     edt_memo.setText(map.get("1"));
     edt_content.setText(map.get("0"));
     edt_middle.setText(map.get("3"));
     
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
     ab.setPositiveButton("수정", new DialogInterface.OnClickListener() {
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
         
         	favorInputDB.datadel(map.get("4"));
         	favorInputDB.insertTable(val);
         	adapter=null;
         	tmp_Content.clear();
         	madeAdapter();
         	adapter.notifyDataSetChanged();
         	lv.setAdapter(adapter);
         }
     });
      ab.setNeutralButton("삭제",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.d("",map.get("4"));
				favorInputDB.datadel(map.get("4"));
				Toast.makeText(getApplicationContext(), "삭제 완료 되었습니다.",Toast.LENGTH_SHORT).show();
				favorlistdialog = dialog_list_favor();
				favorlistdialog.show();
			}
		});
     ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface arg0, int arg1) {
           //  setDismiss(mDialog);
        	 favorlistdialog = dialog_list_favor();
				favorlistdialog.show();
         }
     });
       
     return ab.create();
 }
 
 
 private AlertDialog dialog_search(){
	 final View innerView = getLayoutInflater().inflate(R.layout.dialog_searche, null);
	 AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("수입 내역 검색");
        ab.setView(innerView);
        final EditText editText_1 = (EditText)innerView.findViewById(R.id.dialog_searche_word);
        //editText_1.setHint("검색어를 입력해 주세요");
        
        Search_date_start = (Button)innerView.findViewById(R.id.dialog_searche_startdate);
        
        Search_date_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(Tab_TwoActivity.this);
				builder.setTitle("검색 시작일 설정");
				builder.setItems(util.searchdate, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if(util.searchdate[item].equals("전체 기간")){
				    		
				    	}else if(util.searchdate[item].equals("기간 설정")){
				    		showDialog(DIALOG_DATE_start);
				    	}				    	
				    }
				    
				    
				});
				AlertDialog alert = builder.create();

				alert.show();
			}
		});
		
        
        Search_date_fin = (Button)innerView.findViewById(R.id.dialog_searche_enddate);
        
        Search_date_fin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(Tab_TwoActivity.this);
				builder.setTitle("검색 시작일 설정");
				builder.setItems(util.searchdate, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if(util.searchdate[item].equals("전체 기간")){
				    		
				    	}else if(util.searchdate[item].equals("기간 설정")){
				    		showDialog(DIALOG_DATE_fin);
				    	}				    	
				    }
				    
				    
				});
				AlertDialog alert = builder.create();

				alert.show();
			}
		});
		
        final Button b3 = (Button)innerView.findViewById(R.id.dialog_searche_cate);
        b3.setText("전체");
        b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(Tab_TwoActivity.this);
				builder.setTitle("검색 분류 선택");
				builder.setItems(util.Middleitems_input1, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	b3.setText(util.Middleitems_input1[item]);
				    	if(b3.getText().toString().equals("전체")){
				    	
				    	}
				    	
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
       

        ab.setView(innerView);
        ab.setPositiveButton("검색",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(editText_1.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "검색어를 입력해 주세요.",Toast.LENGTH_SHORT).show();
				}else{
					if(!Search_date_start.getText().toString().equals("") && !Search_date_fin.getText().toString().equals("")){
						//날짜 검색
						Calendar calendar1 = Calendar.getInstance();
						Calendar calendar2 = Calendar.getInstance();
						String[] strs1 = Search_date_start.getText().toString().split("\\-");
						String[] strs2 = Search_date_fin.getText().toString().split("\\-");
						
						calendar1.set(Integer.parseInt(strs1[0]),Integer.parseInt(strs1[1])-1,Integer.parseInt(strs1[2]));
						calendar2.set(Integer.parseInt(strs2[0]),Integer.parseInt(strs2[1])-1,Integer.parseInt(strs2[2]));
						
						
						int[] cs = {calendar1.get(Calendar.YEAR),
								(calendar1.get(Calendar.MONTH)+1),
								calendar1.get(Calendar.DAY_OF_MONTH)};
						int[] cf = {calendar2.get(Calendar.YEAR),
								(calendar2.get(Calendar.MONTH)+1),
								calendar2.get(Calendar.DAY_OF_MONTH)};
						Date dates = new Date(cs[0]-1900, cs[1]-1, cs[2]);
						Date datef = new Date(cf[0]-1900, cf[1]-1, cf[2]);
						
						if(dates.getTime() > datef.getTime()){
							Toast.makeText(getApplicationContext(), "검색 기간을 확인해 주세요.",Toast.LENGTH_SHORT).show();
						}else{
						
						
						String searchstr = editText_1.getText().toString();
						String kind1 = b3.getText().toString();
						
						
						
						Intent i = new Intent(getApplicationContext(),SearcheActivity.class);
						i.putExtra("cs",cs);
						i.putExtra("cf",cf);
						i.putExtra("str",searchstr);
						i.putExtra("kind",kind1);
					
						startActivity(i);
						}
						
						

						
						/*
						calendar1.get(Calendar.YEAR)
						(calendar1.get(Calendar.MONTH)+1)
						calendar1.get(Calendar.DAY_OF_MONTH)
						*/
					}else if(Search_date_start.getText().toString().equals("") && Search_date_fin.getText().toString().equals("")){
						//전체검색
						
						int[] cs = {0,0,0};
						int[] cf = {0,0,0};
						
						String searchstr = editText_1.getText().toString();
						String kind1 = b3.getText().toString();
					
						
						Intent i = new Intent(getApplicationContext(),SearchsActivity.class);
						i.putExtra("cs",cs);
						i.putExtra("cf",cf);
						i.putExtra("str",searchstr);
				
						startActivity(i);
						
					}else{
						Toast.makeText(getApplicationContext(), "검색 기간을 확인해 주세요.",Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
        ab.setNegativeButton("취소",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
        return ab.create();
 }
 private AlertDialog dialog_edit_favor(){
	 final View innerView = getLayoutInflater().inflate(R.layout.dialog_favorite_input_edit, null);
	 AlertDialog.Builder ab = new AlertDialog.Builder(this);
     
	 final EditText edt_money =(EditText)innerView.findViewById(R.id.dialog_edit_money);
     final EditText edt_content = (EditText)innerView.findViewById(R.id.dialog_edit_content);
     final EditText edt_memo = (EditText)innerView.findViewById(R.id.dialog_edit_memo);
     final EditText edt_detail =  (EditText)innerView.findViewById(R.id.dialog_edit_detail);
     
     
     edt_detail.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(Tab_TwoActivity.this);
			builder.setTitle("수입 분류 선택");
			builder.setItems(util.Middleitems_input, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	edt_detail.setText(util.Middleitems_input[item]);
			    	//Toast.makeText(getApplicationContext(), util.Middleitems[item], Toast.LENGTH_SHORT).show();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	});
	 
	 ab.setTitle("수입 즐겨찾기 편집");
        
     ab.setView(innerView);
        
        
        
        ab.setPositiveButton("입력",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				ContentValues val = new ContentValues();
				val.put("content",edt_content.getText().toString());
            	val.put("memo",edt_memo.getText().toString());
            	val.put("money",Integer.parseInt(edt_money.getText().toString()));
            	val.put("kindof",edt_detail.getText().toString());
            	favorInputDB.insertTable(val);
				Toast.makeText(getApplicationContext(), "입력되었습니다.",Toast.LENGTH_SHORT).show();
				favorlistdialog = dialog_list_favor();
				favorlistdialog.show();
			}
		});
        ab.setNegativeButton("취소",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
        return ab.create();
        
 } private AlertDialog dialog_edit_favor_input(){
	 final View innerView = getLayoutInflater().inflate(R.layout.dialog_favorite_input_edit, null);
	 AlertDialog.Builder ab = new AlertDialog.Builder(this);
     
	 final EditText edt_money =(EditText)innerView.findViewById(R.id.dialog_edit_money);
     final EditText edt_content = (EditText)innerView.findViewById(R.id.dialog_edit_content);
     final EditText edt_memo = (EditText)innerView.findViewById(R.id.dialog_edit_memo);
     final EditText edt_detail =  (EditText)innerView.findViewById(R.id.dialog_edit_detail);
     
     
     edt_detail.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(Tab_TwoActivity.this);
			builder.setTitle("수입 분류 선택");
			builder.setItems(util.Middleitems_input, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	edt_detail.setText(util.Middleitems_input[item]);
			    	//Toast.makeText(getApplicationContext(), util.Middleitems[item], Toast.LENGTH_SHORT).show();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	});
	 
	 ab.setTitle("수입 즐겨찾기 편집");
        
     ab.setView(innerView);
        
        
        
        ab.setPositiveButton("입력",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				ContentValues val = new ContentValues();
				val.put("content",edt_content.getText().toString());
            	val.put("memo",edt_memo.getText().toString());
            	val.put("money",Integer.parseInt(edt_money.getText().toString()));
            	val.put("kindof",edt_detail.getText().toString());
            	favorInputDB.insertTable(val);
				Toast.makeText(getApplicationContext(), "입력되었습니다.",Toast.LENGTH_SHORT).show();
				favorlistdialog = dialog_list_favor();
				favorlistdialog.show();
			}
		});
        ab.setNegativeButton("취소",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
        return ab.create();
        
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
					dd = dialog_list_favor_input();					
					dd.show();
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
	 private AlertDialog dialog_add_spend(HashMap<String,String> h) {
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
	        
	        edt_money.setText(h.get("2"));
	        edt_content.setText(h.get("0"));
	        edt_memo.setText(h.get("1"));
	        edt_middle.setText(h.get("3"));
	        
	        
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
					AlertDialog dd = dialog_list_favor_input();
					
					dd.show();
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
			tmp_Content.clear();
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
		        	String[] columns={"content","memo","money","kindof","date","_id"};
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
		        			
		        				String tmp_contentString =str_tmp_date+"#"+c.getString(3)+"#"+c.getString(1)+"#"+c.getString(2)+"원"+"#"+c.getInt(5);
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
	        	
	        	String tmp_plus="";
	            
	       	 
	       	 
	            int tmp_i = tmp_moneyint - tmp_spendint;
	            Log.d("","값"+tmp_i);
	            if((tmp_moneyint-tmp_spendint)>0){
	            	tmp_plus="+";
	            }else{
	           	 
	            }
	            
	            
	            tv_middle.setText("이번 달 잔여금(수입-지출):"+tmp_plus+(tmp_moneyint-tmp_spendint)+"원");
		}
	 

		class dialogAdapter extends BaseAdapter{
			private LayoutInflater Inflater;
			private ArrayList<HashMap<String,String>> map;
			private int _layout;
			public dialogAdapter(Context c,int layout,ArrayList<HashMap<String,String>> map){
				Inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				_layout=layout;
				this.map=map;
			}
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return map.size();
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
				// TODO Auto-generated method stub
				if(arg1==null){
					arg1=Inflater.inflate(_layout, arg2,false);
				}
				HashMap<String, String> hashMap = map.get(arg0);
				
				TextView t1 = (TextView)arg1.findViewById(R.id.shortcutrow_where);
				TextView t2 = (TextView)arg1.findViewById(R.id.shortcutrow_cate);
				TextView t3 = (TextView)arg1.findViewById(R.id.shortcutrow_price);
				TextView t4 = (TextView)arg1.findViewById(R.id.shortcutrow_auto);
				t1.setText(hashMap.get("0"));
			//	String strings[] = hashMap.get("3").split("+");
				t2.setText(hashMap.get("3"));
				//t2.setText(strings[0]);
				t3.setText(hashMap.get("2")+"원");
				//t4.setText(hashMap.get("0"));
				return arg1;
			}
			
		}
}