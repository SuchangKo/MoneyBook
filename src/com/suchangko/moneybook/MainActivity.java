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
        addTab("지출내역",R.drawable.tab_icon_1,Tab_OneActivity.class);
        addTab("수입내역",R.drawable.tab_icon_3,Tab_TwoActivity.class);
        addTab("예산관리",R.drawable.tab_icon_4,Tab_ThreeActivity.class);
        addTab("자료통계",R.drawable.tab_icon_2,Tab_FourActivity.class);
        addTab("자료차트",R.drawable.tab_icon_5,Tab_FiveActivity.class);
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
	
}
