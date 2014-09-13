package cn.bluedrum.b4pos;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends  Activity
implements android.view.View.OnClickListener 
{
	private static DB_helper mDbHelper;
	private static SQLiteDatabase mdb;
	private EditText edt_username;
	private EditText edt_passwd;
	private Context context;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); //去标题栏 
     
        
        setContentView(R.layout.login);
        edt_username=(EditText)findViewById(R.id.editUsername);
        edt_passwd=(EditText)findViewById(R.id.editPasswd);
        context=(Context)this;
        mDbHelper=new DB_helper(context);
        ((Button)findViewById(R.id.login)).setOnClickListener(this);
        
        
    }
    
    @Override  
	  protected void onDestroy() {  
	      super.onDestroy();  
	  }

	@Override
	public void onClick(View v) {
		
		int id  = v.getId();
		switch(id)
		{
		case R.id.login:
			
			Intent intent=new Intent();
			Bundle bundle=new Bundle();
			bundle.putString("authority","ok");
			intent.putExtras(bundle);
			intent.setClass(Login.this, Show_func.class);
			startActivity(intent);
			//startActivity(new Intent(getApplication(),Show_func.class));  
	         finish();  
		
//			//连接数据库
//			mdb=mDbHelper.getReadableDatabase();
//		
//			
//			//查询表
//			String[] projections=new String[]{"id","username","passwd","authority"};
//			Cursor cur=mdb.query("login_table", projections, null, null, null, null, null);
//			String temp="";
//			//startActivity(new Intent(getApplication(),Show_func.class));
//		
//			if(cur!=null)
//			{
//				if(cur.getCount()==0)
//				{
//					temp="无数据";
//				}
//				while(cur.moveToNext())
//				{
//					//比较
//					if(edt_username.getText().toString().equals(cur.getString(1).toString())&&edt_passwd.getText().toString().equals(cur.getString(2).toString()))//用户名
//					{
//						Intent intent=new Intent();
//						Bundle bundle=new Bundle();
//						bundle.putString("authority", cur.getString(3).toString());
//						intent.putExtras(bundle);
//						intent.setClass(Login.this, Show_func.class);
//						startActivity(intent);
//						//startActivity(new Intent(getApplication(),Show_func.class));  
//				         finish();  	
//				       
//					}else
//					{
//						Toast.makeText(context, "用户名或密码不正确", Toast.LENGTH_SHORT);
//					}
//					
//				}
//				
//			}
//	
	         break;
	     default:    
	
		}
		
	}
     
   
    
  

}
