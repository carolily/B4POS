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
	
	SQLiteDatabase database = null; //用于处理SQLite的操作
	public final static String TAG="transDatabse"; 
	
	public final static String PROCDUCT_NAME="name";
	
	public boolean open(Context context)
	{
		//从配置里读取数据
		SharedPreferences sharePref; 
		//取得配置对象
		sharePref =  PreferenceManager.getDefaultSharedPreferences(context);
		
		//根据key读出配置数据,其中key名字由配置窗口定义
		databaseName = sharePref.getString("db_path", DEFAULT_DB_PATH);
		
		
		try{
			if(database == null)
			{//由openDatabase 打开数据库并且创建数据库对象返回
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
		//构造一个查询SQL语句
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
			//查询数据产生异常
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
