package com.suchangko.moneybook;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 4. 3
 * Time: 오후 8:54
 * To change this template use File | Settings | File Templates.
 */
public class Tab_TwoActivity extends Activity {
	ListView lv;
	MoneyInputDB inputDB;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        lv = (ListView)findViewById(R.id.listview1);
        inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
        inputDB.open();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	menu.add(0,0,0,"수입입력").setIcon(R.drawable.ic_menu_add);
	menu.add(0,1,0,"이전문자등록").setIcon(R.drawable.ic_menu_copy);
	menu.add(0,2,0,"즐겨찾기편집").setIcon(R.drawable.btn_star_off_disabled_holo_light);
	menu.add(1,3,0,"수입내역검색").setIcon(R.drawable.ic_menu_search);
	menu.add(1,4,0,"회사소개").setIcon(R.drawable.ic_menu_notifications);
	menu.add(1,5,0,"더보기").setIcon(R.drawable.ic_menu_more);	
	return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int itemid =item.getItemId();
		switch (itemid) {
		case 0:
			Toast.makeText(this,"수입입력",100).show();
			//AlertDialog a = dialog_add_spend();
			//a.show();
			break;
		case 1:
			Toast.makeText(this,"이전문자등록",100).show();
			break;
		case 2:
			Toast.makeText(this,"즐겨찾기편집",100).show();
			break;
		case 3:
			Toast.makeText(this,"지출내역검색",100).show();
			break;
		case 4:
			Toast.makeText(this,"회사소개",100).show();
			break;
		case 5:
			Toast.makeText(this,"더보기",100).show();
			break;
				}
		return super.onOptionsItemSelected(item);
	}
}