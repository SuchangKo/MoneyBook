package com.suchangko.moneybook;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseExpandableAdapter extends BaseExpandableListAdapter{
    
   private ArrayList<String> groupList = null;
   private ArrayList<ArrayList<String>> childList = null;
   private LayoutInflater inflater = null;
   private ViewHolder viewHolder = null;

	
	  	 
   public BaseExpandableAdapter(Context c, ArrayList<String> groupList, 
           ArrayList<ArrayList<String>> childList){
       super();
       this.inflater = LayoutInflater.from(c);
       this.groupList = groupList;
       this.childList = childList;
   }
    
   // 그룹 포지션을 반환한다.
   @Override
   public String getGroup(int groupPosition) {
       return groupList.get(groupPosition);
   }

   // 그룹 사이즈를 반환한다.
   @Override
   public int getGroupCount() {
       return groupList.size();
   }

   // 그룹 ID를 반환한다.
   @Override
   public long getGroupId(int groupPosition) {
       return groupPosition;
   }

   // 그룹뷰 각각의 ROW 
   @Override
   public View getGroupView(int groupPosition, boolean isExpanded,
           View convertView, ViewGroup parent) {
        
       View v = convertView;
        
       if(v == null){
           viewHolder = new ViewHolder();
           v = inflater.inflate(R.layout.group_row, parent, false);
           viewHolder.tv_groupName = (TextView) v.findViewById(R.id.groupname);
           viewHolder.Total_money = (TextView)v.findViewById(R.id.total_money);
           v.setTag(viewHolder);
       }else{
           viewHolder = (ViewHolder)v.getTag();
       }
        /*
       // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
       if(isExpanded){
           viewHolder.iv_image.setBackgroundColor(Color.GREEN);
       }else{
           viewHolder.iv_image.setBackgroundColor(Color.WHITE);
       }
        */
       String tmp_String = getGroup(groupPosition);
       Log.d("tmp_String",tmp_String);
       String[] tmp_Strings = tmp_String.split("#");
       viewHolder.tv_groupName.setText(tmp_Strings[0]);
       viewHolder.Total_money.setText(tmp_Strings[1]);
       //viewHolder.Total_money.setText("￦"+df.format(Integer.parseInt(tmp_Strings[1])));
       
       return v;
   }
    
   // 차일드뷰를 반환한다.
   @Override
   public String getChild(int groupPosition, int childPosition) {
       return childList.get(groupPosition).get(childPosition);
   }
    
   // 차일드뷰 사이즈를 반환한다.
   @Override
   public int getChildrenCount(int groupPosition) {
       return childList.get(groupPosition).size();
   }

   // 차일드뷰 ID를 반환한다.
   @Override
   public long getChildId(int groupPosition, int childPosition) {
	   
       return childPosition;
   }
   public int getchildintid(int grouppos,int childpos){
	   int a=0;
	   String tmp_String = getChild(grouppos, childpos);
	   String[] Text=tmp_String.split("#");
	   a = Integer.parseInt(Text[4]);
	   
	   return a;
   }
   

   // 차일드뷰 각각의 ROW
   @Override
   public View getChildView(int groupPosition, int childPosition,
           boolean isLastChild, View convertView, ViewGroup parent) {
        
       View v = convertView;
        
       if(v == null){
           viewHolder = new ViewHolder();
           v = inflater.inflate(R.layout.list_row, null);
           viewHolder.tv_childName = (TextView) v.findViewById(R.id.tv_time);
           viewHolder.tv_childName2 = (TextView) v.findViewById(R.id.tv_child_2);
           viewHolder.tv_childName3 = (TextView) v.findViewById(R.id.tv_child_3);
           viewHolder.tv_childName4 = (TextView) v.findViewById(R.id.tv_child_4);
           
           v.setTag(viewHolder);
       }else{
           viewHolder = (ViewHolder)v.getTag();
       }        
       String tmp_String = getChild(groupPosition, childPosition);

       
       if(!tmp_String.equals("값없음#값없음#값없음#값없음")){
           String[] Text=tmp_String.split("#");
    	   viewHolder.tv_childName.setText(Text[0]);
           viewHolder.tv_childName2.setText(Text[1]);
           viewHolder.tv_childName3.setText(Text[2]);
           viewHolder.tv_childName4.setText(Text[3]);
          // viewHolder.tv_childName4.setText("￦"+df.format(Integer.parseInt(Text[3])));
   
       }else{
    	   viewHolder.tv_childName.setText("");
           viewHolder.tv_childName2.setText("");
           viewHolder.tv_childName3.setText("");
           viewHolder.tv_childName4.setText("");
           v.setTag(viewHolder);
    	  
       }
       
       return v;
   }

   @Override
   public boolean hasStableIds() { return true; }

   @Override
   public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }
    
   class ViewHolder {
       public TextView Total_money;
       public TextView tv_groupName;
       public TextView tv_childName;
       public TextView tv_childName2;
       public TextView tv_childName3;
       public TextView tv_childName4;
   }

}