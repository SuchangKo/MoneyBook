package com.suchangko.moneybook;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
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

	 @Override
	  protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    // save the current data, for instance when changing screen orientation
	    outState.putSerializable("dataset", mDataset);
	    outState.putSerializable("renderer", mRenderer);
	    outState.putSerializable("current_series", mCurrentSeries);
	    outState.putSerializable("current_renderer", mCurrentRenderer);
	  }

	  @Override
	  protected void onRestoreInstanceState(Bundle savedState) {
	    super.onRestoreInstanceState(savedState);
	    // restore the current data, for instance when changing the screen
	    // orientation
	    mDataset = (XYMultipleSeriesDataset) savedState.getSerializable("dataset");
	    mRenderer = (XYMultipleSeriesRenderer) savedState.getSerializable("renderer");
	    mCurrentSeries = (XYSeries) savedState.getSerializable("current_series");
	    mCurrentRenderer = (XYSeriesRenderer) savedState.getSerializable("current_renderer");
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
       
       mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
       inputDB = new MoneyInputDB(getApplicationContext(), MoneyInputDB.SQL_Create_Moneybook,MoneyInputDB.SQL_DBname);
       moneyBookDB =  new MoneyBookDB(this,MoneyBookDB.SQL_Create_Moneybook,MoneyBookDB.SQL_DBname);
       inputDB.open();
       moneyBookDB.open();
       bt_next=(Button)findViewById(R.id.button2);
       bt_pre=(Button)findViewById(R.id.button1);
       btdate = (Button)findViewById(R.id.button3);
       btdate.setOnClickListener(this);
       bt4=(Button)findViewById(R.id.button4);
       bt5=(Button)findViewById(R.id.button5);
       bt6=(Button)findViewById(R.id.button6);
       bt4.setOnClickListener(this);
       bt5.setOnClickListener(this);
       bt6.setOnClickListener(this);
       // set some properties on the main renderer
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
       r.setColor(Color.RED);
       r.setLineWidth(3);
       
       
       mCurrentRenderer = r;
       
       
       makechart();
       mRenderer.setYAxisMin(-(maxmoney/6));
       mRenderer.setYAxisMax(maxmoney+(maxmoney/6));
       mRenderer.setXLabelsAlign(Align.CENTER);
       
       mParams = new WindowManager.LayoutParams(
               WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.TYPE_PHONE, //TouchEvent ����      
               WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //KeyEvent, TouchEvent�� Child(��)�� �ѱ� 
               PixelFormat.TRANSLUCENT );      
           mParams.gravity = Gravity.LEFT  | Gravity.TOP; 
    }
	void makechart(){
		 //ArrayList<Integer> integers = new ArrayList<Integer>();
	       Calendar c1 = Calendar.getInstance();
	       Calendar c2 = Calendar.getInstance();
	       c2.set(Calendar.YEAR,c1.get(Calendar.YEAR)-1);
	       
	       
	       boolean prezero=false;
	       boolean notzero=false;
	      	Log.d("1","1");
	      	 int cnt=1;
	       for(int i=0;i<13;i++){
	    	   int money_fin = 0;
	    	   Date tmp1 = new Date(c2.get(Calendar.YEAR)-1900,c2.get(Calendar.MONTH)-1,c2.get(Calendar.DAY_OF_MONTH));
	    	   c2.set(Calendar.MONTH,c2.get(Calendar.MONTH)+1);
	    	   Date tmp2 = new Date(c2.get(Calendar.YEAR)-1900,c2.get(Calendar.MONTH)-1,c2.get(Calendar.DAY_OF_MONTH));
	    	   
	    	   String queryStr = "SELECT money,date FROM "+moneyBookDB.SQL_DBname+" WHERE date BETWEEN "+String.valueOf(tmp1.getTime())+" AND "+String.valueOf(tmp2.getTime());
	    	   Log.d("QUERY", queryStr);
	    	   Cursor tc = moneyBookDB.RawQueryString(queryStr);
	    	   Log.d("dd",tc.getCount()+"");
	    	   if(tc.getCount()>0){
	    		   if(tc.moveToFirst()){
	    			   do{
	    				   int money = Integer.parseInt(tc.getString(0));
	    				   money_fin+=money;
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
		    	   
		    	   mRenderer.addTextLabel(cnt,(c2.get(Calendar.MONTH)+1)+"월");
		    	   cnt++;
	    	   }
	    	   
	    	   
	    	   
	    	   if(maxmoney<money_fin){
	    		   maxmoney=money_fin;
	    	   }

	    	   
	       }
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button4){
		AlertDialog.Builder builder = new AlertDialog.Builder(Tab_FiveActivity.this);
		builder.setTitle("통계 종류 선택");
		builder.setItems(util.statistics1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	bt4.setText(util.statistics1[item]);
		    	//Toast.makeText(getApplicationContext(), util.Middleitems[item], Toast.LENGTH_SHORT).show();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		}else if(v.getId()==R.id.button6){
			AlertDialog.Builder builder = new AlertDialog.Builder(Tab_FiveActivity.this);
			builder.setTitle("통계 단위 선택");
			builder.setItems(util.monthyear, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	bt5.setText(util.monthyear[item]);
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
			    	bt6.setText(util.allspend[item]);
			    	//Toast.makeText(getApplicationContext(), util.Middleitems[item], Toast.LENGTH_SHORT).show();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		}else if(v.getId()==R.id.button3){
			Toast.makeText(getApplicationContext(), "새로고침",Toast.LENGTH_SHORT).show();
			mCurrentSeries.clear();
			makechart();
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
			        	  tv3.setText("￦"+df.format((int)seriesSelection.getValue()));
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
			
		}
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
	 @Override
	  protected void onResume() {
	    super.onResume();
	    if (mChartView == null) {
	      LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
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
	        	  tv3.setText("￦"+df.format((int)seriesSelection.getValue()));
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
	      
	    } else {
	      mChartView.repaint();
	    }
	  }


}