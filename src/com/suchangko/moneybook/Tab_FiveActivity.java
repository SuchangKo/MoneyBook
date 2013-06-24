package com.suchangko.moneybook;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 4. 3
 * Time: 오후 8:55
 * To change this template use File | Settings | File Templates.
 * 파이그래프, 꺾은선 그래프 완료시킬것.
 */
@SuppressLint("NewApi")
public class Tab_FiveActivity extends Activity implements OnClickListener {
	  /** The main dataset that includes all the series that go into a chart. */
	  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	  /** The main renderer that includes all the renderers customizing a chart. */
	  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	  /** The most recently added series. */
	  private XYSeries mCurrentSeries;
	  /** The most recently created renderer, customizing the current series. */
	  private XYSeriesRenderer mCurrentRenderer;
	  private GraphicalView mChartView;
	  private WindowManager mWindowManager;
	  /** Colors to be used for the pie slices. */
	  private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN };
	  /** The main series that will include all the data. */
	  private CategorySeries mSeries = new CategorySeries("");
	  /** The main renderer for the main dataset. */
	  private DefaultRenderer PiemRenderer = new DefaultRenderer();
	  LinearLayout layout;
	  int statisticscode = 1; // 1=지/전 2=수/전 3=지/분 4=수/분
		Button bt_next;
		Button bt_pre;
		Button btdate;
		Button bt4;
		Button bt5;
		Button bt6;
		MoneyInputDB inputDB;
		MoneyBookDB moneyBookDB;
		int maxmoney=0;
		int getx;
		int gety;
		private float mTouchX, mTouchY;
		private WindowManager.LayoutParams mParams; 
		private LayoutInflater inflater;
		private LinearLayout popButton;
		TextView tv1,tv2,tv3,tv4;
		boolean popup=false;
		double LastXvalue=0;
		Calendar calendar_1_bt;
		Calendar calendar_2_bt;

			ArrayList<String> dateArrayList;
			SimpleDateFormat simpleDateFormat;
			boolean kind_month=true;
			String kindofString="";


	 @Override
	  protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    // save the current data, for instance when changing screen orientation
	   
	    if(statisticscode==1 || statisticscode==2){
	    	 outState.putSerializable("dataset", mDataset);
	 	    outState.putSerializable("renderer", mRenderer);
	 	    outState.putSerializable("current_series", mCurrentSeries);
	 	    outState.putSerializable("current_renderer", mCurrentRenderer);
	    }else if(statisticscode==3 || statisticscode==4){
	    	 outState.putSerializable("current_series", mSeries);
	 	    outState.putSerializable("current_renderer", PiemRenderer);
	    }
	   
	  }

	  @Override
	  protected void onRestoreInstanceState(Bundle savedState) {
	    super.onRestoreInstanceState(savedState);
	    // restore the current data, for instance when changing the screen
	    // orientation
	    if(statisticscode==1 || statisticscode==2){
	    	mDataset = (XYMultipleSeriesDataset) savedState.getSerializable("dataset");
		    mRenderer = (XYMultipleSeriesRenderer) savedState.getSerializable("renderer");
		    mCurrentSeries = (XYSeries) savedState.getSerializable("current_series");
		    mCurrentRenderer = (XYSeriesRenderer) savedState.getSerializable("current_renderer");
	    }else if(statisticscode==3 || statisticscode==4){
	    	mSeries = (CategorySeries) savedState.getSerializable("current_series");
		    PiemRenderer = (DefaultRenderer) savedState.getSerializable("current_renderer");
	    }
	    }
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_tab5);
       inflater  = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
       popButton=(LinearLayout)inflater.inflate(R.layout.miniview,null);
       tv1=(TextView)popButton.findViewById(R.id.tv1);
       tv2=(TextView)popButton.findViewById(R.id.tv2);
       tv3=(TextView)popButton.findViewById(R.id.tv3);
       tv4=(TextView)popButton.findViewById(R.id.tv4);
       simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
       inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
       moneyBookDB =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
       inputDB.open();
       moneyBookDB.open();
       bt_next=(Button)findViewById(R.id.button2);
       bt_next.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if (kind_month) {
				calendar_1_bt.set(Calendar.MONTH,calendar_1_bt.get(Calendar.MONTH)+1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,1);
				calendar_2_bt.set(Calendar.MONTH,calendar_2_bt.get(Calendar.MONTH)+1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,calendar_2_bt.getActualMaximum(Calendar.DAY_OF_MONTH));
				//makeAdapter(statisticscode);
				//adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
		      //  listView.setAdapter(adapter);
		        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
		        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
		        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
		      
		        btdate.setText(dateString);
		        
		        if(statisticscode==1 || statisticscode==2){
		        	layout.removeView(mChartView);		    		
		    		mChartView=null;
		    		 makelinechart(statisticscode);
		    		mChartView = ChartFactory.getLineChartView(Tab_FiveActivity.this, mDataset, mRenderer);
			   	      mChartView.setBackgroundColor(Color.WHITE);
			   	      // enable the chart click events
			   	      mRenderer.setClickEnabled(true);
			   	      mRenderer.setSelectableBuffer(10);
			   	      mRenderer.setInScroll(true);
			   	      mRenderer.setZoomEnabled(true);
			   	      
			   	    //  mChartView.invalidate();
			   	   //mChartView.repaint();
			   	    layout.addView(mChartView);
			   	   addchartlistener();
				}else{
		    		layout.removeView(mChartView);
		    		mChartView=null;
		    		makepiechart(statisticscode);
		    		mChartView = ChartFactory.getPieChartView(Tab_FiveActivity.this, mSeries, PiemRenderer);
		    		
		    	      PiemRenderer.setClickEnabled(true);
		    	      mChartView.setOnClickListener(new View.OnClickListener() {
		    	        @Override
		    	        public void onClick(View v) {
		    	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		    	          if (seriesSelection == null) {
		    	            Toast.makeText(getApplicationContext(), "No chart element selected", Toast.LENGTH_SHORT)
		    	                .show();
		    	          } else {
		    	            for (int i = 0; i < mSeries.getItemCount(); i++) {
		    	              PiemRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
		    	            }
		    	            mChartView.repaint();
		    	            Toast.makeText(
		    	                getApplicationContext(),
		    	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
		    	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
		    	          }
		    	        }
		    	      });
		    	      
		    	      layout.addView(mChartView);    		
		    		
				}
			}else{
				calendar_1_bt.set(Calendar.YEAR,calendar_1_bt.get(Calendar.YEAR)+1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,1);
				calendar_2_bt.set(Calendar.YEAR,calendar_2_bt.get(Calendar.YEAR)+1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,calendar_2_bt.getActualMaximum(Calendar.DAY_OF_MONTH));
				//makeAdapter(statisticscode);
				//adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
		      //  listView.setAdapter(adapter);
		        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
		        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
		        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
		      
		        btdate.setText(dateString);
		        
		        if(statisticscode==1 || statisticscode==2){
		        	layout.removeView(mChartView);		    		
		    		mChartView=null;
		    		 makelinechart(statisticscode);
		    		mChartView = ChartFactory.getLineChartView(Tab_FiveActivity.this, mDataset, mRenderer);
			   	      mChartView.setBackgroundColor(Color.WHITE);
			   	      // enable the chart click events
			   	      mRenderer.setClickEnabled(true);
			   	      mRenderer.setSelectableBuffer(10);
			   	      mRenderer.setInScroll(true);
			   	      mRenderer.setZoomEnabled(true);
			   	      
			   	    //  mChartView.invalidate();
			   	   //mChartView.repaint();
			   	    layout.addView(mChartView);
			   	   addchartlistener();
				}else{
		    		layout.removeView(mChartView);
		    		mChartView=null;
		    		makepiechart(statisticscode);
		    		mChartView = ChartFactory.getPieChartView(Tab_FiveActivity.this, mSeries, PiemRenderer);
		    		
		    	      PiemRenderer.setClickEnabled(true);
		    	      mChartView.setOnClickListener(new View.OnClickListener() {
		    	        @Override
		    	        public void onClick(View v) {
		    	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		    	          if (seriesSelection == null) {
		    	            Toast.makeText(getApplicationContext(), "No chart element selected", Toast.LENGTH_SHORT)
		    	                .show();
		    	          } else {
		    	            for (int i = 0; i < mSeries.getItemCount(); i++) {
		    	              PiemRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
		    	            }
		    	            mChartView.repaint();
		    	            Toast.makeText(
		    	                getApplicationContext(),
		    	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
		    	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
		    	          }
		    	        }
		    	      });
		    	      
		    	      layout.addView(mChartView);    		
		    		
				}
			}
		}
	});
       
      
       bt_pre=(Button)findViewById(R.id.button1);
       bt_pre.setOnClickListener(new OnClickListener() {
   		
   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   			if (kind_month) {
				
				calendar_1_bt.set(Calendar.MONTH,calendar_1_bt.get(Calendar.MONTH)-1);
				//calendar_2_bt = (Calendar) calendar_1_bt.clone();
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,1);
				calendar_2_bt.set(Calendar.MONTH,calendar_2_bt.get(Calendar.MONTH)-1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,calendar_2_bt.getActualMaximum(Calendar.DAY_OF_MONTH));
				
				///adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
		       // listView.setAdapter(adapter);
		        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
		        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
		        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
		      
		        btdate.setText(dateString);
		        
		        if(statisticscode==1 || statisticscode==2){
		        	layout.removeView(mChartView);		    		
		    		mChartView=null;
		    		 makelinechart(statisticscode);
		    		mChartView = ChartFactory.getLineChartView(Tab_FiveActivity.this, mDataset, mRenderer);
			   	      mChartView.setBackgroundColor(Color.WHITE);
			   	      // enable the chart click events
			   	      mRenderer.setClickEnabled(true);
			   	      mRenderer.setSelectableBuffer(10);
			   	      mRenderer.setInScroll(true);
			   	      mRenderer.setZoomEnabled(true);
			   	      
			   	    //  mChartView.invalidate();
			   	   //mChartView.repaint();
			   	    layout.addView(mChartView);
			   	   addchartlistener();
				}else{
		    		layout.removeView(mChartView);
		    		mChartView=null;
		    		makepiechart(statisticscode);
		    		mChartView = ChartFactory.getPieChartView(Tab_FiveActivity.this, mSeries, PiemRenderer);
		    		
		    	      PiemRenderer.setClickEnabled(true);
		    	      mChartView.setOnClickListener(new View.OnClickListener() {
		    	        @Override
		    	        public void onClick(View v) {
		    	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		    	          if (seriesSelection == null) {
		    	            Toast.makeText(getApplicationContext(), "No chart element selected", Toast.LENGTH_SHORT)
		    	                .show();
		    	          } else {
		    	            for (int i = 0; i < mSeries.getItemCount(); i++) {
		    	              PiemRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
		    	            }
		    	            mChartView.repaint();
		    	            Toast.makeText(
		    	                getApplicationContext(),
		    	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
		    	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
		    	          }
		    	        }
		    	      });
		    	      
		    	      layout.addView(mChartView);    		
		    		
				}
			}else{
				calendar_1_bt.set(Calendar.YEAR,calendar_1_bt.get(Calendar.YEAR)-1);
				//calendar_2_bt = (Calendar) calendar_1_bt.clone();
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,1);
				calendar_2_bt.set(Calendar.YEAR,calendar_2_bt.get(Calendar.YEAR)-1);
				calendar_2_bt.set(Calendar.DAY_OF_MONTH,calendar_2_bt.getActualMaximum(Calendar.DAY_OF_MONTH));
				
				///adapter = new ListViewAdapter(getApplicationContext(), R.layout.layout4,arrayList1,arrayList2,arrayList3);
		       // listView.setAdapter(adapter);
		        Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
		        Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
		        String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
		      
		        btdate.setText(dateString);
		        
		        if(statisticscode==1 || statisticscode==2){
		        	layout.removeView(mChartView);		    		
		    		mChartView=null;
		    		 makelinechart(statisticscode);
		    		mChartView = ChartFactory.getLineChartView(Tab_FiveActivity.this, mDataset, mRenderer);
			   	      mChartView.setBackgroundColor(Color.WHITE);
			   	      // enable the chart click events
			   	      mRenderer.setClickEnabled(true);
			   	      mRenderer.setSelectableBuffer(10);
			   	      mRenderer.setInScroll(true);
			   	      mRenderer.setZoomEnabled(true);
			   	      
			   	    //  mChartView.invalidate();
			   	   //mChartView.repaint();
			   	    layout.addView(mChartView);
			   	   addchartlistener();
				}else{
		    		layout.removeView(mChartView);
		    		mChartView=null;
		    		makepiechart(statisticscode);
		    		mChartView = ChartFactory.getPieChartView(Tab_FiveActivity.this, mSeries, PiemRenderer);
		    		
		    	      PiemRenderer.setClickEnabled(true);
		    	      mChartView.setOnClickListener(new View.OnClickListener() {
		    	        @Override
		    	        public void onClick(View v) {
		    	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		    	          if (seriesSelection == null) {
		    	            Toast.makeText(getApplicationContext(), "No chart element selected", Toast.LENGTH_SHORT)
		    	                .show();
		    	          } else {
		    	            for (int i = 0; i < mSeries.getItemCount(); i++) {
		    	              PiemRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
		    	            }
		    	            mChartView.repaint();
		    	            Toast.makeText(
		    	                getApplicationContext(),
		    	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
		    	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
		    	          }
		    	        }
		    	      });
		    	      
		    	      layout.addView(mChartView);    		
		    		
				}
			}
   		}
   	});
       btdate = (Button)findViewById(R.id.button3);
       GregorianCalendar gregorianCalendar = new GregorianCalendar();
       calendar_1_bt =  Calendar.getInstance();
       calendar_1_bt.set(Calendar.DAY_OF_MONTH,1);
       calendar_2_bt =  Calendar.getInstance();
       calendar_2_bt.set(Calendar.DAY_OF_MONTH,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
       
       Date ddate1 = new Date(calendar_1_bt.get(Calendar.YEAR)-1900,calendar_1_bt.get(Calendar.MONTH),calendar_1_bt.get(Calendar.DAY_OF_MONTH));
       Date ddate2 = new Date(calendar_2_bt.get(Calendar.YEAR)-1900,calendar_2_bt.get(Calendar.MONTH),calendar_2_bt.get(Calendar.DAY_OF_MONTH));
       
       
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       
       String dateString = simpleDateFormat.format(ddate1) + " ~ "+ simpleDateFormat.format(ddate2);
      
       btdate.setText(dateString);
       btdate.setOnClickListener(this);
       bt4=(Button)findViewById(R.id.button4);
       bt5=(Button)findViewById(R.id.button5);
       bt6=(Button)findViewById(R.id.button6);
       bt4.setOnClickListener(this);
       bt5.setOnClickListener(this);
       bt6.setOnClickListener(this);
       // set some properties on the main renderer
      
       
       makelinechart(statisticscode);
       
       
       mParams = new WindowManager.LayoutParams(
               WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.TYPE_PHONE, //TouchEvent ����      
               WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //KeyEvent, TouchEvent�� Child(��)�� �ѱ� 
               PixelFormat.TRANSLUCENT );      
           mParams.gravity = Gravity.LEFT  | Gravity.TOP; 
    }
	void makepiechart(int code ){
		
		PiemRenderer.setZoomButtonsVisible(true);
	    PiemRenderer.setStartAngle(180);
	    PiemRenderer.setDisplayValues(true);
	    PiemRenderer.setLabelsTextSize(17);
	    PiemRenderer.setLegendTextSize(17);
	    
		mSeries.clear();
		PiemRenderer.removeAllRenderers();
		
		Calendar c1 = (Calendar) calendar_1_bt.clone();
		Calendar c2 = (Calendar) calendar_1_bt.clone();
	    c2.set(Calendar.YEAR,c1.get(Calendar.YEAR)-1);
	    c2.set(Calendar.MONTH,c2.get(Calendar.MONTH));
	    Date tmp1 = new Date(c2.get(Calendar.YEAR)-1900,c2.get(Calendar.MONTH)+1,1);
 	   	GregorianCalendar gregorianCalendar = new GregorianCalendar(c1.get(Calendar.YEAR),c1.get(Calendar.MONTH)-1,c1.get(Calendar.DAY_OF_MONTH));   
 	   	Date tmp2 = new Date(c1.get(Calendar.YEAR)-1900,c1.get(Calendar.MONTH)+1,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
 	   	String dbname="";
 	   Cursor c = null;
 	   	if(code==3){
 	   		dbname=moneyBookDB.SQL_DBname;
 	   		c=moneyBookDB.RawQueryString("SELECT * FROM "+dbname+" WHERE date BETWEEN "+String.valueOf(tmp1.getTime())+" AND "+String.valueOf(tmp2.getTime())+" ORDER BY kindof ASC");
 	   		
 	   	}else if(code==4){
 	   		dbname=inputDB.SQL_DBname;
 	   		c=inputDB.RawQueryString("SELECT * FROM "+dbname+" WHERE date BETWEEN "+String.valueOf(tmp1.getTime())+" AND "+String.valueOf(tmp2.getTime())+" ORDER BY kindof ASC");
 	   	}
 	   	
 	   	if(c.getCount()>0){
 	   		if(c.moveToFirst()){
 	   			String kindof="";
 	   			int money=0;
 	   			do{
 	   				String[] a1 = c.getString(c.getColumnIndex("kindof")).split("\\+");
 	   				String a = a1[0];
 	   				
 	   				if(!kindof.equals(a)){
 	   					Log.d("log",a);
 	   					money+=Integer.parseInt(c.getString(c.getColumnIndex("money")));
 	   					if(money>0){
 	   						Log.d("money","kindof : "+money);
 	   						mSeries.add(a,(double)money);
 	   					SimpleSeriesRenderer renderer  =new SimpleSeriesRenderer();
 	   					renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
 	   					PiemRenderer.addSeriesRenderer(renderer);
 	   						money=0;
 	   					}
 	   					kindof=a; 	   					
 	   				}else{
 	   					money+=Integer.parseInt(c.getString(c.getColumnIndex("money")));
 	   					Log.d("",""+money);
 	   				}
 	   				
 	   			}while(c.moveToNext());
 	   			
 	   		}
 	   	}else{
 	   		Toast.makeText(getApplicationContext(), "자료가 없습니다.",Toast.LENGTH_SHORT).show();
 	   	}
		 
				
		
		//mSeries.add("",1.0); // 값 input
		
       // mChartView.repaint();
		
		
		

	}
	void makelinechart(int code){ 
		mRenderer=new XYMultipleSeriesRenderer();
		mRenderer.clearTextLabels();
		
		mDataset.clear();
		dateArrayList= new ArrayList<String>();
		mRenderer.setApplyBackgroundColor(true);
	    mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
	    mRenderer.setBackgroundColor(Color.WHITE);
	    mRenderer.setBarSpacing(1);
	    mRenderer.setAxisTitleTextSize(17);
	    mRenderer.setChartTitleTextSize(20);
	    mRenderer.setLabelsTextSize(20);
	    mRenderer.setZoomEnabled(true);
	    mRenderer.setXLabelsPadding(30);       
	    mRenderer.setLegendTextSize(0);
	    
	    mRenderer.setXAxisMin(0);
	    mRenderer.setXAxisMax(14);
	    mRenderer.setMargins(new int[] { 20, 50, 15, 50 });
	    mRenderer.setZoomButtonsVisible(true);
	    mRenderer.setPointSize(10);
	    mRenderer.setGridColor(Color.WHITE);
	    mRenderer.setXLabels(0);
	    mRenderer.setMarginsColor(Color.WHITE);
	    String Title = "Helloworld";
	    XYSeries ss = new XYSeries(Title);
	    mDataset.addSeries(ss);
	    mCurrentSeries = ss;
	    XYSeriesRenderer r = new XYSeriesRenderer();
	    mRenderer.addSeriesRenderer(r);
	    r.setPointStyle(PointStyle.CIRCLE);
	    r.setPointStrokeWidth(10);
	    r.setFillPoints(true);
	    r.setDisplayChartValues(true);
	    r.setDisplayChartValuesDistance(10);
	    
	    
	    r.setColor(Color.parseColor("#1482f9"));
	    r.setLineWidth(3);
	    
	    
	    mCurrentRenderer = r;
    
		 //ArrayList<Integer> integers = new ArrayList<Integer>();
	       Calendar c1 = (Calendar) calendar_1_bt.clone();
	       Calendar c2 = (Calendar) calendar_1_bt.clone();
	       c2.set(Calendar.YEAR,c1.get(Calendar.YEAR)-1);
	       c2.set(Calendar.MONTH,c2.get(Calendar.MONTH));
	       
	       
	       boolean prezero=false;
	       boolean notzero=false;
	      	Log.d("1","1");
	      	 int cnt=1;
	       for(int i=0;i<12;i++){
	    	   int money_fin = 0;
	    	   Date tmp1 = new Date(c2.get(Calendar.YEAR)-1900,c2.get(Calendar.MONTH)+1,1);
	    	   GregorianCalendar gregorianCalendar = new GregorianCalendar(c2.get(Calendar.YEAR),c2.get(Calendar.MONTH)-1,c2.get(Calendar.DAY_OF_MONTH));
	    	   
	    	   Date tmp2 = new Date(c2.get(Calendar.YEAR)-1900,c2.get(Calendar.MONTH)+1,gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	    	   Cursor tc = null;
	    	   if(code==1){
	    		   String queryStr = "SELECT money,date FROM "+moneyBookDB.SQL_DBname+" WHERE date BETWEEN "+String.valueOf(tmp1.getTime())+" AND "+String.valueOf(tmp2.getTime())+" ORDER BY date ASC";
	    		   if(!kindofString.equals("")){
	    			   queryStr="SELECT money,date FROM "+moneyBookDB.SQL_DBname+" WHERE kindof LIKE '"+kindofString+"%' AND date BETWEEN "+String.valueOf(tmp1.getTime())+" AND "+String.valueOf(tmp2.getTime())+" ORDER BY date ASC";
	    		   }
	    		   tc = moneyBookDB.RawQueryString(queryStr);
	    	   }else if(code==2){
	    		   String queryStr = "SELECT money,date FROM "+inputDB.SQL_DBname+" WHERE date BETWEEN "+String.valueOf(tmp1.getTime())+" AND "+String.valueOf(tmp2.getTime())+" ORDER BY date ASC";
	    		   if(!kindofString.equals("")){
	    			   queryStr="SELECT money,date FROM "+inputDB.SQL_DBname+" WHERE kindof LIKE '"+kindofString+"%' AND date BETWEEN "+String.valueOf(tmp1.getTime())+" AND "+String.valueOf(tmp2.getTime())+" ORDER BY date ASC";
	    		   }
	    		   tc = inputDB.RawQueryString(queryStr);
	    	   }
	    	   String date ="";
	    	   String firstdate="";
	    	   if(tc.getCount()>0){
	    		   if(tc.moveToFirst()){
	    			   do{
	    				   int money = Integer.parseInt(tc.getString(0));
	    				   money_fin+=money;
	    				   date = tc.getString(1);
	    				   if(firstdate.equals("")){
	    					   firstdate=tc.getString(1);
	    				   }
	    			   }while(tc.moveToNext());
	    		   }
	    	   }
	    	   if(i==0){
		    	   if(money_fin==0){
		    		   prezero=true;
		    	   }else if(money_fin>0){
		    		   notzero=true;
		    	   }
	    	   }
	    	   
	    	   if(money_fin>0){
	    		   if(prezero){
	    			   notzero=true;
	    		   }
	    	   }
	    	  
	    	   if(notzero){
	    		   
		    	   add(cnt,money_fin);
		    	   dateArrayList.add(date+"-"+firstdate);
		    	   Log.d("firstdate",firstdate);
		    	   Log.d("date",date);
		    	   Log.d("d",cnt+"::"+(((c2.get(Calendar.MONTH)+2)%13)+1)+"월");
		    	   mRenderer.addTextLabel(cnt,(((c2.get(Calendar.MONTH)+1)%12)+1)+"월");
		    	   cnt++;
	    	   }
	    	   
	    	   
	    	   
	    	   if(maxmoney<money_fin){
	    		   maxmoney=money_fin;
	    	   }
	    	   c2.set(Calendar.MONTH,c2.get(Calendar.MONTH)+1);
	    	   
	       }
	       if(dateArrayList.size()==0){
	    	   Toast.makeText(getApplicationContext(), "자료가 없습니다.",Toast.LENGTH_SHORT).show();
	       }
	       mRenderer.setYAxisMin(-(maxmoney/6));
	       mRenderer.setYAxisMax(maxmoney+(maxmoney/6));
	       mRenderer.setXLabelsAlign(Align.CENTER);
	}
	@Override
	public void onClick(View v) {
		if(popup){
			  mWindowManager.removeView(popButton);
    		  popup=false;
		}
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button4){
		AlertDialog.Builder builder = new AlertDialog.Builder(Tab_FiveActivity.this);
		builder.setTitle("통계 종류 선택");
		builder.setItems(util.statistics2, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	bt4.setText(util.statistics2[item]);
		    	bt5.setText(util.allspend[0]);
		    	//Toast.makeText(getApplicationContext(), util.statistics2[item],Toast.LENGTH_SHORT).show();
		    	if(util.statistics2[item].equals("지출/전체")){
		    		statisticscode=1; 
		    		
		    		layout.removeView(mChartView);		    		
		    		mChartView=null;
		    		 makelinechart(statisticscode);
		    		mChartView = ChartFactory.getLineChartView(Tab_FiveActivity.this, mDataset, mRenderer);
			   	      mChartView.setBackgroundColor(Color.WHITE);
			   	      // enable the chart click events
			   	      mRenderer.setClickEnabled(true);
			   	      mRenderer.setSelectableBuffer(10);
			   	      mRenderer.setInScroll(true);
			   	      mRenderer.setZoomEnabled(true);
			   	      
			   	    //  mChartView.invalidate();
			   	   //mChartView.repaint();
			   	    layout.addView(mChartView);
			   	   addchartlistener();
		    		//mChartView.invalidate();
		    		
		    		
		    		
		    	}else if(util.statistics2[item].equals("수입/전체")){
		    		//mChartView=null;
		    		//mChartView.repaint();
		    		statisticscode=2;
		    		layout.removeView(mChartView);		    		
		    		mChartView=null;
		    		 makelinechart(statisticscode);
		    		mChartView = ChartFactory.getLineChartView(Tab_FiveActivity.this, mDataset, mRenderer);
			   	      mChartView.setBackgroundColor(Color.WHITE);
			   	      // enable the chart click events
			   	      mRenderer.setClickEnabled(true);
			   	      mRenderer.setSelectableBuffer(10);
			   	      mRenderer.setInScroll(true);
			   	      mRenderer.setZoomEnabled(true);
			   	      
			   	    //  mChartView.invalidate();
			   	   //mChartView.repaint();
			   	    layout.addView(mChartView);
			   	   addchartlistener();
		    		//mChartView.invalidate();
		    		
		    		
		    		
		    		
		    		//addchartlistener();
		    	}else if(util.statistics2[item].equals("지출/분류")){
		    		statisticscode=3;
		    		layout.removeView(mChartView);
		    		mChartView=null;
		    		makepiechart(statisticscode);
		    		mChartView = ChartFactory.getPieChartView(Tab_FiveActivity.this, mSeries, PiemRenderer);
		    		
		    	      PiemRenderer.setClickEnabled(true);
		    	      mChartView.setOnClickListener(new View.OnClickListener() {
		    	        @Override
		    	        public void onClick(View v) {
		    	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		    	          if (seriesSelection == null) {
		    	            Toast.makeText(getApplicationContext(), "No chart element selected", Toast.LENGTH_SHORT)
		    	                .show();
		    	          } else {
		    	            for (int i = 0; i < mSeries.getItemCount(); i++) {
		    	              PiemRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
		    	            }
		    	            mChartView.repaint();
		    	            Toast.makeText(
		    	                getApplicationContext(),
		    	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
		    	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
		    	          }
		    	        }
		    	      });
		    	      
		    	      layout.addView(mChartView);    		
		    		
		    		
		    	}else if(util.statistics2[item].equals("수입/분류")){
		    		statisticscode=4;
		    		layout.removeView(mChartView);
		    		mChartView=null;
		    		makepiechart(statisticscode);
		    		mChartView = ChartFactory.getPieChartView(Tab_FiveActivity.this, mSeries, PiemRenderer);
		    		
		    	      PiemRenderer.setClickEnabled(true);
		    	      mChartView.setOnClickListener(new View.OnClickListener() {
		    	        @Override
		    	        public void onClick(View v) {
		    	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		    	          if (seriesSelection == null) {
		    	            Toast.makeText(getApplicationContext(), "No chart element selected", Toast.LENGTH_SHORT)
		    	                .show();
		    	          } else {
		    	            for (int i = 0; i < mSeries.getItemCount(); i++) {
		    	              PiemRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
		    	            }
		    	            mChartView.repaint();
		    	            Toast.makeText(
		    	                getApplicationContext(),
		    	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
		    	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
		    	          }
		    	        }
		    	      });
		    	      
		    	      layout.addView(mChartView);    		
		    	}
		    	
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		}else if(v.getId()==R.id.button6){
			AlertDialog.Builder builder = new AlertDialog.Builder(Tab_FiveActivity.this);
			builder.setTitle("통계 단위 선택");
			builder.setItems(util.monthyear, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	bt6.setText(util.monthyear[item]);
			    	if(item==0){
			    		kind_month=true;
			    		
			    	}else{
			    		kind_month=false;
			    	}
			    	
			    	if(statisticscode==1 || statisticscode==2){
						 makelinechart(statisticscode);
						 mChartView.repaint();
					}else{
						makepiechart(statisticscode);
						mChartView.repaint();
					}
			    	//Toast.makeText(getApplicationContext(), util.Middleitems[item], Toast.LENGTH_SHORT).show();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();	
		}else if(v.getId()==R.id.button5){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(Tab_FiveActivity.this);
			builder.setTitle("통계 분류 선택");
			builder.setItems(util.allspend, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	
			    	if(statisticscode==1){
			    		bt5.setText(util.allspend[item]);
			    		if(item==0){
			    			kindofString="";
			    		}else{
			    			kindofString=util.allspend[item].toString();
			    		}
			    		statisticscode=1; 
			    		
			    		layout.removeView(mChartView);		    		
			    		mChartView=null;
			    		 makelinechart(statisticscode);
			    		mChartView = ChartFactory.getLineChartView(Tab_FiveActivity.this, mDataset, mRenderer);
				   	      mChartView.setBackgroundColor(Color.WHITE);
				   	      // enable the chart click events
				   	      mRenderer.setClickEnabled(true);
				   	      mRenderer.setSelectableBuffer(10);
				   	      mRenderer.setInScroll(true);
				   	      mRenderer.setZoomEnabled(true);
				   	      
				   	    //  mChartView.invalidate();
				   	   //mChartView.repaint();
				   	    layout.addView(mChartView);
				   	   addchartlistener();
			    	}else{
			    		Toast.makeText(getApplicationContext(),"통계 분류는 지출/전체에서만 지원됩니다.", Toast.LENGTH_SHORT).show();
			    	}
			    	
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		}else if(v.getId()==R.id.button3){
			Toast.makeText(getApplicationContext(), "새로고침",Toast.LENGTH_SHORT).show();
			if(statisticscode==1 || statisticscode==2){
				 makelinechart(statisticscode);
				 mChartView.repaint();
			}else{
				makepiechart(statisticscode);
				mChartView.repaint();
			}
			/*
			mCurrentSeries.clear();
			mDataset.clear();
			makelinechart(statisticscode);
			mChartView.repaint();
			  mChartView.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						getx = (int)(event.getRawX() - mTouchX); 
						gety = (int)(event.getRawY() - mTouchY);   
						
						mParams.x = getx+20;
						mParams.y = gety-120;
						
						return false;
					}
				});
			  mChartView.setOnClickListener(new View.OnClickListener() {
		   	        public void onClick(View v) {
		   	          // handle the click event on the chart
		   	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		   	          if (seriesSelection == null) {
		   	           // Toast.makeText(Tab_FiveActivity.this, "No chart element", Toast.LENGTH_SHORT).show();
		   	          } else {
		   	            // display information of the clicked point
		   	        	//Toast.makeText(getApplicationContext(), "getx ="+getx+" AND gety ="+gety,1000).show();
		   	        	  DecimalFormat df = new DecimalFormat("#,##0");
		   	        	  
		   	        	  String datetext = dateArrayList.get((int)seriesSelection.getXValue()-1);
		   	        	  String datearr[] = datetext.split("-");
		   	        	  Date first = new Date(Long.parseLong(datearr[1]));
		   	        	  Date Last = new Date(Long.parseLong(datearr[0]));
		   	        	tv1.setText(simpleDateFormat.format(first));
		   	        	tv2.setText(simpleDateFormat.format(Last));
		   	        	  
		   	        	tv3.setText("￦"+df.format((int)seriesSelection.getValue()));
		   	        	tv4.setText("￦"+df.format((int)seriesSelection.getValue()));
		   	        	  if(LastXvalue==0 || LastXvalue==seriesSelection.getXValue()){
		   	        		 LastXvalue=seriesSelection.getXValue();
		   	        		 
		   	        		 if(popup){
		   		        		  mWindowManager.removeView(popButton);
		   		        		  popup=false;
		   		        	  }else{
		   		        		  mWindowManager.addView(popButton, mParams);
		   		        		  popup=true;
		   		        	  }
		   	        		 
		   	        	  }else{
		   	        		  LastXvalue=seriesSelection.getXValue();
		   	        		  if(popup){
		   		        		  mWindowManager.removeView(popButton);
		   		        		  popup=false;
		   		        		  mWindowManager.addView(popButton, mParams);
		   		        		  popup=true;
		   		        	  }else{
		   		        		  
		   		        	  }
		   	        		  
		   	        	  }
		   	        	  
		   	        	
		   	        	
		   	        	Toast.makeText(
		   	            		Tab_FiveActivity.this,
		   	                "Chart element in series index " + seriesSelection.getSeriesIndex()
		   	                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
		   	                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
		   	                    + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
		   	                    
		   	        	}
		   	        }
		   	      });
			*/
		}
	}
	
	void addchartlistener(){
		mChartView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				getx = (int)(event.getRawX() - mTouchX); 
				gety = (int)(event.getRawY() - mTouchY);   
				
				mParams.x = getx+20;
				mParams.y = gety-120;
				
				return false;
			}
		});
	  mChartView.setOnClickListener(new View.OnClickListener() {
   	        public void onClick(View v) {
   	          // handle the click event on the chart
   	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
   	          if (seriesSelection == null) {
   	           // Toast.makeText(Tab_FiveActivity.this, "No chart element", Toast.LENGTH_SHORT).show();
   	          } else {
   	            // display information of the clicked point
   	        	//Toast.makeText(getApplicationContext(), "getx ="+getx+" AND gety ="+gety,1000).show();
   	        	  DecimalFormat df = new DecimalFormat("#,##0");
   	        	  
   	        	  String datetext = dateArrayList.get((int)seriesSelection.getXValue()-1);
   	        	  Log.d("dsd",datetext);
   	        	  String datearr[] = datetext.split("-");
   	        	Log.d("dsd",datearr.length+"");
   	        	  if(datearr.length>=2){
   	        		 Date first = new Date(Long.parseLong(datearr[1]));
      	        	  Date Last = new Date(Long.parseLong(datearr[0]));
      	        	tv1.setText(simpleDateFormat.format(first));
      	        	tv2.setText(simpleDateFormat.format(Last));
      	        	  
      	        	tv3.setText("￦"+df.format((int)seriesSelection.getValue()));
      	        	tv4.setText("￦"+df.format((int)seriesSelection.getValue()));
      	        	  if(LastXvalue==0 || LastXvalue==seriesSelection.getXValue()){
      	        		 LastXvalue=seriesSelection.getXValue();
      	        		 
      	        		 if(popup){
      		        		  mWindowManager.removeView(popButton);
      		        		  popup=false;
      		        	  }else{
      		        		  mWindowManager.addView(popButton, mParams);
      		        		  popup=true;
      		        	  }
      	        		 
      	        	  }else{
      	        		  LastXvalue=seriesSelection.getXValue();
      	        		  if(popup){
      		        		  mWindowManager.removeView(popButton);
      		        		  popup=false;
      		        		  mWindowManager.addView(popButton, mParams);
      		        		  popup=true;
      		        	  }else{
      		        		  
      		        	  }
      	        		  
      	        	  }
   	        	  }else{
   	        		  Toast.makeText(getApplicationContext(), "데이터가 없습니다.",Toast.LENGTH_SHORT).show();
   	        	  }
   	        	 
   	        	  
   	        	
   	        	/*
   	        	Toast.makeText(
   	            		Tab_FiveActivity.this,
   	                "Chart element in series index " + seriesSelection.getSeriesIndex()
   	                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
   	                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
   	                    + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
   	                    */
   	        	}
   	        }
   	      });
	}
	 void add(int a,int b){
		  mCurrentSeries.add((double)a,(double)b);
	     
	  }
	 @Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			if(popup){
      		  mWindowManager.removeView(popButton);
      		  popup=false;
      	  	}
		}
	 void chartviewnull(){
		 
	 }
	 @Override
	  protected void onResume() {
	    super.onResume();
	    if (mChartView == null) {
	    	if(statisticscode==1 || statisticscode==2){
	    		 layout = (LinearLayout) findViewById(R.id.chart);
	   	      mChartView = ChartFactory.getLineChartView(this, mDataset, mRenderer);
	   	      mChartView.setBackgroundColor(Color.WHITE);
	   	      // enable the chart click events
	   	      mRenderer.setClickEnabled(true);
	   	      mRenderer.setSelectableBuffer(10);
	   	      mRenderer.setInScroll(false);
	   	      mRenderer.setZoomEnabled(true);
	   	      
	   	      mChartView.setOnTouchListener(new View.OnTouchListener() {
	   			
	   			@Override
	   			public boolean onTouch(View v, MotionEvent event) {
	   				// TODO Auto-generated method stub
	   				getx = (int)(event.getRawX() - mTouchX); 
	   				gety = (int)(event.getRawY() - mTouchY);   
	   				
	   				mParams.x = getx+20;
	   				mParams.y = gety-120;
	   				
	   				return false;
	   			}
	   		});
	   	      mChartView.setOnClickListener(new View.OnClickListener() {
	   	        public void onClick(View v) {
	   	          // handle the click event on the chart
	   	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
	   	          if (seriesSelection == null) {
	   	           // Toast.makeText(Tab_FiveActivity.this, "No chart element", Toast.LENGTH_SHORT).show();
	   	          } else {
	   	            // display information of the clicked point
	   	        	//Toast.makeText(getApplicationContext(), "getx ="+getx+" AND gety ="+gety,1000).show();
	   	        	  DecimalFormat df = new DecimalFormat("#,##0");
	   	        	  
	   	        	  String datetext = dateArrayList.get((int)seriesSelection.getXValue()-1);
	   	        	  String datearr[] = datetext.split("-");
	   	        	  Date first = new Date(Long.parseLong(datearr[1]));
	   	        	  Date Last = new Date(Long.parseLong(datearr[0]));
	   	        	tv1.setText(simpleDateFormat.format(first));
	   	        	tv2.setText(simpleDateFormat.format(Last));
	   	        	  
	   	        	tv3.setText("￦"+df.format((int)seriesSelection.getValue()));
	   	        	tv4.setText("￦"+df.format((int)seriesSelection.getValue()));
	   	        	  if(LastXvalue==0 || LastXvalue==seriesSelection.getXValue()){
	   	        		 LastXvalue=seriesSelection.getXValue();
	   	        		 
	   	        		 if(popup){
	   		        		  mWindowManager.removeView(popButton);
	   		        		  popup=false;
	   		        	  }else{
	   		        		  mWindowManager.addView(popButton, mParams);
	   		        		  popup=true;
	   		        	  }
	   	        		 
	   	        	  }else{
	   	        		  LastXvalue=seriesSelection.getXValue();
	   	        		  if(popup){
	   		        		  mWindowManager.removeView(popButton);
	   		        		  popup=false;
	   		        		  mWindowManager.addView(popButton, mParams);
	   		        		  popup=true;
	   		        	  }else{
	   		        		  
	   		        	  }
	   	        		  
	   	        	  }
	   	        	  
	   	        	
	   	        	/*
	   	        	Toast.makeText(
	   	            		Tab_FiveActivity.this,
	   	                "Chart element in series index " + seriesSelection.getSeriesIndex()
	   	                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
	   	                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
	   	                    + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
	   	                    */
	   	        	}
	   	        }
	   	      });
	   	      
	   	      layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
	   	          LayoutParams.FILL_PARENT));
	   	      boolean enabled = mDataset.getSeriesCount() > 0;
	   	      
	    	}else if(statisticscode==3 || statisticscode==4){
	    		 LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
	    	      mChartView = ChartFactory.getPieChartView(this, mSeries, PiemRenderer);
	    	      PiemRenderer.setClickEnabled(true);
	    	      mChartView.setOnClickListener(new View.OnClickListener() {
	    	        @Override
	    	        public void onClick(View v) {
	    	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
	    	          if (seriesSelection == null) {
	    	            Toast.makeText(getApplicationContext(), "No chart element selected", Toast.LENGTH_SHORT)
	    	                .show();
	    	          } else {
	    	            for (int i = 0; i < mSeries.getItemCount(); i++) {
	    	              PiemRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
	    	            }
	    	            mChartView.repaint();
	    	            Toast.makeText(
	    	                getApplicationContext(),
	    	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
	    	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
	    	          }
	    	        }
	    	      });
	    	      layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
	    	          LayoutParams.FILL_PARENT));
	    	}
	     
	    } else {
	      mChartView.repaint();
	    }
	  }


}