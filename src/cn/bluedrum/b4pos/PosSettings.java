package cn.bluedrum.b4pos;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PosSettings extends PreferenceActivity {
	 /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.preference);
    }
}
