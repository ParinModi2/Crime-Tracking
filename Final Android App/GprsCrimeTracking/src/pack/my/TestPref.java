package pack.my;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class TestPref extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	private void Init()
	{
		SharedPreferences preferences=getSharedPreferences("gpscrime.xml", MODE_PRIVATE);
		Editor editor= preferences.edit();
		editor.putInt("highscore", 100);
		editor.commit();
		
		preferences.getInt("highscore", 0);
	}
	

}
