package com.suchangko.moneybook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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
    KinofDB kindofdb;
    DetailKindofDB detaildb;
    boolean ok=false;
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
        kindofdb = new KinofDB(this,KinofDB.SQL_Create_kindofdb,KinofDB.SQL_DBname);
        kindofdb.open();
        detaildb = new DetailKindofDB(this,DetailKindofDB.SQL_Create_detailkindofdb,DetailKindofDB.SQL_DBname);
        detaildb.open();
        acdb=new AccountDB(this,AccountDB.SQL_Create_accountdb,"accountdb");
        acdb.open();
        new DBcheckTask().execute();
        /*
        if(ok){
			StartMain();
		}else{
			Toast.makeText(getApplicationContext(), "DB생성중입니다. 잠시후에 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
		}
		*/
        //StartMain(); // Developer.
    }
	void AddDBs(){
		Cursor a1 = kindofdb.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname);
		Cursor a2 = detaildb.RawQueryString("SELECT * FROM "+DetailKindofDB.SQL_DBname);
		Log.d("Hello","Kindof"+a1.getCount());
		Log.d("Hello","Detail"+a2.getCount());
		if(a1.getCount()==0){
			adddb1("식비");
			adddb1("교통비");
			adddb1("교육비");
			adddb1("건강,의료비");
			adddb1("통신비");
			adddb1("가구집기");
			adddb1("주거비");
			adddb1("품위유지비");
			adddb1("교양,오락비");
			adddb1("보험,저축");
			adddb1("사업운영비");
			adddb1("수수료,세금");
			adddb1("기타");
		}
		if(a2.getCount()==0){
			adddb2("식비","외식비");
			adddb2("식비","간식비");
			adddb2("식비","급식비");
			adddb2("식비","주부식비");
			adddb2("식비","주류비");
			adddb2("교통비","교통비");
			adddb2("교통비","주유비");
			adddb2("교통비","유지비");
			adddb2("교통비","통행료");
			adddb2("교통비","주차비");
			adddb2("교통비","자동차세");
			adddb2("교통비","자동차보험");
			adddb2("교통비","세차비");
			adddb2("교육비","학교");
			adddb2("교육비","학원");
			adddb2("교육비","교재비");
			adddb2("교육비","학용품");
			adddb2("교육비","체험용품");
			adddb2("건강,의료비","건강식품");
			adddb2("건강,의료비","의약품");
			adddb2("건강,의료비","진료비");
			adddb2("건강,의료비","헬스");
			adddb2("통신비","핸드폰");
			adddb2("통신비","인터넷전화");
			adddb2("통신비","우편요금");
			adddb2("가구집기","생활용품");
			adddb2("가구집기","가전제품");
			adddb2("가구집기","주방용품");
			adddb2("가구집기","생필품");
			adddb2("주거비","상하수도");
			adddb2("주거비","난방비");
			adddb2("주거비","전기요금");
			adddb2("주거비","가스요금");
			adddb2("주거비","일반관리비");
			adddb2("주거비","임대료");
			adddb2("주거비","렌탈비");
			adddb2("품위유지비","뷰티,미용비");
			adddb2("품위유지비","의류비");
			adddb2("품위유지비","세탁비");
			adddb2("품위유지비","액세서리");
			adddb2("품위유지비","목욕비");
			adddb2("교양,오락비","신문구독료");
			adddb2("교양,오락비","여행");
			adddb2("교양,오락비","도서");
			adddb2("교양,오락비","영화");
			adddb2("교양,오락비","대여비");
			adddb2("교양,오락비","공연");
			adddb2("보험,저축","국민연금");
			adddb2("보험,저축","건강보험");
			adddb2("보험,저축","종신보험");
			adddb2("보험,저축","기타보험");
			adddb2("보험,저축","저축");
			adddb2("사업운영비","본사입금");
			adddb2("사업운영비","문구류");
			adddb2("사업운영비","교육비");
			adddb2("사업운영비","상금");
			adddb2("사업운영비","컨텐츠");
			adddb2("수수료,세금","카드수수료");
			adddb2("수수료,세금","이체수수료");
			adddb2("수수료,세금","세금");
			adddb2("수수료,세금","기타수수료");				
			adddb2("기타","기부금");
			adddb2("기타","용돈");
			adddb2("기타","접대비");
			adddb2("기타","회비");
			adddb2("기타","축의금");
			adddb2("기타","부의금");
		}
	}
	private class DBcheckTask extends AsyncTask<Void,Void,Void>{
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Cursor c1 = kindofdb.RawQueryString("SELECT * FROM "+KinofDB.SQL_DBname);
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<String> list1 = new ArrayList<String>();
			ArrayList<String> list2 = new ArrayList<String>();
			list1.add("전체");
			if(c1.moveToFirst()){
				do{
					String item = c1.getString(1);					
					list.add(item);
					list1.add(item);
					list2.add(item);
					Cursor c2 = detaildb.RawQueryString("SELECT * FROM "+DetailKindofDB.SQL_DBname+" WHERE kindof='"+item+"'");
					ArrayList<String> arrayList = new ArrayList<String>();
					if(c2.moveToFirst()){
						do{
							arrayList.add(c2.getString(2));
						}while(c2.moveToNext());
					}
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
					
				}while(c1.moveToNext());
			}
		
			util.Middleitems = list.toArray(new CharSequence[list.size()]);
			util.Middleitems1 = list1.toArray(new CharSequence[list1.size()]);
			util.allspend = list2.toArray(new CharSequence[list2.size()]);
			ok=true;
			StartMain();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
						AddDBs();
			return null;
		}		
	}
	void adddb1(String value){
		ContentValues val = new ContentValues();
		val.put("kindof",value);
		kindofdb.insertTable(val);
	}
	void adddb2(String value1,String value2){
		ContentValues val = new ContentValues();
		val.put("kindof",value1);
		val.put("detail",value2);
		detaildb.insertTable(val);
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
        		if(ok){
        			StartMain();
        		}else{
        			Toast.makeText(getApplicationContext(), "DB생성중입니다. 잠시후에 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
        		}
        		
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
