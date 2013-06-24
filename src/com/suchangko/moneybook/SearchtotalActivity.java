package com.suchangko.moneybook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.suchangko.moneybook.SearcheActivity.ListViewAdapter1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SearchtotalActivity extends Activity {
	Intent i;	
	ListView lv;
	ArrayList<String> dateArrayList;
	ArrayList<String> whereArrayList;
	ArrayList<String> priceArrayList;
	ArrayList<String> idArrayList;
	MoneyBookDB mdb;
	MoneyInputDB inputdb;
	int searchcnt=0;
	ListViewAdapter1 adapter1;
	TextView tv;
	EditText edt_date1; //Dialog
    EditText edt_time1; //Dialog
    EditText edt_date2; //Dialog
    EditText edt_time2; //Dialog
    int code_;
    String datetext_;
    private static final int DIALOG_DATE_edt1 = 1;
	private static final int DIALOG_TIME_edt1 = 2;
	private static final int DIALOG_DATE_edt2 = 3;
	private static final int DIALOG_TIME_edt2 = 4;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchs);
        i=getIntent();
        final String datetext = i.getStringExtra("datetext");
        final int code = i.getIntExtra("code",0);
        tv = (TextView)findViewById(R.id.tv_title);
        dateArrayList=new ArrayList<String>();
        whereArrayList=new ArrayList<String>();
        priceArrayList=new ArrayList<String>();
        idArrayList=new ArrayList<String>();
        code_=code;
        datetext_=datetext;
        
        lv = (ListView)findViewById(R.id.listview1);
        
        madeAdapter(code,datetext);
        
        adapter1 = new ListViewAdapter1(getApplicationContext(), R.layout.listrow_search,dateArrayList,whereArrayList,priceArrayList,idArrayList);
        lv.setAdapter(adapter1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(code==1){
					AlertDialog.Builder builder = new AlertDialog.Builder(SearchtotalActivity.this);
    				builder.setTitle("선택");
    				builder.setItems(util.fixdel, new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int item) {
    						if(util.fixdel[item].equals("삭제")){
    					    	mdb.datadel(adapter1.getQueryID(arg2)+"");
    					    	Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
    					    	madeAdapter(code,datetext);
    					    	adapter1 = new ListViewAdapter1(getApplicationContext(), R.layout.listrow_search,dateArrayList,whereArrayList,priceArrayList,idArrayList);
    					        lv.setAdapter(adapter1);
    					    }else if(util.fixdel[item].equals("수정")){
    					    	
    					    	AlertDialog aaa = dialog_editing1(adapter1.getQueryID(arg2));
    					    	aaa.show();
    					    	
    					    	}
    					    }
    					});
    					AlertDialog alert = builder.create();
    					alert.show();
				}else if(code==2){
					AlertDialog.Builder builder = new AlertDialog.Builder(SearchtotalActivity.this);
    				builder.setTitle("선택");
    				builder.setItems(util.fixdel, new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int item) {
    						if(util.fixdel[item].equals("삭제")){
    					    	inputdb.datadel(adapter1.getQueryID(arg2)+"");
    					    	Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
    					    	madeAdapter(code,datetext);
    					    	adapter1 = new ListViewAdapter1(getApplicationContext(), R.layout.listrow_search,dateArrayList,whereArrayList,priceArrayList,idArrayList);
    					        lv.setAdapter(adapter1);
    					    }else if(util.fixdel[item].equals("수정")){
    					    	AlertDialog aaa = dialog_editing2(adapter1.getQueryID(arg2));
    					    	aaa.show();
    					    	}
    					    }
    					});
    					AlertDialog alert = builder.create();
    					alert.show();
				}
			}
		});
        
        //tv.setText("지출 검색 결과 : "+searchcnt+"개");
        
    }
    public void madeAdapter(int code,String datetext){
    	searchcnt=0;
		dateArrayList.clear();
		whereArrayList.clear();
		priceArrayList.clear();
		idArrayList.clear();
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
        			idArrayList.add(c.getString(c.getColumnIndex("_id")));
        			searchcnt++;
				}while(c.moveToNext());
			}
		}
		 tv.setText("지출 검색 결과 : "+searchcnt+"개");
		 adapter1 = new ListViewAdapter1(getApplicationContext(), R.layout.listrow_search,dateArrayList,whereArrayList,priceArrayList,idArrayList);
	        lv.setAdapter(adapter1);
    }
    
    
    private AlertDialog dialog_editing1(int idnum){
		 final View innerView = getLayoutInflater().inflate(R.layout.dialog_add_spend, null);
    AlertDialog.Builder ab = new AlertDialog.Builder(this);
    ab.setTitle("지출내역");
    ab.setView(innerView);
    edt_date1 = (EditText)innerView.findViewById(R.id.dialog_edit_date);
    edt_time1 = (EditText)innerView.findViewById(R.id.dialog_edit_time);
    final EditText edt_money =(EditText)innerView.findViewById(R.id.dialog_edit_money);
    final EditText edt_content = (EditText)innerView.findViewById(R.id.dialog_edit_content);
    final EditText edt_memo = (EditText)innerView.findViewById(R.id.dialog_edit_memo);
    final EditText edt_middle = (EditText)innerView.findViewById(R.id.dialog_edit_middle);
    final EditText edt_detail =  (EditText)innerView.findViewById(R.id.dialog_edit_detail);
    final EditText edt_spendhow = (EditText)innerView.findViewById(R.id.dialog_edit_spendhow);
    final EditText edt_spend_detail = (EditText)innerView.findViewById(R.id.dialog_edit_spend_detail);
    
    edt_middle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
					

				AlertDialog.Builder builder = new AlertDialog.Builder(SearchtotalActivity.this);
				builder.setTitle("지출 분류 선택");
				builder.setItems(util.Middleitems, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	edt_middle.setText(util.Middleitems[item]);
				    	edt_detail.setText("상세선택");
				    	//Toast.makeText(getApplicationContext(), util.Middleitems[item], Toast.LENGTH_SHORT).show();
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
    edt_detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(SearchtotalActivity.this);
				builder.setTitle("지출 세부 분류 선택");
				String tmp_middle = edt_middle.getText().toString();
				if(tmp_middle.equals("식비")){
					builder.setItems(util.detailitems1, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems1[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("교통비")){
					builder.setItems(util.detailitems2, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems2[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("교육비")){
					builder.setItems(util.detailitems3, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems3[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("건강,의료비")){
					builder.setItems(util.detailitems4, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems4[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("통신비")){
					builder.setItems(util.detailitems5, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems5[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("가구집기")){
					builder.setItems(util.detailitems6, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems6[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("주거비")){
					builder.setItems(util.detailitems7, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems7[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("품위유지비")){
					builder.setItems(util.detailitems8, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems8[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("교양,오락비")){
					builder.setItems(util.detailitems9, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems9[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("보험,저축비")){
					builder.setItems(util.detailitems10, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems10[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("사업운영비")){
					builder.setItems(util.detailitems11, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems11[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("수수료,세금")){
					builder.setItems(util.detailitems12, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems12[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else if(tmp_middle.equals("기타")){
					builder.setItems(util.detailitems13, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_detail.setText(util.detailitems13[item]);
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}else{
					Toast.makeText(getApplicationContext(), "분류를 선택해주세요.",1000).show();
				}
			}
		});
    //Calendar cc = Calendar.getInstance();
    //String tmp_date = cc.get(Calendar.YEAR)+"-"+(cc.get(Calendar.MONTH)+1)+"-"+cc.get(Calendar.DAY_OF_MONTH);
    //String tmp_time =  cc.get(Calendar.HOUR_OF_DAY)+":"+cc.get(Calendar.MINUTE);
    edt_spendhow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(SearchtotalActivity.this);
				builder.setTitle("결제 분류 선택");
				builder.setItems(util.spendhow, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if(util.spendhow[item].equals("현금")){
				    		edt_spend_detail.setText("현금");
				    	}else{
				    		edt_spend_detail.setText("-");
				    	}
				    	edt_spendhow.setText(util.spendhow[item]);
				    }
				});
				AlertDialog alert = builder.create();

				alert.show();
			}
		});
    edt_spend_detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edt_spend_detail.getText().toString().equals("현금")){
					Toast.makeText(getApplicationContext(), "현금으로 선택되었습니다.", Toast.LENGTH_SHORT).show();
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(SearchtotalActivity.this);
					builder.setTitle("결제 분류 선택");
					builder.setItems(util.spendkindof, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	edt_spend_detail.setText(util.spendkindof[item]);
					    	
					    	
					    }
					});
					AlertDialog alert = builder.create();

					alert.show();
				}
			}
		});
    
    SimpleDateFormat formatter1 = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    final Date currentDate = new Date ( );
    String dDate = formatter1.format ( currentDate );
    SimpleDateFormat formatter2 = new SimpleDateFormat ( "HH:mm", Locale.KOREA );
    Date currentTime = new Date ( );
    String dTime = formatter2.format ( currentTime );
    
    edt_date1.setText(dDate);
    edt_time1.setText(dTime);
    
    edt_date1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_DATE_edt1);
			}
		});
    edt_time1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DIALOG_TIME_edt1);
			}
		});
    
    ////////////////////////////////*************************//
    String[] columns={"content","memo","money","kindof","date","minutetime","moneykindof","_id"};
    String selection="_id=?";
    String[] selectionArgs={String.valueOf(idnum)};
    
    
    
    Cursor cursor = mdb.selectTable(columns, selection, selectionArgs,null,null,null);
    cursor.moveToNext();
   
    Date dated = new Date(Long.parseLong(cursor.getString(4)));
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    edt_date1.setText(format.format(dated));
    edt_time1.setText(cursor.getString(5));
    edt_money.setText(cursor.getString(2));
    edt_content.setText(cursor.getString(0));
    edt_memo.setText(cursor.getString(1));
    
    String[] tmp_t=cursor.getString(3).split("\\+");
    edt_middle.setText(tmp_t[0]);
    edt_detail.setText(tmp_t[1]);
    String[] tmp_a=cursor.getString(6).split("/");
    edt_spendhow.setText(tmp_a[0]);
    edt_spend_detail.setText(tmp_a[1]);
    final String idnumber = cursor.getString(7);
    ab.setPositiveButton("수정", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
        	Log.d("edt_date",edt_date1.getText().toString());
        	String[] tmp_str_date = edt_date1.getText().toString().split("-");
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
        	//val.put("date",Integer.parseInt(""+tmp_date_sql.getTime()));
        	val.put("date",aaa);
        	val.put("kindof",edt_middle.getText().toString()+"+"+edt_detail.getText().toString());
        	val.put("moneykindof",edt_spendhow.getText().toString()+"/"+edt_spend_detail.getText().toString());
        	val.put("minutetime",edt_time1.getText().toString());
        	mdb.datadel(idnumber);
        	mdb.insertTable(val);
        	
        	madeAdapter(code_,datetext_);
        }
    });/*
     ab.setNeutralButton("즐겨찾기",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog_alert = dialog_list_favor_input();
				dialog_alert.show();
			}
		});
		*/
    ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
          //  setDismiss(mDialog);
        }
    });
      
    return ab.create();
	 }
    
    
    private AlertDialog dialog_editing2(final int num){
 	   final View innerView = getLayoutInflater().inflate(R.layout.dialog_add_input, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("수입내역");
        ab.setView(innerView);
        edt_date2 = (EditText)innerView.findViewById(R.id.dialog_edit_date);
        final EditText edt_money =(EditText)innerView.findViewById(R.id.dialog_edit_money);
        final EditText edt_content = (EditText)innerView.findViewById(R.id.dialog_edit_content);
        final EditText edt_memo = (EditText)innerView.findViewById(R.id.dialog_edit_memo);
        final EditText edt_middle = (EditText)innerView.findViewById(R.id.dialog_edit_middle);
        
        edt_middle.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 					

 				AlertDialog.Builder builder = new AlertDialog.Builder(SearchtotalActivity.this);
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
        
        edt_date2.setText(dDate);
        
        edt_date2.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				showDialog(DIALOG_DATE_edt2);
 			}
 		});

 		String[] columns={"content","memo","money","kindof","date","_id"};
 		String selection="_id=?";
 		String[] selectionArgs={
 				        			//"1366708459052"
 				        			String.valueOf(num)
 				        			};
        
        Cursor cursor = inputdb.selectTable(columns, selection, selectionArgs, null, null, null);
        cursor.moveToNext();
        Date datedd = new Date(Long.parseLong(cursor.getString(4)));
        
        edt_content.setText(cursor.getString(0));
        edt_memo.setText(cursor.getString(1));
        edt_middle.setText(cursor.getString(3));
        edt_money.setText(cursor.getString(2));
        edt_date2.setText(formatter1.format(datedd));
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            	Log.d("edt_date",edt_date2.getText().toString());
            	String[] tmp_str_date = edt_date2.getText().toString().split("-");
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
            
            	inputdb.datadel(""+num);
            	inputdb.insertTable(val);
            	madeAdapter(code_, datetext_);
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
  
    private DatePickerDialog.OnDateSetListener edt_dateListener1 = 
	        new DatePickerDialog.OnDateSetListener() {
	         
	        @Override
	        public void onDateSet(DatePicker view, int year, int monthOfYear,
	                int dayOfMonth) {
	        	//bt_datepick.setText(year+"년 "+(monthOfYear+1)+"월");
	        	Date tmp_date = new Date(year-1900,monthOfYear,dayOfMonth);
	        	SimpleDateFormat dateformat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
	        	edt_date1.setText(dateformat.format(tmp_date));
	        }
	    };
private TimePickerDialog.OnTimeSetListener edt_timeListener1 = 
	new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			SimpleDateFormat timeformat = new SimpleDateFormat ( "HH:mm", Locale.KOREA );
	        Date tmp_time = new Date (2000,10,20,hourOfDay,minute);
	        edt_time1.setText(timeformat.format(tmp_time));
		}
	};
	 private DatePickerDialog.OnDateSetListener edt_dateListener2 = 
		        new DatePickerDialog.OnDateSetListener() {
		         
		        @Override
		        public void onDateSet(DatePicker view, int year, int monthOfYear,
		                int dayOfMonth) {
		        	//bt_datepick.setText(year+"년 "+(monthOfYear+1)+"월");
		        	Date tmp_date = new Date(year-1900,monthOfYear,dayOfMonth);
		        	SimpleDateFormat dateformat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		        	edt_date2.setText(dateformat.format(tmp_date));
		        }
		    };	
 @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
        case DIALOG_DATE_edt2:
        	Calendar c22 = Calendar.getInstance();
        	return new DatePickerDialog(this, edt_dateListener2,c22.get(Calendar.YEAR),c22.get(Calendar.MONTH),c22.get(Calendar.DAY_OF_MONTH));
        
       case DIALOG_DATE_edt1:
        	Calendar c2 = Calendar.getInstance();
        	return new DatePickerDialog(this, edt_dateListener1,c2.get(Calendar.YEAR),c2.get(Calendar.MONTH),c2.get(Calendar.DAY_OF_MONTH));
        case DIALOG_TIME_edt1:
        	Calendar c3 = Calendar.getInstance();
        	return new TimePickerDialog(this, edt_timeListener1,c3.get(Calendar.HOUR_OF_DAY),c3.get(Calendar.MINUTE),false);
     }
        return null;
 }

		
    class ListViewAdapter1 extends BaseAdapter{
    	private LayoutInflater Inflater;
    	private int _layout;
    	private ArrayList<String> a;
    	private ArrayList<String> b;
    	private ArrayList<String> d;
    	private ArrayList<String> id;
    	public ListViewAdapter1(Context c, int layout,ArrayList<String> a,ArrayList<String> b,ArrayList<String> d,ArrayList<String> id){
    		Inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		_layout=layout;
    		this.a=a;
    		this.b=b;
    		this.d=d;
    		this.id=id;
    		
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
    	public int getQueryID(int arg0){
    		return Integer.parseInt(id.get(arg0));
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
    

