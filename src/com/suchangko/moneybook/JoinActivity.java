package com.suchangko.moneybook;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends Activity implements OnClickListener {
	EditText edt_id;
	EditText edt_pw;
	EditText edt_pw1;
	Button bt_join;
	AccountDB acdb;
	Cursor c;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_join);
	    acdb = new AccountDB(this,AccountDB.SQL_Create_accountdb,"accountdb");
	    acdb.open();
	    edt_id = (EditText)findViewById(R.id.edit_id);
	    edt_pw = (EditText)findViewById(R.id.edit_password);
	    edt_pw1 = (EditText)findViewById(R.id.edit_password1);
	    bt_join = (Button)findViewById(R.id.button_join);
	    bt_join.setOnClickListener(this);
	    
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button_join){
			String id = edt_id.getText().toString();
			String pw = edt_pw.getText().toString();
			String pw1 = edt_pw1.getText().toString();
			if(pw.equals(pw1)){
				String[] columns={"id","pw"};
				String[] parm={id};
			    c=acdb.selectTable(columns,"id=?",parm,null,null,null);
			    int count = c.getCount();				
				if(count==0){
					ContentValues values = new ContentValues();
					values.put("id",id);
					values.put("pw",pw);			
					acdb.insertTable(values);
					acdb.close();
					Toast.makeText(this,"ID: "+id+" PW: "+pw,Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(this,"이미 등록되어있는 ID입니다.\n ID입력을 확인해 주세요.",Toast.LENGTH_LONG).show();
				}
				
			}else{
				Toast.makeText(getApplicationContext(), "비밀번호를 제대로 입력해주세요.",Toast.LENGTH_SHORT).show();
			}
		}
	}

}
