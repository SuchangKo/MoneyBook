package com.suchangko.moneybook;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EditCategory extends Activity {
	KinofDB kindDB;
	DetailKindofDB detailKindofDB;
	ListView lv;
	ArrayAdapter<String> arrayAdapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.editcategory);
	    lv = (ListView)findViewById(R.id.listView1);
	    kindDB =new KinofDB(getApplicationContext(), KinofDB.SQL_Create_kindofdb,KinofDB.SQL_DBname);
	    kindDB.open();
	    detailKindofDB = new DetailKindofDB(getApplicationContext(), DetailKindofDB.SQL_Create_detailkindofdb,DetailKindofDB.SQL_DBname);
	    detailKindofDB.open();
	    
	    Cursor c = kindDB.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname);
	    ArrayList<String> arrayList = new ArrayList<String>();
	    if(c.moveToFirst()){
	    	do{
	    		arrayList.add(c.getString(1));
	    	}while(c.moveToNext());
	    }
	    String[] arrStrings = arrayList.toArray(new String[arrayList.size()]);
	   arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row_simple, arrStrings);
	    lv.setAdapter(arrayAdapter);
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String a = arrayAdapter.getItem(arg2).toString();
				Cursor cc = detailKindofDB.RawQueryString("SELECT * FROM "+detailKindofDB.SQL_DBname+" WHERE kindof='"+a+"'");
				ArrayList<String> arrayList = new ArrayList<String>();
			    if(cc.moveToFirst()){
			    	do{
			    		arrayList.add(cc.getString(2));
			    	}while(cc.moveToNext());
			    }
			    String[] arrStrings = arrayList.toArray(new String[arrayList.size()]);
			    ArrayAdapter<String> arrayAdaptera = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row_simple, arrStrings);
			    lv.setAdapter(arrayAdaptera);
				lv.setOnItemClickListener(null);
			}
		});
	}

}
