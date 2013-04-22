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
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 3. 27
 * Time: 오후 4:27
 * To change this template use File | Settings | File Template
 */
public class Tab_OneActivity extends Activity implements OnClickListener {	
		
	Button bt_datepick;
	Calendar c;
	TextView tv_date;
	private static final int DIALOG_DATE = 0;
	private ArrayList<String> mGroupList = null;
	private ArrayList<ArrayList<String>> mChildList = null;
	private ArrayList<String> mChildListContent = null;
	private ArrayList<String> mChildListContent1 = null;
	MoneyBookDB mdb;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        c=Calendar.getInstance();
        bt_datepick  = (Button)findViewById(R.id.bt_pickdate);
        bt_datepick.setOnClickListener(this);
        ExpandableListView mListView = (ExpandableListView)findViewById(R.id.listview1);
        
        mdb =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
        mdb.open();
        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();
        mChildListContent1 = new ArrayList<String>();
        /*
         * http://www.androidpub.com/465319
         *  input : new java.util.Date().getTime()
         *  read :  
         *  1. 
         *  long datetime =  cursor.getLong(rowIndex);
         *  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( datetime); 
         * 2.
         * long _timeMillis = System.currentTimeMillis();
         * DateFormat.format("yyyy-MM-dd HH:mm:ss", _timeMillis).toString());
         * 3.
         * TimeZone timezone = TimeZone.getTimeZone("Etc/GMT-9");
         * TimeZone.setDefault(timezone);
         * */
        
        //ListItem
        mChildListContent.add("1");
        mChildListContent.add("2");
        mChildListContent.add("3");
      
        GregorianCalendar grecal = new GregorianCalendar();
        int LastDay = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int i=0;
        while(i<LastDay){
        	int a = LastDay;
        	int year_ = c.get(Calendar.YEAR);
        	int month_ = c.get(Calendar.MONTH)+1;
        	mGroupList.add(year_+"."+month_+"."+(a-i));
        	mChildList.add(mChildListContent);
        	i++;
        }
        mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));
        
        // 그룹 클릭 했을 경우 이벤트
        mListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
               // Toast.makeText(getApplicationContext(), "g click = " + groupPosition, 
                 //       Toast.LENGTH_SHORT).show();
                return false;
            }
        });
         
        // 차일드 클릭 했을 경우 이벤트
        mListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
              //  Toast.makeText(getApplicationContext(), "c click = " + childPosition, 
                //        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
         
        // 그룹이 닫힐 경우 이벤트
        mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition, 
                  //      Toast.LENGTH_SHORT).show();
            }
        });
         
        // 그룹이 열릴 경우 이벤트
        mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
              //  Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition, 
                    //    Toast.LENGTH_SHORT).show();
            }
        });
        
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	menu.add(0,0,0,"지출입력").setIcon(R.drawable.ic_menu_add);
	menu.add(0,1,0,"이전문자등록").setIcon(R.drawable.ic_menu_copy);
	menu.add(0,2,0,"즐겨찾기편집").setIcon(R.drawable.btn_star_off_disabled_holo_light);
	menu.add(1,3,0,"지출내역검색").setIcon(R.drawable.ic_menu_search);
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
			//Toast.makeText(this,"지출입력",100).show();
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.bt_pickdate){
			showDialog(DIALOG_DATE);	
		}
	}
	 private DatePickerDialog.OnDateSetListener dateListener = 
		        new DatePickerDialog.OnDateSetListener() {
		         
		        @Override
		        public void onDateSet(DatePicker view, int year, int monthOfYear,
		                int dayOfMonth) {
		        	bt_datepick.setText(year+"년 "+(monthOfYear+1)+"월");
		        }
		    };
	
	 @Override
	    protected Dialog onCreateDialog(int id){
	        switch(id){
	        case DIALOG_DATE:
	            return new DatePickerDialog(this, dateListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));	    
	    }
	        return null;
	 }
	 
	 private AlertDialog dialog_add_spend() {
	        final View innerView = getLayoutInflater().inflate(R.layout.dialog_add_spend, null);
	        AlertDialog.Builder ab = new AlertDialog.Builder(this);
	        ab.setTitle("지출내역");
	        ab.setView(innerView);
	        EditText edt_date = (EditText)innerView.findViewById(R.id.dialog_edit_date);
	        EditText edt_time = (EditText)innerView.findViewById(R.id.dialog_edit_time);
	        EditText edt_money =(EditText)innerView.findViewById(R.id.dialog_edit_money);
	        EditText edt_content = (EditText)innerView.findViewById(R.id.dialog_edit_content);
	        EditText edt_memo = (EditText)innerView.findViewById(R.id.dialog_edit_memo);
	        final EditText edt_middle = (EditText)innerView.findViewById(R.id.dialog_edit_middle);
	        final EditText edt_detail =  (EditText)innerView.findViewById(R.id.dialog_edit_detail);
	        EditText edt_spendhow = (EditText)innerView.findViewById(R.id.dialog_edit_spendhow);
	        EditText edt_spend_detail = (EditText)innerView.findViewById(R.id.dialog_edit_spend_detail);
	        
	        edt_middle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
						

					AlertDialog.Builder builder = new AlertDialog.Builder(Tab_OneActivity.this);
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
					AlertDialog.Builder builder = new AlertDialog.Builder(Tab_OneActivity.this);
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
	        
	        
	        SimpleDateFormat formatter1 = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
	        Date currentDate = new Date ( );
	        String dDate = formatter1.format ( currentDate );
	        SimpleDateFormat formatter2 = new SimpleDateFormat ( "HH:mm", Locale.KOREA );
	        Date currentTime = new Date ( );
	        String dTime = formatter2.format ( currentTime );
	        
	        edt_date.setText(dDate);
	        edt_time.setText(dTime);
	        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface arg0, int arg1) {
	               // setDismiss(mDialog);
	            	
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
}