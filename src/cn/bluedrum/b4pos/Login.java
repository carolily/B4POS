package cn.bluedrum.b4pos;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Login extends  Activity
implements android.view.View.OnClickListener 
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); //»•±ÍÃ‚¿∏ 
     
        
        setContentView(R.layout.login);
        
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
			startActivity(new Intent(getApplication(),MainActivity.class));  
	         finish();  
	         break;
	     default:       
		}
		
	}
     
   
    
  

}
