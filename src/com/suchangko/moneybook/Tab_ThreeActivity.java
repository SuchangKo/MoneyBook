package com.suchangko.moneybook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * 
 * Date: 13. 4. 3
 * Time: 오후 8:55
 * To change this template use File | Settings | File Templates.
 */
public class Tab_ThreeActivity extends Activity {
	Button bt_date;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
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
    
}