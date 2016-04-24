package net.pocketmagic.android.eventinjector4;

import com.tjeannin.apprate.AppRate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;




public class MainActivity extends Activity  {
	public static MediaProjectionManager projectionManager;
	public static MediaProjection projection;
	public static int width, height;
    public static Intent data;      

    private static final String LUNCH_COUNT = "lunch_count";
	private int lunchCount;
	private SharedPreferences sharedPreferences;
    
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				
				
		//setContentView(R.layout.activity_main);
		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		lunchCount = sharedPreferences.getInt(LUNCH_COUNT, 0) + 1;
		sharedPreferences.edit().putInt(LUNCH_COUNT, lunchCount).commit();
		
		new AppRate(this)
		.setMinDaysUntilPrompt(0)
		.setMinLaunchesUntilPrompt(1)
		.setShowIfAppHasCrashed(false)
		.init();
		
		
		 final Window window = this.getWindow();
	        window.setFlags(
	            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     
		
		width = getWindowManager().getDefaultDisplay().getWidth();
		height = getWindowManager().getDefaultDisplay().getHeight();
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;	
    	if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
		projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
		
			startActivityForResult(projectionManager.createScreenCaptureIntent(), 100);		
		
    	}else{
    		//moveTaskToBack(true);
    		
    		if (lunchCount%2 == 1) {
    			setContentView(R.layout.rate);	
            	
			}else{
				startService(new Intent(this,ChatHeadsService.class));
        		finish();  
				
			}    		   			
    	}    	
	}	
	
		
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.i("TAG", "resultCode " + resultCode);
    	if (requestCode == 100) {
    		MainActivity.data = data;
    		projection = projectionManager.getMediaProjection(resultCode, data);
    		Log.i("TAG", "projection " + projection);    		
    		
    	}
    	//moveTaskToBack(true);    	
    	if (lunchCount%2 == 0) {
			setContentView(R.layout.rate);	
        	
		}else{
    	startService(new Intent(this,ChatHeadsService.class));
		finish();  
		}
		
		
    	super.onActivityResult(requestCode, resultCode, data);
    }

	
	
	
}
