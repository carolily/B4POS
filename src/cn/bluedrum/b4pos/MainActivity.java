package cn.bluedrum.b4pos;


import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity 
 implements android.view.View.OnClickListener{
	private Button btn_hx;
	private Button btn_sgz;
	private Button btn_xc;
	private Button btn_lc;
	private Button btn_zs;
	
	private Button btntaihao;
	private Button btncai_pin;
	private Button btnaaa;
	
	private Button btndatabase;
	
	
	public static TransDatabase database = new TransDatabase();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	  getWindow().requestFeature(Window.FEATURE_NO_TITLE);
  
		setContentView(R.layout.activity_main);
		
		
		btn_hx=(Button)findViewById(R.id.button1);
		btn_sgz=(Button)findViewById(R.id.button2);
		btn_xc=(Button)findViewById(R.id.button3);
		btn_lc=(Button)findViewById(R.id.button4);
		btn_zs=(Button)findViewById(R.id.button5);
		btntaihao=(Button)findViewById(R.id.buttonLeft);
		btncai_pin=(Button)findViewById(R.id.buttonRight);
		btnaaa=(Button)findViewById(R.id.buttonRight1);
		btndatabase=(Button)findViewById(R.id.buttonRight2);
		//为按钮增加监听器
		btn_hx.setOnClickListener(this);
		btn_sgz.setOnClickListener(this);
		btn_xc.setOnClickListener(this);
		btn_lc.setOnClickListener(this);
		btn_zs.setOnClickListener(this);
		btntaihao.setOnClickListener(this);
		btncai_pin.setOnClickListener(this);
		btnaaa.setOnClickListener(this);
		btndatabase.setOnClickListener(this);
		
		btntaihao.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, Show_func.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
			
			
			
		});
		
		
		
		
		
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
    	MenuFragment frag = (MenuFragment) getFragmentManager().findFragmentById(R.id.fragment2);
    	
		int id  = v.getId();
		switch(id)	
		{
		case R.id.button1:
			//把海鲜的菜品放入gridView
			frag.createGridData1(1);
			break;
		case R.id.button2:
			frag.createGridData1(2);
			//把砂锅粥的菜品加入gridView
			break;
		case R.id.button3:
			frag.createGridData1(3);
			//把小炒的菜品加入gridView
			break;
		case R.id.button4:
			frag.createGridData1(4);
			//把凉菜的菜品加入gridView
			break;
		case R.id.button5:
			frag.createGridData1(5);
			//把主食的菜品加入gridView
			break;
			
		case R.id.buttonLeft:
			//InvoiceFragment frag1=(InvoiceFragment)getFragmentManager().findFragmentById(R.id.fragment1);
			//frag1.createListData1();
			
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, Show_func.class);
			startActivity(intent);
		    //startActivity(new Intent(getApplication(),Show_func.class));
		//	break;
		case R.id.buttonRight:
			Intent intent1=new Intent();
			intent1.setClass(MainActivity.this, Cai_pin.class);
			startActivity(intent1);
			 //startActivity(new Intent(getApplication(),Cai_pin.class));
			break;
		case R.id.buttonRight1:
			Intent intent2=new Intent();
			intent2.setClass(MainActivity.this,MinorActivity.class);
			startActivity(intent2);
			break;
		case R.id.buttonRight2:
			Intent intent3 =new Intent();
			intent3.setClass(MainActivity.this, Tables.class);
			startActivity(intent3);
			break;
			
	     default:       
		}
		
	}

	

}
