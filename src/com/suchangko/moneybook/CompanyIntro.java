package com.suchangko.moneybook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CompanyIntro extends Activity implements OnClickListener {
	Button bt1;
	Button bt2;
	Button bt3;
	Button bt4;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_introcompany);
	    bt1 = (Button)findViewById(R.id.Button01);
	    bt2 = (Button)findViewById(R.id.button1);
	    bt3 = (Button)findViewById(R.id.Button02);
	    bt4 = (Button)findViewById(R.id.Button03);
	    bt1.setOnClickListener(this);
	    bt2.setOnClickListener(this);
	    bt3.setOnClickListener(this);
	    bt4.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.Button01){
			intentact(Intro1.class);
		}else if(v.getId()==R.id.button1){
			intentact(Intro2.class);
		}else if(v.getId()==R.id.Button02){
			intentact(Intro3.class);
		}else if(v.getId()==R.id.Button03){
			intentact(Intro4.class);
		}
		
	}
	
	void intentact(Class<?> c){
		Intent i = new Intent(this,c);
		startActivity(i);
	}

}
