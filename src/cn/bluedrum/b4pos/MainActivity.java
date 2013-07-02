package cn.bluedrum.b4pos;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity 
 implements android.view.View.OnClickListener{

	public static TransDatabase database = new TransDatabase();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	  getWindow().requestFeature(Window.FEATURE_NO_TITLE);
  
		setContentView(R.layout.activity_main);
		
		database.open(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 
    
    //响应菜单处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      
    	int id = item.getItemId();
    	
    	
    	
    	switch(id)
    	{
    	case R.id.action_settings:
    		
              startActivity(new Intent(getApplication(),PosSettings.class));
    		break;
    
    	}
    	return true;
    }
    
    @Override
	public void onClick(View v) {
		
		int id  = v.getId();
//		switch(id)
//		{
//	
//	     default:       
//		}
		
	}

	

}
