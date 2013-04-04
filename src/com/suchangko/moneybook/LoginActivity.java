package com.suchangko.moneybook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {
	Button bt_join;
    Button bt_login;
    EditText edit_id;
    EditText edit_password;
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
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button_login){
            String str_id = edit_id.getText().toString();
            String str_pw = edit_password.getText().toString();
            Intent i = new Intent(this,MainActivity.class);
            finish();
            startActivity(i);
        }else if(view.getId()==R.id.button_join){
            Intent i = new Intent(this,JoinActivity.class);
            startActivity(i);
        }
    }

}
