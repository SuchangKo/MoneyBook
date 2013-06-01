package com.suchangko.moneybook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class FavorSpendDB {
   private DBHelper mHelper;
   private SQLiteDatabase db;
   private static final String Database_Name="favorspenddb";
   private static final int Database_Version = 2;
   private static String SQL_Table_Create;
   private static String Table_Name;
   public static final String SQL_DBname="favorspenddb";
   public static final String SQL_Create_favorspenddb =
           "create table favorspenddb (_id integer primary key autoincrement,"
                   +"content text,"
                   +"memo text,"
                   +"money integer not null,"
                   +"kindof text,"
                   +"moneykindof text"
                   +")";
   private final Context cxt;

   private static class DBHelper extends SQLiteOpenHelper {
       DBHelper(Context context) {
           super(context,Database_Name,null,Database_Version);
       }
       @Override
       public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_Create_favorspenddb);
       }
       @Override
       public void onOpen(SQLiteDatabase db) {
           super.onOpen(db);
       }
       @Override
       public void onUpgrade(SQLiteDatabase db, int i, int i2) {
           db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
           onCreate(db);
       }
   }
    public FavorSpendDB(Context cxt, String sql, String tname){
        this.cxt=cxt;
        SQL_Table_Create=sql;
        Table_Name=tname;
    }
    public FavorSpendDB open() throws SQLiteException{
        mHelper = new DBHelper(cxt);
        db = mHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        mHelper.close();
    }
    public void datadel(String id){
    	db.execSQL("DELETE FROM "+Database_Name+" WHERE _id="+id);
    }
    public long insertTable(ContentValues values) {
        return db.insert(Table_Name, null, values);
    }
    public boolean deleteTable(String pkColumn, long pkData) {
        return db.delete(Table_Name, pkColumn+"="+pkData, null)>0;
    }
    public Cursor RawQueryString(String sql){
    	return db.rawQuery(sql,null);
    }
    public Cursor selectTable(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(Table_Name, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
    public boolean updateTable(ContentValues values, String pkColumn, long pkData) {
        return db.update(Table_Name, values, pkColumn+"="+pkData, null)>0;
    }
}
