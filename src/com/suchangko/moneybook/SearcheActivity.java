package com.suchangko.moneybook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChangKo
 * Date: 13. 6. 3
 * Time: 오전 1:38
 * To change this template use File | Settings | File Templates.
 */
public class SearcheActivity extends Activity {
	Intent i;
	int[] cs;
	int[] cf;
	String searchstr;
	String[] kind;
	String card;
	ListView lv;
	ArrayList<String> dateArrayList;
	ArrayList<String> whereArrayList;
	ArrayList<String> priceArrayList;
	MoneyBookDB mdb;
	int searchcnt=0;
	ListViewAdapter1 adapter1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchs);
        i=getIntent();
        cs = i.getIntArrayExtra("cs");
        cf = i.getIntArrayExtra("cf");
        searchstr = i.getStringExtra("str");
        String tmp_kind =i.getStringExtra("kind"); 
        kind = tmp_kind.split("\\+");
        card = i.getStringExtra("card");
        dateArrayList=new ArrayList<String>();
        whereArrayList=new ArrayList<String>();
        priceArrayList=new ArrayList<String>();
       
        
        mdb =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
        mdb.open();
        lv = (ListView)findViewById(R.id.listview1);
        Log.d("cs",cs[0]+"년"+cs[1]+"월"+cs[2]+"일");
        Log.d("cf",cf[0]+"년"+cf[1]+"월"+cf[2]+"일");
        Log.d("str",searchstr);
        Log.d("kind",tmp_kind);
        Log.d("card",card);
     //   Log.d("Error","Error1");
        madeAdapter();
        
        adapter1 = new ListViewAdapter1(getApplicationContext(), R.layout.listrow_search,dateArrayList,whereArrayList,priceArrayList);
        lv.setAdapter(adapter1);
        TextView tv = (TextView)findViewById(R.id.tv_title);
        tv.setText("지출 검색 결과 : "+searchcnt+"개");
    }
    public void madeAdapter(){
		dateArrayList.clear();
		whereArrayList.clear();
		//Log.d("Error","Error2");
		if(cs[0]==0){
			
		}else{
		//	Log.d("Error","Error3");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.set(cs[0],cs[1]-1,cs[2]);
			c2.set(cf[0],cf[1]-1,cf[2]+1);
		//	Log.d("Error","Error4");
			while(!c1.getTime().equals(c2.getTime())){
				int a = c1.get(Calendar.DAY_OF_MONTH);
	        	int year_ = c1.get(Calendar.YEAR);
	        	int month_ = c1.get(Calendar.MONTH)+1;
	        	//Log.d("Error","Error5");
	        	
	          	Date tmp_date = new Date(year_-1900, month_-1, a);
	          	
	          	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        	String str_tmp_date = formatter.format(tmp_date);
	        	//Log.d("Error","Error6");
	        	
	        	//mGroupList.add(year_+"."+month_+"."+(a-i));
	        
	        	//mChildListContent.clear();
	        	String[] columns={"content","memo","money","kindof","date","minutetime","moneykindof"};
	        	String selection="date=? AND content LIKE ? ";
	        	ArrayList<String> strings = new ArrayList<String>();
	        	strings.add(String.valueOf(tmp_date.getTime()));
	        	strings.add(searchstr);
	        	//Log.d("Error","Error7");
	        	if(card.equals("전체")){
	        		
	        	}else {
	        		if(card.equals("현금")){
		        		strings.add("현금%");
		        	}else if(card.equals("카드")){
		        		strings.add("카드%");
		        	}
		        	
	        		selection="date=? AND content LIKE ? AND moneykindof LIKE ?";
	        	}
	        	
	        	//Log.d("Error","Error8");
	        	
	        	
	        	if(kind[0].equals("전체")){
	        		//detailString="";
	        	}else{
	        		selection+=" AND kindof LIKE ?";
	        		if(kind[1].equals("전체")){
	        			strings.add(kind[0]+"%");
	        		}else{
	        			strings.add(kind[0]+"+"+kind[1]);
	        		}
	        		
	        	}
	        	String[] selectionArgs=(String[])strings.toArray(new String[strings.size()]);
	        	
	        	//Log.d("Error","Error9");
	        	//Log.d("selection",selection);
	        	//Log.d("args",strings.toString());
	        	Cursor c = mdb.selectTable(columns, selection, selectionArgs, null,null,"minutetime ASC");
	        	//Log.d("Error","Error10"); 	
	        	int cnt = c.getCount();
	        	Log.d("",""+cnt);
	        	if(cnt==0){
	        		
	        	}else{
	        		if(c.moveToFirst()){
	        			do{
	        				dateArrayList.add(str_tmp_date);
		        			whereArrayList.add(c.getString(0));
		        			priceArrayList.add(c.getString(2));
		        			searchcnt++;	    	  
			        	}while(c.moveToNext());
	        			
	        		}
	        		
	        	}
	        	//Log.d("Error","Error11");
				c1.set(Calendar.DAY_OF_MONTH,c1.get(Calendar.DAY_OF_MONTH)+1);
			}
			
		}
    }
		 /*
	        LastDay = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        int i=0;
	        while(i<LastDay){
	        	int a = LastDay;
	        	int year_ = c.get(Calendar.YEAR);
	        	int month_ = c.get(Calendar.MONTH)+1;
	        	
	          	Date tmp_date = new Date(year_-1900, month_-1, a-i);
	        	
	        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        	String str_tmp_date = formatter.format(tmp_date);
	        	
	        	
	        	//mGroupList.add(year_+"."+month_+"."+(a-i));
	        
	        	//mChildListContent.clear();
	        	String[] columns={"content","memo","money","kindof","date","minutetime","moneykindof"};
	        	String selection="date=?";
	        	if(kindof==0){
	        		
	        	}else {
	        		selection="date=? AND moneykindof LIKE ?";
	        	}
	        	
	        	ArrayList<String> strings = new ArrayList<String>();
	        	strings.add(String.valueOf(tmp_date.getTime()));
	        	
	        	if(kindof==1){
	        		strings.add("현금%");
	        	}else if(kindof==2){
	        		strings.add("카드%");
	        	}
	        	
	        	if(middleString.equals("전체")){
	        		detailString="";
	        	}else{
	        		selection+=" AND kindof LIKE ?";
	        		if(detailString.equals("")){
	        			strings.add(middleString+"%");
	        		}else{
	        			strings.add(middleString+"+"+detailString);
	        		}
	        		
	        	}
	        	String[] selectionArgs=(String[])strings.toArray(new String[strings.size()]);
	        	
	        	//Log.d("Timestamp",String.valueOf(tmp_date.getTime()));
	        	Cursor c = mdb.selectTable(columns, selection, selectionArgs, null,null,"minutetime ASC");
	        	//        	c.getCount();
	        	//Log.d("count",""+c.getCount());
	        	//c.moveToFirst();
	        	ArrayList<String> tmp_Content = new ArrayList<String>();
	        	
	        	
	        	int tmp_count=c.getCount();
	        	Log.d("count",""+tmp_count);
	        	if(tmp_count>0){
	        		int tmp_money = 0;
	        		
	        		if(c.moveToFirst()){
	        			do{
	        				tmp_money += Integer.parseInt(c.getString(2));
	        				String tmp_content_String = c.getString(5)+"#"+c.getString(6)+"#"+c.getString(1)+"#"+c.getString(2)+"원"; 
	    	        		tmp_Content.add(tmp_content_String);	    	  
			        	}while(c.moveToNext());
	        		}
	        		str_tmp_date+="#"+tmp_money+"원";
	        		tmp_moneyint+=tmp_money;
	        	}else{
	        		//Log.d("값 있음","");
	        		//c.moveToNext();
	        		str_tmp_date+="#0원";
	        		//tmp_Content.add("값없음#값없음#값없음#값없음");	        		//mChildListContent.add(c.getString(0));
	        		
	        	}
	        	mGroupList.add(str_tmp_date);
	        	baseadapter = new BaseExpandableAdapter(this, mGroupList, mChildList);
	        	
	        	mChildList.add(tmp_Content);
	        	i++;
	        }

        	bt_money.setText(""+tmp_moneyint+"원");
        	
        	 //mListView.setAdapter(baseadapter);
	}
    */
    class ListViewAdapter1 extends BaseAdapter{
    	private LayoutInflater Inflater;
    	private int _layout;
    	private ArrayList<String> a;
    	private ArrayList<String> b;
    	private ArrayList<String> d;
    	public ListViewAdapter1(Context c, int layout,ArrayList<String> a,ArrayList<String> b,ArrayList<String> d){
    		Inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		_layout=layout;
    		this.a=a;
    		this.b=b;
    		this.d=d;
    		
    	}
    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		return a.size();
    		//return 19;
    	}

    	@Override
    	public Object getItem(int arg0) {
    		// TODO Auto-generated method stub
    		return null;
    	}

    	@Override
    	public long getItemId(int arg0) {
    		// TODO Auto-generated method stub
    		return 0;
    	}

    	@Override
    	public View getView(int arg0, View arg1, ViewGroup arg2) {
    		if(arg1==null){
    			arg1=Inflater.inflate(_layout, arg2,false);
    		}
    		TextView tv1 = (TextView)arg1.findViewById(R.id.searchrow_date);
    		TextView tv2 = (TextView)arg1.findViewById(R.id.searchrow_price);
    		TextView tv3 = (TextView)arg1.findViewById(R.id.searchrow_where);
    		
    		tv1.setText(a.get(arg0));
    		tv2.setText(b.get(arg0));
    		tv3.setText(d.get(arg0));
    		
    		return arg1;
    	}
    	
    }
 }
    

