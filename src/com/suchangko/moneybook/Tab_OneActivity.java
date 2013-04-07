package com.suchangko.moneybook;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
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
 * To change this template use File | Settings | File Templates.
 */
public class Tab_OneActivity extends Activity implements OnClickListener {	
		
	Button bt_datepick;
	Calendar c;
	TextView tv_date;
	private static final int DIALOG_DATE = 0;
	private ArrayList<String> mGroupList = null;
	private ArrayList<ArrayList<String>> mChildList = null;
	private ArrayList<String> mChildListContent = null;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        c=Calendar.getInstance();
        bt_datepick  = (Button)findViewById(R.id.bt_pickdate);
        ExpandableListView mListView = (ExpandableListView)findViewById(R.id.listview1);
        
        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();
 
        mGroupList.add("가위");
        mGroupList.add("바위");
        mGroupList.add("보");
 
        mChildListContent.add("1");
        mChildListContent.add("2");
        mChildListContent.add("3");
 
        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);
        
        mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));
        
        // 그룹 클릭 했을 경우 이벤트
        mListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                Toast.makeText(getApplicationContext(), "g click = " + groupPosition, 
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
         
        // 차일드 클릭 했을 경우 이벤트
        mListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), "c click = " + childPosition, 
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
         
        // 그룹이 닫힐 경우 이벤트
        mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition, 
                        Toast.LENGTH_SHORT).show();
            }
        });
         
        // 그룹이 열릴 경우 이벤트
        mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition, 
                        Toast.LENGTH_SHORT).show();
            }
        });
        
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
	            return new DatePickerDialog(this, dateListener,c.get(Calendar.YEAR),Calendar.MONTH+1,Calendar.DAY_OF_MONTH);	    
	    }
	        return null;
	 }
}