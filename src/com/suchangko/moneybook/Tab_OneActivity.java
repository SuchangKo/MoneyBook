package com.suchangko.moneybook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 3. 27
 * Time: 오후 4:27
 * To change this template use File | Settings | File Templates.
 */
public class Tab_OneActivity extends Activity implements OnClickListener {	
		
	Button bt_datepick;
	Calendar c;
	TextView tv_date;
	private static final int DIALOG_DATE = 0;
	 private DatePickerDialog.OnDateSetListener dateListener = 
		        new DatePickerDialog.OnDateSetListener() {
		         
		        @Override
		        public void onDateSet(DatePicker view, int year, int monthOfYear,
		                int dayOfMonth) {
		        	bt_datepick.setText(year+"년 "+(monthOfYear+1)+"월");
		        }
		    };
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        c=Calendar.getInstance();
        bt_datepick  = (Button)findViewById(R.id.bt_pickdate);
    
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.bt_pickdate){
			showDialog(DIALOG_DATE);	
		}
	}
	 @Override
	    protected Dialog onCreateDialog(int id){
	        switch(id){
	        case DIALOG_DATE:
	            return new DatePickerDialog(this, dateListener,c.get(Calendar.YEAR),Calendar.MONTH+1,Calendar.DAY_OF_MONTH);	    
	    }
	        return null;
	 }
}