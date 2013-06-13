package com.suchangko.moneybook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {
	Button bt_join;
    Button bt_login;
    EditText edit_id;
    EditText edit_password;
    AccountDB acdb;
    Cursor c;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	    bt_join=(Button)findViewById(R.id.button_join);
        bt_login=(Button)findViewById(R.id.button_login);
        bt_join.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        edit_id = (EditText)findViewById(R.id.edit_id);
        edit_password = (EditText)findViewById(R.id.edit_password);
        acdb=new AccountDB(this,AccountDB.SQL_Create_accountdb,"accountdb");
        acdb.open();
        StartMain(); // Developer.
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button_login){
        	String[] columns={"id","pw"};
        	String str_edtid = edit_id.getText().toString();
        	String str_edtpw = edit_password.getText().toString();
        	Log.d("search_id",str_edtid);
        	String[] selectionArgs = {str_edtid,str_edtpw};
        	c = acdb.selectTable(columns,"id=? AND pw=?",selectionArgs,null,null,null);
        	int count =c.getCount();
        	Log.d("count",""+count);
        	
        	if(count==0){
        		Toast.makeText(this,"로그인 실패. \n ID와 PW를 확인해주세요.",Toast.LENGTH_SHORT).show();
        	}else{
        		StartMain();
        	}
        }else if(view.getId()==R.id.button_join){
            Intent i = new Intent(this,JoinActivity.class);
            startActivity(i);
        }
    }
    
    public void StartMain(){
    	Intent i = new Intent(this,MainActivity.class);
        finish();
        startActivity(i);
    }

}
