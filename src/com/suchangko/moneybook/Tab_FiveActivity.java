package com.suchangko.moneybook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 4. 3
 * Time: 오후 8:55
 * To change this template use File | Settings | File Templates.
 */
public class Tab_FiveActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
    	menu.add(0,0,0,"예산 추가").setIcon(R.drawable.ic_menu_add);
    	menu.add(0,1,0,"예산 복사").setIcon(android.R.drawable.ic_menu_share);
    	menu.add(0,2,0,"예산 붙여넣기").setIcon(android.R.drawable.ic_menu_set_as);
    	menu.add(1,3,0,"상위 예산 분배").setIcon(android.R.drawable.ic_menu_search);
    	menu.add(1,4,0,"도움말").setIcon(android.R.drawable.ic_menu_help);
    	menu.add(1,5,0,"환경설정").setIcon(R.drawable.ic_menu_more);	
	return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int itemid =item.getItemId();
		switch (itemid) {
		case 0:
			Toast.makeText(this,"수입입력",100).show();
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