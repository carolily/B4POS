package cn.bluedrum.b4pos;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.util.Log;

public class TransDatabase {
	
	
	
	public final static String DEFAULT_DB_PATH = "/sdcard/b4pos/b4pos.db";
	
	public String databaseName = DEFAULT_DB_PATH;
	
	SQLiteDatabase database = null; //���ڴ���SQLite�Ĳ���
	public final static String TAG="transDatabse"; 
	
	public final static String PROCDUCT_NAME="name";
	
	public boolean open(Context context)
	{
		//���������ȡ����
		SharedPreferences sharePref; 
		//ȡ�����ö���
		sharePref =  PreferenceManager.getDefaultSharedPreferences(context);
		
		//����key������������,����key���������ô��ڶ���
		databaseName = sharePref.getString("db_path", DEFAULT_DB_PATH);
		
		
		try{
			if(database == null)
			{//��openDatabase �����ݿⲢ�Ҵ������ݿ���󷵻�
				database = SQLiteDatabase.openDatabase(databaseName, null, 0);
			}
			
			if(!database.isOpen())
			{
				database.openDatabase(databaseName, null, 0);
			}
			
			Log.i(TAG,"open datbase "+databaseName+",isOpen="+database.isOpen());
			
			return database.isOpen();
		}
		catch(Exception e)
		{
			Utils.showMessage(context, e.getMessage()+databaseName);
			e.printStackTrace();
			return false;
		}
	
	}
	
	
	public Cursor queryProduct(String code)
	{
		//����һ����ѯSQL���
		if(database == null)
		{
			Log.i(TAG,"database not open");
			return null;
		}
		String sqlStr = "  select Name,Unit,UnitPrice,BarCode from Products where BarCode=\'"+code+"\';";
		//String sqlStr = "select Name,Unit,Price,Code from Products ";
		
		Cursor result = null;
		
		Log.i(TAG,"queryProce:"+sqlStr);
		
		try{
		 result = database.rawQuery(sqlStr, null);
		}
		catch (SQLiteException e)
		{
			//��ѯ���ݲ����쳣
			e.printStackTrace();
		}
		
		
		return result;
		
		
		
	}
	
	public void close()
	{
		Log.i(TAG,"close "+databaseName);
		database.close();
	}
	
	
			

}
