package com.suchangko.moneybook;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 4. 3
 * Time: 오후 8:54
 * To change this template use File | Settings | File Templates.
 */
public class Tab_TwoActivity extends Activity {
	ListView lv;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        lv = (ListView)findViewById(R.id.listview1);
        
    }
}