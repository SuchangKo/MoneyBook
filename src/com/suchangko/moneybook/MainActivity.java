package com.suchangko.moneybook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity{
    TabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        tabHost = getTabHost();
        setTabs();

	}
    private void setTabs(){
        addTab("지출내역",R.drawable.ic_launcher,JoinActivity.class);
        addTab("지출내역",R.drawable.ic_launcher,JoinActivity.class);
        addTab("지출내역",R.drawable.ic_launcher,JoinActivity.class);
        addTab("지출내역",R.drawable.ic_launcher,JoinActivity.class);
        addTab("지출내역",R.drawable.ic_launcher,JoinActivity.class);

    }
    private void addTab(String title,int img_drawable , Class<?> c){
        Intent i = new Intent(this,c);
        TabHost.TabSpec spec = tabHost.newTabSpec("tab"+title);
        View TabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator,getTabWidget(),false);
        TextView tv_title = (TextView)TabIndicator.findViewById(R.id.title);
        tv_title.setText(title);
        ImageView icon = (ImageView)TabIndicator.findViewById(R.id.icon);
        icon.setImageResource(img_drawable);
        spec.setIndicator(TabIndicator);
        spec.setContent(i);
        tabHost.addTab(spec);

    }
	@Override
        public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
