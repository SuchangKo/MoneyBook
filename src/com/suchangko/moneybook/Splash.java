package com.suchangko.moneybook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Splash extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_splash);
	    init();
	    // TODO Auto-generated method stub
	}
	private void init(){
		Handler h = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Intent i = new Intent(getApplicationContext(),LoginActivity.class);
					finish();
					startActivity(i);
			}
		};
		h.sendEmptyMessageDelayed(0,2300);
	}
}
