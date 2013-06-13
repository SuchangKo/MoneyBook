package com.suchangko.moneybook;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: SuChang_NT900X
 * Date: 13. 4. 3
 * Time: 오후 8:55
 * To change this template use File | Settings | File Templates.
 */
public class Tab_FiveActivity extends Activity {
	  /** The main dataset that includes all the series that go into a chart. */
	  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	  /** The main renderer that includes all the renderers customizing a chart. */
	  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	  /** The most recently added series. */
	  private XYSeries mCurrentSeries;
	  /** The most recently created renderer, customizing the current series. */
	  private XYSeriesRenderer mCurrentRenderer;
	  private GraphicalView mChartView;

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
       
       // set some properties on the main renderer
       mRenderer.setApplyBackgroundColor(true);
       mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
       mRenderer.setBackgroundColor(Color.WHITE);
       mRenderer.setBarSpacing(10);
       mRenderer.setAxisTitleTextSize(17);
       mRenderer.setChartTitleTextSize(20);
       mRenderer.setLabelsTextSize(20);
       mRenderer.setZoomEnabled(false);
       mRenderer.setXLabelsPadding(30);       
       mRenderer.setLegendTextSize(0);
       mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
       mRenderer.setZoomButtonsVisible(false);
       mRenderer.setPointSize(6);
       
       String Title = "Helloworld";
       XYSeries ss = new XYSeries(Title);
       mDataset.addSeries(ss);
       mCurrentSeries = ss;
       XYSeriesRenderer r = new XYSeriesRenderer();
       mRenderer.addSeriesRenderer(r);
       r.setPointStyle(PointStyle.CIRCLE);
       r.setFillPoints(true);
       r.setDisplayChartValues(true);
       r.setDisplayChartValuesDistance(10);
       r.setColor(Color.RED);
       r.setLineWidth(3);
       mCurrentRenderer = r;
       for(int i=0;i<12;i++){
       	add(i+1);
       }
      
    }
	 void add(int a){
		  mCurrentSeries.add((double)a,(double)a);
	     
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
	      mRenderer.setZoomEnabled(false);
	      mRenderer.setXAxisMax(12);
	      mChartView.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	          // handle the click event on the chart
	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
	          if (seriesSelection == null) {
	            Toast.makeText(Tab_FiveActivity.this, "No chart element", Toast.LENGTH_SHORT).show();
	          } else {
	            // display information of the clicked point
	            Toast.makeText(
	            		Tab_FiveActivity.this,
	                "Chart element in series index " + seriesSelection.getSeriesIndex()
	                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
	                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
	                    + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
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