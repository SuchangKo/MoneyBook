package com.suchangko.moneybook;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditCategory extends Activity {
	KinofDB kindDB;
	DetailKindofDB detailKindofDB;
	ListView lv;
	EditText edt;
	Button bt;
	int arraysize=0;
	ArrayAdapter<String> arrayAdapter;
	boolean detail=false;
	String[] arrStrings;
	String kindString="";
	TextView tv_status;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.editcategory);
	    lv = (ListView)findViewById(R.id.listView1);
	    bt = (Button)findViewById(R.id.button1);
	    edt = (EditText)findViewById(R.id.editText1);
	    bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String item = edt.getText().toString();
				if(detail){
					ContentValues val = new ContentValues();
					val.put("kindof",kindString);
					val.put("detail",item);
					detailKindofDB.insertTable(val);
					refreshdetail();
				}else{
					ContentValues val = new ContentValues();
					val.put("kindof",item);
					kindDB.insertTable(val);
					refreshkindof();
				}
				Toast.makeText(getApplicationContext(), "추가완료",Toast.LENGTH_LONG).show();
				edt.setText("");
			}
		});
	    
	    tv_status = (TextView)findViewById(R.id.tv_status);
	    kindDB =new KinofDB(getApplicationContext(), KinofDB.SQL_Create_kindofdb,KinofDB.SQL_DBname);
	    kindDB.open();
	    detailKindofDB = new DetailKindofDB(getApplicationContext(), DetailKindofDB.SQL_Create_detailkindofdb,DetailKindofDB.SQL_DBname);
	    detailKindofDB.open();
	    makeArrayAdapter();
	    //Toast.makeText(getApplicationContext(), "Size : "+arraysize,Toast.LENGTH_LONG).show();
	}
	
	
	void makeArrayAdapter(){
		Cursor c = kindDB.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname);
	    ArrayList<String> arrayList = new ArrayList<String>();
	    if(c.moveToFirst()){
	    	do{
	    		arrayList.add(c.getString(1));
	    	}while(c.moveToNext());
	    }
	    arraysize = arrayList.size();
	    
	   arrStrings = arrayList.toArray(new String[arrayList.size()]);
	   arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row_simple, arrStrings);
	    lv.setAdapter(arrayAdapter);
	    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				
		      	AlertDialog.Builder builder = new AlertDialog.Builder(EditCategory.this);
				builder.setTitle("선택");
					String[] items1={"위로","아래로","수정","삭제"};
					builder.setItems(items1, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	if(item==0){
					    		if(arg2==0){
					    			Toast.makeText(getApplicationContext(),"가장 처음 입니다.",1000).show();
					    		}else{
					    			if(detail){
					    				String tmp1 = arrStrings[arg2];
					    				String tmp2 = arrStrings[arg2-1];
					    				Cursor tc_1 = detailKindofDB.RawQueryString("SELECT * FROM "+DetailKindofDB.SQL_DBname+" WHERE detail LIKE '%"+tmp1+"%'");
					    				tc_1.moveToNext();
					    				Cursor tc_2 = detailKindofDB.RawQueryString("SELECT * FROM "+DetailKindofDB.SQL_DBname+" WHERE detail LIKE '%"+tmp2+"%'");
					    				tc_2.moveToNext();
					    				ContentValues v1 = new ContentValues();
					    				v1.put("detail",tmp1);
					    				ContentValues v2 = new ContentValues();
					    				v2.put("detail",tmp2);
					    				detailKindofDB.update(v1,"_id="+tc_2.getString(0));
					    				detailKindofDB.update(v2,"_id="+tc_1.getString(0));
					    				refreshdetail();
					    			}else{
					    				String tmp1 = arrStrings[arg2];
					    				String tmp2 = arrStrings[arg2-1];
					    				Cursor tc_1 = kindDB.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname+" WHERE kindof LIKE '%"+tmp1+"%'");
					    				tc_1.moveToNext();
					    				Cursor tc_2 = kindDB.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname+" WHERE kindof LIKE '%"+tmp2+"%'");
					    				tc_2.moveToNext();
					    				Log.d("dddd","dd:"+tc_1.getCount()+"dd:"+tc_2.getCount());
					    				ContentValues v1 = new ContentValues();
					    				v1.put("kindof",tmp1);
					    				ContentValues v2 = new ContentValues();
					    				v2.put("kindof",tmp2);
					    				kindDB.update(v1,"_id="+tc_2.getString(0));
					    				kindDB.update(v2,"_id="+tc_1.getString(0));
					    				refreshkindof();
					    			}					    			
					    		}					    		
					    	}else if(item==1){
					    		if(arg2==(arraysize-1)){
					    			Toast.makeText(getApplicationContext(),"가장 끝 입니다.",1000).show();
					    		}else{
					    			if(detail){
					    				String tmp1 = arrStrings[arg2];
					    				String tmp2 = arrStrings[arg2+1];
					    				Cursor tc_1 = detailKindofDB.RawQueryString("SELECT * FROM "+DetailKindofDB.SQL_DBname+" WHERE detail LIKE '%"+tmp1+"%'");
					    				tc_1.moveToNext();
					    				Cursor tc_2 = detailKindofDB.RawQueryString("SELECT * FROM "+DetailKindofDB.SQL_DBname+" WHERE detail LIKE '%"+tmp2+"%'");
					    				tc_2.moveToNext();
					    				ContentValues v1 = new ContentValues();
					    				v1.put("detail",tmp1);
					    				ContentValues v2 = new ContentValues();
					    				v2.put("detail",tmp2);
					    				detailKindofDB.update(v1,"_id="+tc_2.getString(0));
					    				detailKindofDB.update(v2,"_id="+tc_1.getString(0));
					    				refreshdetail();
					    			}else{
					    				String tmp1 = arrStrings[arg2];
					    				String tmp2 = arrStrings[arg2+1];
					    				Cursor tc_1 = kindDB.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname+" WHERE kindof LIKE '%"+tmp1+"%'");
					    				tc_1.moveToNext();
					    				Cursor tc_2 = kindDB.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname+" WHERE kindof LIKE '%"+tmp2+"%'");
					    				tc_2.moveToNext();
					    				Log.d("dddd","dd:"+tc_1.getCount()+"dd:"+tc_2.getCount());
					    				ContentValues v1 = new ContentValues();
					    				v1.put("kindof",tmp1);
					    				ContentValues v2 = new ContentValues();
					    				v2.put("kindof",tmp2);
					    				kindDB.update(v1,"_id="+tc_2.getString(0));
					    				kindDB.update(v2,"_id="+tc_1.getString(0));
					    				refreshkindof();
					    			}
					    		}
					    	}else if(item==2){					    		
				    				AlertDialog dialog1 = dialog_edit(arrStrings[arg2]);
				    				dialog1.show();
					    	}else if(item==3){
					    		if(detail){
				    				detailKindofDB.datadel1(arrStrings[arg2]);
				    				refreshdetail();
				    			}else{
				    				kindDB.datadel1(arrStrings[arg2]);
				    				refreshkindof();
				    			}
					    	}
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();   
				return false;
				}	    	
		});
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				kindString = arrayAdapter.getItem(arg2).toString();
				tv_status.setText("(현재 카테고리 : "+kindString+"\\)");
				Cursor cc = detailKindofDB.RawQueryString("SELECT * FROM "+detailKindofDB.SQL_DBname+" WHERE kindof='"+kindString+"'");
				ArrayList<String> arrayList = new ArrayList<String>();
			    if(cc.moveToFirst()){
			    	do{
			    		arrayList.add(cc.getString(2));
			    	}while(cc.moveToNext());
			    }
			    arraysize = arrayList.size();
			    arrStrings = arrayList.toArray(new String[arrayList.size()]);
			    ArrayAdapter<String> arrayAdaptera = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row_simple, arrStrings);
			    lv.setAdapter(null);
			    lv.setAdapter(arrayAdaptera);
				lv.setOnItemClickListener(null);
				detail=true;
				//Toast.makeText(getApplicationContext(), "Size : "+arraysize,Toast.LENGTH_LONG).show();
			}
		});
	}
	private AlertDialog dialog_edit(final String item){
		 final View innerView = getLayoutInflater().inflate(R.layout.dialog_editcategory, null);
		 final AlertDialog.Builder ab = new AlertDialog.Builder(this);
	        ab.setTitle("목록 편집");	
	        final EditText edt_ = (EditText)innerView.findViewById(R.id.edit_category);
	       
	        ab.setView(innerView);
	        ab.setPositiveButton("편집",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(detail){
						ContentValues val = new ContentValues();
						val.put("detail",edt_.getText().toString());
						detailKindofDB.update(val,"detail='"+item+"'");
						refreshdetail();
					}else{
						ContentValues val = new ContentValues();
						val.put("kindof",edt_.getText().toString());
						kindDB.update(val,"kindof='"+item+"'");
						refreshkindof();
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
	
	void refreshkindof(){
		ArrayList<String> l1 = new ArrayList<String>();
		ArrayList<String> l2 = new ArrayList<String>();
		ArrayList<String> l3 = new ArrayList<String>();
		l1.add("전체");
		l2.add("전 체");
		Cursor c = kindDB.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname);
	    ArrayList<String> arrayList = new ArrayList<String>();
	    if(c.moveToFirst()){
	    	do{
	    		String item = c.getString(1);
	    		l1.add(item);
	    		l2.add(item);
	    		l3.add(item);
	    		arrayList.add(c.getString(1));
	    	}while(c.moveToNext());
	    }
	    arraysize = arrayList.size();
	    
	   arrStrings = arrayList.toArray(new String[arrayList.size()]);
	   arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row_simple, arrStrings);
	   lv.setAdapter(null);	   
	   lv.setAdapter(arrayAdapter);
	   util.Middleitems = l1.toArray(new CharSequence[l1.size()]);
	   util.Middleitems1 = l2.toArray(new CharSequence[l2.size()]);
	   util.allspend = l3.toArray(new CharSequence[l3.size()]);	   
	}
	void refreshdetail(){
		Cursor cc = detailKindofDB.RawQueryString("SELECT * FROM "+detailKindofDB.SQL_DBname+" WHERE kindof='"+kindString+"'");
		ArrayList<String> arrayList = new ArrayList<String>();
	    if(cc.moveToFirst()){
	    	do{
	    		arrayList.add(cc.getString(2));
	    	}while(cc.moveToNext());
	    }
	    arraysize = arrayList.size();
	    arrStrings = arrayList.toArray(new String[arrayList.size()]);
	    ArrayAdapter<String> arrayAdaptera = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row_simple, arrStrings);
	    lv.setAdapter(arrayAdaptera);
		lv.setOnItemClickListener(null);
		String item = kindString;
		if(item.equals("식비")){
			util.detailitems1 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("교통비")){
			util.detailitems2 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("교육비")){
			util.detailitems3 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("건강,의료비")){
			util.detailitems4 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("통신비")){
			util.detailitems5 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("가구집기")){
			util.detailitems6 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("주거비")){
			util.detailitems7 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("품위유지비")){
			util.detailitems8 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("교양,오락비")){
			util.detailitems9 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("보험,저축")){
			util.detailitems10 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("사업운영비")){
			util.detailitems11 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("수수료,세금")){
			util.detailitems12= arrayList.toArray(new CharSequence[arrayList.size()]);
		}else if(item.equals("기타")){
			util.detailitems13 = arrayList.toArray(new CharSequence[arrayList.size()]);
		}
	}
	@Override
	public void onBackPressed() {
		if(detail){
			tv_status.setText("(현재 카테고리 : \\)");
			detail=false;
			makeArrayAdapter();
		}else{
			finish();
		}
	}

	
}
