package net.pocketmagic.android.eventinjector4;

/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;*/
/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;*/
/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;*/
import java.util.Timer;
import java.util.TimerTask;

import net.pocketmagic.android.eventinjector4.Events.InputDevice;
import quicklic.quicklic.servicecontrol.FinishService;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.Contacts.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.A1w0n.androidcommonutils.CMDUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


@SuppressLint("ServiceCast")
public class ChatHeadsService extends Service implements SurfaceHolder.Callback{
    int lastAction=0;
    ImageView mImageView;
    private String TAG = "APP";
    
    private WindowManager.LayoutParams mParams;
    public static WindowManager.LayoutParams mParams2;
    private WindowManager.LayoutParams mParams3;
    
    public static WindowManager mWindowManager;  
    private ChatHeadsView mChatHeadsView; 
    public static VirtualDisplay virtualDisplay;
   	MediaProjectionManager projectionManager;
   	public static MediaProjection projection;
    public static SurfaceView surfaceview, reverse_surfaceview; 
    public static SurfaceHolder mAssistive_holder;
    private float mTouchX, mTouchY;
    private int mViewX, mViewY;
    Button bt1,bt2,bt3,bt4,bt5,bt6;
    Button hidden, quit, back, angle, icon_view, favorite_view, scale;
    ImageView iv_arrow;
    public static View mAssistiveView, mReverseView;
    boolean img_arrowisclicked=false;
    private LayoutInflater inflater;
   
    private float px, py;    
	
    private Events events = new Events();
    
    public static int rotation_s, rotation_e;
    public static int touch_id, touch_key;
    public static Drawable iv;
    
    public static Bitmap bitmap, bitmap2, bmp;
    public static Process sh = null;
    
    private boolean flag =true;
    private boolean backturn = false;
    private boolean angle_flag = false ;
	 Animation anim;
	 
	public static android.view.ViewGroup.LayoutParams avvglp,buttonslp,adviewlp,layoutlp, scalelp;
	private FrameLayout fl;
	private LinearLayout ll;
	private LinearLayout sl;
	
	
	private TextView xAxisLabel;
	private TextView yAxisLabel;
	private TextView zAxisLabel;
	public static SensorManager mSensorManager;
	private SensorEventListener mEventListenerOrientation;
	private float xAxis;
	private float yAxis;
	private float zAxis;
	public static boolean mChatView_flag, mReverseView_flag = false; // mWindowManager 객체에 붙은 View의 상태를 알려주기 위함.
	private boolean mAssistiveView_flag = false;
	private boolean upside_flag= true;	
	private boolean upside_flag_com = true;
	private boolean icon_view_flag ;
	public static boolean hidden_flag = false;
	private int once_count = 0;
	private Object lock = new Object();		
	private int bb;
	private int adview_height;
	public static Context main_context;
	private static final int NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	    
	
	private static Context context;
	private int deviceWidth;
	private int deviceHeight;
	private int deviceHorizontalCenter;
	private int deviceVerticalCenter;
	private int imageWidth;
	private int imageHeight;
	private int scale_height;
	private int windowHeight;

	private static boolean moveToSide;
	
	private boolean isDoubleClicked = false;
	private boolean isMoved = false;
	private Timer timer;
	private long lastPressTime;
	
	private static final int DOUBLE_PRESS_INTERVAL = 300;
	private static final int LIMITED_MOVE_DISTANCE = 1000;
	private static final long ANIMATION_DURATION = 100;
	
	private int floatings = 0;
	private int layout = 0;
	private boolean layout_flag = true;
	private boolean onoff = false;
	
	private static final String LUNCH_COUNT = "lunch_count";
	private int lunchCount;
	private SharedPreferences sharedPreferences;
	
	private LinearLayout mScaleLyt;
	
	private  InterstitialAd mInterstitialAd;
	
	private void quicklicNotification()
	{
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, FinishService.class), Intent.FLAG_ACTIVITY_NEW_TASK);

		Notification notification = new NotificationCompat.Builder(getApplicationContext()).setContentTitle(getString(R.string.app_name))
				.setContentText(getResources().getString(R.string.stop_quicklic)).setSmallIcon(R.drawable.ic_launcher).setTicker(getResources().getString(R.string.hello_quicklic)).setOngoing(true)
				.setContentIntent(intent).build();
		notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_NO_CLEAR;

		notificationManager.notify(NOTIFICATION_ID, notification);
		startForeground(NOTIFICATION_ID, notification);
	}
	
    
    /*private OnTouchListener mViewTouchListener2 = new OnTouchListener() {
        
        @Override public boolean onTouch(View v, MotionEvent event) {
        	int nowAction = event.getAction();
            
            if(event.getAction()==MotionEvent.ACTION_MOVE){
            	int x = (int)(event.getRawX() - mTouchX);  
                int y = (int)(event.getRawY() - mTouchY);  
                 
         
                mParams.x = mViewX + x;
                mParams.y = Math.abs(mViewY - y);
                 
                mWindowManager.updateViewLayout(mAssistiveView, mParams);    
                Log.d("LOG","Move");
                lastAction=nowAction;
                return true;
            }else if(event.getAction()==MotionEvent.ACTION_CANCEL){
            	Log.d("LOG","Cancel");
            	lastAction=nowAction;
            	return true;
            }else if(event.getAction()==0){
            	Log.d("LOG","false Action : "+event.getAction());
            	int x = (int)(event.getRawX() - mTouchX);  
                int y = (int)(event.getRawY() - mTouchY);
                Log.d("XY","x:"+x+" y:"+y);
            	lastAction=nowAction;
            	return false;
            }else{
            	boolean returnbool;
            	if(lastAction==0){
            		returnbool=false;
            	}else{
            		returnbool=true;
            	}
            	Log.d("LOG","true Action : "+event.getAction());
            	lastAction=nowAction;
            	if(img_arrowisclicked){
            		mAssistiveView.setOnTouchListener(null);
            		img_arrowisclicked=false;
            	}
            	return returnbool;
            }
        }    
    };*/
    
    // mAssistiveView 보이기
	
	private void mChatViewClicked(){
		
		
		if (icon_view_flag && mChatView_flag) {
			mChatView_flag = false;
			mWindowManager.removeView(mChatHeadsView);
		}
		
		
		if (once_count == 0) {
			mWindowManager.addView(mAssistiveView, mParams2);
			mAssistiveView_flag = true;
			once_count++;
		}else{						
			mWindowManager.updateViewLayout(mAssistiveView, mParams2);
			mParams2.alpha = (float)1;
			// 내용물의 높이값을 가져옴
			adviewlp.height = 	adview_height;	
			buttonslp.height = quit.getHeight();
			surfaceExecuted(mAssistive_holder);
										
		}			
		hidden_flag = false;	
		slideUp(fl);
		//TimeOut();
		
	}
		
		/*This will handle the first time call*/
	
		public void slideUp(final View fl){
			
			Handler handler2 = new Handler(); 
			handler2.post(new Runnable(){ 
				public void run(){		
					
					if (mAssistiveView_flag) {		
						buttonslp.height = ll.getHeight();	
//						avvglp.height = surfaceview.getHeight();	
						
						avvglp.height = scale_height;
						System.out.println("first scale : " + String.valueOf(scale_height));
						mWindowManager.updateViewLayout(mAssistiveView, mParams2);	
					}else{
						buttonslp.height = ll.getHeight();	
						avvglp.height = scale_height;
						mWindowManager.addView(mAssistiveView,mParams2);
					}					
				}					
				});
			
			
		}		
		
public void TimeOut(){
			
			//avvglp.height = 0;
			new Runnable(){
				
			
				public void run() {
					float startTimer = System.nanoTime();
					float endTimer = 0;	
					while(true){		
						endTimer = System.nanoTime();
						Log.d("IntervalTimer", String.valueOf((endTimer - startTimer) / 1000000));
						if (hidden_flag || ((endTimer - startTimer) / 1000000) >= 3000) {	
							
							hidden_flag = false;
							// mAssistiveView가 사라져야 Sensor로 update가능하도록함
							upside_flag_com = true;
							
							mParams2.alpha = (float)0;
							mParams2.y = 0;
							
							avvglp.height = 1;
							buttonslp.height = 0;
							adviewlp.height = 1;
							mWindowManager.updateViewLayout(mAssistiveView, mParams2);	
							
							
							if (icon_view_flag && !mChatView_flag) {
								mWindowManager.addView(mChatHeadsView, mParams);
								mChatView_flag = true;	
							}else if(!icon_view_flag && mChatView_flag){
								mWindowManager.removeView(mChatHeadsView);
								mChatView_flag = false;
							}
							
							int currentapiVersion = android.os.Build.VERSION.SDK_INT;	
					    	if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP && ChatHeadsService.virtualDisplay != null) {
							ChatHeadsService.virtualDisplay.release();
					    	}						
							
					    	break;
						}							
					}		
					
				}
			}.run(); 
}
		   
		
    @Override
    public IBinder onBind(Intent arg0) { return null; }
        
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);		
		rotation_s = mWindowManager.getDefaultDisplay().getRotation();
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;	
    	if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP && ChatHeadsService.virtualDisplay != null) {
		if (rotation_s != (Surface.ROTATION_0)) {						
			
			mParams2.alpha = (float)0;
			mParams2.y = 0;
			
			avvglp.height = 1;
			buttonslp.height = 0;
			
			if (mAssistiveView_flag) {
				mWindowManager.updateViewLayout(mAssistiveView, mParams2);
			}			
			if (mChatView_flag) {
				mWindowManager.removeView(mChatHeadsView);
				mChatView_flag = false;
			}
			
			
			ChatHeadsService.virtualDisplay.release();
	    	
				
		}else{			
			if (!mChatView_flag) {
				mChatView_flag = true;
				mWindowManager.addView(mChatHeadsView, mParams);
			}		
		}
		
    	}
		
	}
//    private void requestNewInterstitial() {
//        AdRequest adRequest = new AdRequest.Builder()
////                  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                  .build();
//
//        mInterstitialAd.loadAd(adRequest);
//    }
    
	@Override
    public void onCreate() {
        super.onCreate();           
        
       
		
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-4604217545314757/1829563020");
//        
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                requestNewInterstitial();
//                
//            }
//        });

//        requestNewInterstitial();
        
       // main_context = getApplicationContext();
        quicklicNotification();
        
        
//        try {
        	CMDUtils.runWithRoot("chmod 777 /dev/graphics/fb0");
//			Runtime.getRuntime().exec("su -c setenforce 0", null,null);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
      	
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);  
        
        Events.intEnableDebug(1);
        events.Init();
        
        for (InputDevice idev:events.m_Devs) {          	
        	
        		if (idev.Open(true)) {        			
        			// inform user
        			String name = idev.getName();
                	if (name.equals("touch_dev") | name.contains("touchscreen") | name.contains("synaptics") | name.contains("Touchscreen")         			
                			| name.contains("atmel")	) {        			
        				touch_id = idev.getId();
        				Log.d("LOG",String.valueOf(touch_id));
        			}
                	if (name.equals("sec_touchkey")) {
						touch_key = idev.getId();
					}
                	
                	Log.d("LOG",String.valueOf(touch_id));
                	
        		} else {
        			Toast.makeText(this, "Device failed to open. Do you have root?", Toast.LENGTH_SHORT).show();
        		}
        		Log.d("LOG",String.valueOf(touch_id));      	
        }
              
		inflater  = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);		
				
		// mAssistiveView, mChatHeadsView 설정하기			
		mAssistiveView=(LinearLayout)inflater.inflate(R.layout.activity_main,null); 
		final AdView ad = (AdView) mAssistiveView.findViewById(R.id.adview);
		AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();
		ad.loadAd(adRequest);		
						
		fl = (FrameLayout)mAssistiveView.findViewById(R.id.frameview);
		ll = (LinearLayout) mAssistiveView.findViewById(R.id.buttons);	
		
		surfaceview =(SurfaceView) mAssistiveView.findViewById(R.id.surfaceview);	
			
		
		avvglp = fl.getLayoutParams();
		avvglp.height = 0;
		
		adviewlp = ad.getLayoutParams();
		adview_height = adviewlp.height;
		//  키보드를 가려서 버튼 모음 layoutparams도 만듬.
		buttonslp = ll.getLayoutParams();		
		
		surfaceview.setOnTouchListener(new View.OnTouchListener() {
					
			@Override
			public synchronized boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
								
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					
					
					  
					px = event.getX();
					py = event.getY();
					if(events.m_Devs.size() != 0){
					events.m_Devs.get(touch_id).SendTouchAbsCoord((int)px,(int)py);
					
					}
//					Toast.makeText(getApplicationContext(), "px : "+(int)px+" py : "+(int)py, Toast.LENGTH_SHORT).show();			
					mAssistiveView_flag = true;		
					
					// 5초 이내라도 hidden_flag을 true로 만든다
					hidden_flag = true;
					
					mParams2.alpha = (float)0;
					avvglp.height = 1;
					buttonslp.height = 0;
					adviewlp.height = 1;
					
					mWindowManager.updateViewLayout(mAssistiveView, mParams2);	
					if (icon_view_flag && !mChatView_flag) {
						mWindowManager.addView(mChatHeadsView, mParams);
						mChatView_flag = true;	
					}	
					
					// mAssistiveView가 사라져야 Sensor로 update가능하도록함
					upside_flag_com = true;
					
					int currentapiVersion = android.os.Build.VERSION.SDK_INT;	
			    	if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP && ChatHeadsService.virtualDisplay != null) {
					ChatHeadsService.virtualDisplay.release();
			    	}
//					new android.os.Handler().postDelayed(
//						    new Runnable() {
//						        public void run() {
//						        	 if (mInterstitialAd.isLoaded()) {
//						                    mInterstitialAd.show();
//						                } else {
////						                    beginPlayingGame();
//						                }
//						        }
//						    }, 
//						300);
			    	 
			    	  
				}
				
				return false;			
			}
		});
		mAssistive_holder = surfaceview.getHolder();			
		mAssistive_holder.addCallback(this);
				
		// 서비스 종료하기
		 quit = (Button)mAssistiveView.findViewById(R.id.quit);
		 quit.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub					
					Toast.makeText(getApplicationContext(), "QUIT", Toast.LENGTH_SHORT).show();		
					
					mSensorManager.unregisterListener(mEventListenerOrientation);
					
					mWindowManager.removeView(mAssistiveView);		
					mAssistiveView_flag = false;
					Intent intent;
					intent = new Intent(getApplicationContext(), ChatHeadsService.class);
					stopService(intent);	
					
					int currentapiVersion = android.os.Build.VERSION.SDK_INT;	
			    	if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
					if (MainActivity.projection != null) {
						MainActivity.projection.stop();
					}
			    	}
					
				}
			});
//		favorite_view = (Button)mAssistiveView.findViewById(R.id.favorite_view);
//		 favorite_view.setOnClickListener(new OnClickListener() {			
//				@Override
//				public void onClick(View v) {
//				/*	Intent apk = new Intent(getApplicationContext(), ApkListActivity.class);
//					apk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					apk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					//apk.putExtra("page", getViewPager().getCurrentItem());
//					startActivity(apk);*/
//				
//					surfaceview.setBackgroundResource(R.layout.favorite);
//					
//					
//				}
//			});
		 
		 
			final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	        final SharedPreferences.Editor editor = settings.edit();
	        
	        icon_view_flag = settings.getBoolean("icon_view_flag", true);
	        editor.putBoolean("angle_flag", false);
	        editor.apply();
	        angle_flag = settings.getBoolean("angle_flag", true);
	        
	        if (!icon_view_flag && !angle_flag) {
	        	editor.putBoolean("angle_flag", true);
		        editor.apply();
			}
	        
	        Resources r = getResources();
	        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics());	        
	        scale_height = settings.getInt("scalelp", (int)px);
	        
	        System.out.println("surfaceview height : " + String.valueOf(surfaceview.getHeight()));
	        System.out.println("scale_height : " + String.valueOf(scale_height));
	        avvglp.height = scale_height;
	        
		// 반사화면 아래로 숨기기
        hidden = (Button)mAssistiveView.findViewById(R.id.hidden);
        hidden.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
								
				
				if (layout == 0) {
					ll.setAlpha((float) 0.8);
				}else if (layout == 1) {
					ll.setAlpha((float) 0.5);
				}else if (layout == 2) {
					ll.setAlpha((float) 0.1);
				}else{
					ll.setAlpha((float)1.0);
				}
				
				if (layout == 3) {
					layout = 0;
				}else{
					layout++;
				}		
				
				return true;
				
			}
		});
        hidden.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
								
				// 5초 일지라도 hidden_flag을 true로 만든다
				hidden_flag = true;
				
				
				// mAssistiveView가 사라져야 Sensor로 update가능하도록함
				upside_flag_com = true;
				
				mParams2.alpha = (float)0.5;
				mParams2.y = 0;
				
				avvglp.height = 1;
				buttonslp.height = 0;
				adviewlp.height = 1;
				
				mWindowManager.updateViewLayout(mAssistiveView, mParams2);	
				
				
				if (settings.getBoolean("icon_view_flag", true) && !mChatView_flag) {
					mWindowManager.addView(mChatHeadsView, mParams);
					mChatView_flag = true;	
				}else if(!settings.getBoolean("icon_view_flag", true) && mChatView_flag){
					mWindowManager.removeView(mChatHeadsView);
					mChatView_flag = false;
				}
						
				int currentapiVersion = android.os.Build.VERSION.SDK_INT;	
		    	if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP && ChatHeadsService.virtualDisplay != null) {
				ChatHeadsService.virtualDisplay.release();
		    	}
		    	
			}
		});
        
        String deviceName = getDeviceName();
        if (!deviceName.contains("Nexus")) {			
        	back = (Button)mAssistiveView.findViewById(R.id.back);
            back.setOnClickListener(new OnClickListener() {			
    			@Override
    			public void onClick(View v) {
    		
    				events.m_Devs.get(touch_key).SendTouchBack();	
    				events.m_Devs.get(touch_id).SendTouchBack();	
    				   				
    			}
    		});
		}    
        
       windowHeight = mWindowManager.getDefaultDisplay().getHeight();
        
//       scale = (Button)mAssistiveView.findViewById(R.id.scale);        
//       scale.setOnTouchListener(new OnTouchListener() {
		
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//			int nowAction = event.getAction();
//			 scalelp = fl.getLayoutParams();
//			 
//			 int x = (int)(event.getRawX() - mTouchX);  
//             int y = (int)(event.getRawY() - mTouchY); 
//             
//            if(event.getAction()==MotionEvent.ACTION_MOVE){         
////                mParams.x = mViewX + x;
////                mParams.y = Math.abs(mViewY - y);
//                 
////                mWindowManager.updateViewLayout(mAssistiveView, mParams);  
//                                
////                Log.d("LOG",String.valueOf((float)(windowHeight - y) / (float)windowHeight) * 100);
//                
//                if( ((float)(windowHeight - y) / (float)windowHeight) * 100 > 80){
//                	Resources r = getResources();
//        	        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics());
//        	        scalelp.height = (int)px;
//                }else{
//                	 scalelp.height = mWindowManager.getDefaultDisplay().getHeight() - y;
//                }
//               
//               
//                mWindowManager.updateViewLayout(mAssistiveView, mParams2);	
////                lastAction=nowAction;
//                
//                
//                
//                return true;
//            }
//            
//            if(event.getAction()==MotionEvent.ACTION_UP){
            	
//            	scalelp.height
//            	 if( (((float)(windowHeight - y) / (float)windowHeight) * 100) > 80){
//                 	Resources r = getResources();
//         	        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics());
//         	        scalelp.height = (int)px;
//         	       Toast.makeText(getApplicationContext(), String.valueOf((((float)(windowHeight - y) / (float)windowHeight) * 100)), Toast.LENGTH_SHORT).show();
//                 }else{
            	
//            	Resources r = getResources();
//    	        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics());	        
//    	        scale_height = settings.getInt("scalelp", (int)px);
//    	        
//                 	 scalelp.height = mWindowManager.getDefaultDisplay().getHeight() - y;
////                 }
                 	
            	
//            	scale_height = mWindowManager.getDefaultDisplay().getHeight() - y;
//            	editor.putInt("scalelp", scalelp.height);
//     	        editor.apply();
//            	Toast.makeText(getApplicationContext(), String.valueOf(settings.getInt("scalelp", 0)), Toast.LENGTH_SHORT).show();
//            }
//			return false;
//		}
//	});
            
       angle = (Button)mAssistiveView.findViewById(R.id.angle);
       angle.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				editor.putFloat("xAxis", xAxis);
				editor.apply();
				Toast.makeText(getApplicationContext(), Math.round(Math.abs((float)settings.getFloat("xAxis", 7f)*10))+"˚", Toast.LENGTH_LONG).show();
			}
		});        
        
       angle.setOnLongClickListener(new OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			if (settings.getBoolean("angle_flag", true)) {
				editor.putBoolean("angle_flag", false);
				editor.apply();
				showToast("angle inactivation");
				if (!settings.getBoolean("icon_view_flag", true)) {
					editor.putBoolean("icon_view_flag", true);
					editor.apply();
					icon_view_flag = settings.getBoolean("icon_view_flag", true);
				}
				
			}else if(!settings.getBoolean("angle_flag", true)){
				editor.putBoolean("angle_flag", true);
				editor.apply();
				showToast("angle activation");
			}
			
			
			
			
			return true;
		}
	});
       
       mChatHeadsView = new ChatHeadsView(this);     
        mChatHeadsView.setBackgroundResource(R.drawable.floating1);
        mChatHeadsView.setOnTouchListener(mViewTouchListener1); 
       mChatHeadsView.setOnClickListener(clickListener);
       mParams = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE, //TouchEvent 받음     
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //KeyEvent, TouchEvent를 Child(앱)로 넘김
            PixelFormat.TRANSLUCENT );  
              
       mParams2 = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE, //TouchEvent 받음     
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //KeyEvent, TouchEvent를 Child(앱)로 넘김
                PixelFormat.TRANSLUCENT ); 
       
       try {
   		displayMetrics();
   	} catch (RemoteException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
       
       mParams.windowAnimations = android.R.style.Animation_Dialog;
       mParams.gravity = Gravity.TOP | Gravity.LEFT;
       mParams.x = deviceHorizontalCenter;
       mParams.y = deviceVerticalCenter;
       mParams.width = mChatHeadsView.getWidth();
       mParams.height = mChatHeadsView.getHeight();
                     
       // mParams.gravity = Gravity.BOTTOM;
        mParams2.gravity = Gravity.BOTTOM;
                
        mParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        mParams2.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
       
        if (settings.getBoolean("icon_view_flag", true)) {            
        	mWindowManager.addView(mChatHeadsView, mParams);      
           	mChatView_flag = true;
 		}    
     	
       	
        icon_view = (Button)mAssistiveView.findViewById(R.id.icon_view);
        icon_view.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				
				if (settings.getBoolean("icon_view_flag", true)) {
 					editor.putBoolean("icon_view_flag", false); 	
 					editor.apply(); 					
 					icon_view_flag = settings.getBoolean("icon_view_flag", true);
 					Toast.makeText(getApplicationContext(), "icon inactivation", Toast.LENGTH_SHORT).show();
 					
 					if (!settings.getBoolean("angle_flag", true)) {
 						editor.putBoolean("angle_flag", true); 	
 	 					editor.apply();
					}
 					
 				}else if(!settings.getBoolean("icon_view_flag", true)){
 					editor.putBoolean("icon_view_flag", true);
 					editor.apply();			
 					icon_view_flag = settings.getBoolean("icon_view_flag", true);
 					Toast.makeText(getApplicationContext(),"icon activation", Toast.LENGTH_SHORT).show();
 				}
				 
				floatings--;
				
				
				return false;
			}
		});
        icon_view.setOnClickListener(new OnClickListener() {			
 			@Override
 			public void onClick(View v) {		 				 				
 				
 				if (floatings == 0) {
 					mChatHeadsView.setBackgroundResource(R.drawable.floating2);
 					
				}else if (floatings == 1) {
					mChatHeadsView.setBackgroundResource(R.drawable.floating3);
				
				}else if (floatings == 2) {
					mChatHeadsView.setBackgroundResource(R.drawable.floating4);
					
				}else if (floatings == 3) {
					mChatHeadsView.setBackgroundResource(R.drawable.floating5);
				
				}else if (floatings == 4) {
					mChatHeadsView.setBackgroundResource(R.drawable.floating6);
				
				}else if (floatings == 5) {
					mChatHeadsView.setBackgroundResource(R.drawable.floating1);
					
				}
 				
 				
 				if (floatings == 5) {
					floatings = 0;
				}else{
 				floatings++;
				}
 			}
 		});
        
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);        
        mEventListenerOrientation = new SensorEventListener(){
        	
        	@Override
        	public void onAccuracyChanged(Sensor sensor, int accuracy){        		
        	}
			@Override
			public void onSensorChanged(SensorEvent event){				
				float[] values = event.values;
				xAxis = values[0];
				yAxis = values[1];
				zAxis = values[2];				
				if ((settings.getBoolean("angle_flag", true)) 
						&&  ((xAxis >=  settings.getFloat("xAxis", 5f) || xAxis <= -settings.getFloat("xAxis", 5f)))							
						&& upside_flag  && upside_flag_com
						&& onoff == false  ) {
					onoff = true;
					upside_flag = false;
					// mAssistiveView가 실행되고 있는지 확인
					upside_flag_com = false;
						if (settings.getBoolean("icon_view_flag", true) && mChatView_flag) {
							mChatView_flag = false;
							mWindowManager.removeView(mChatHeadsView);
						}						
						if (once_count == 0) {
							// 한번만 붙이고 계속 업데이트함
							mWindowManager.addView(mAssistiveView, mParams2);
							mAssistiveView_flag = true;
							once_count++;
							
							//TimeOut();
							
						}else{					
							mParams2.alpha = (float)1;
							// 내용물의 높이값을 가져옴
							adviewlp.height = 	adview_height;	
							buttonslp.height = quit.getHeight();
														
							surfaceExecuted(mAssistive_holder);
							if (mAssistiveView_flag) {
								mWindowManager.updateViewLayout(mAssistiveView, mParams2);
							}																	
						}										
						slideUp(fl);												
						//TimeOut();
				}else if( (xAxis <  settings.getFloat("xAxis", 5f) || xAxis > -settings.getFloat("xAxis", 5f))						
							&& !upside_flag && upside_flag_com
							&& onoff == true){
							onoff = false;
							upside_flag = true;
				}
			}
        };        
        mSensorManager.registerListener(mEventListenerOrientation, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_FASTEST);
       
    }	
	
	private void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	private void surfaceExecuted(SurfaceHolder holder){
		
		SurfaceThread st = new SurfaceThread();
		st.run();
		
	}	
	
    @Override
	public void surfaceCreated(SurfaceHolder holder) {   
    	  	    	    	
    	rotation_s = mWindowManager.getDefaultDisplay().getRotation();
    	
    	int currentapiVersion = android.os.Build.VERSION.SDK_INT;	
    	if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
    		if (MainActivity.projection != null ) {
        		virtualDisplay = MainActivity.projection.createVirtualDisplay("test"
    					, MainActivity.width
    					, MainActivity.height
    					, DisplayMetrics.DENSITY_DEFAULT, 0, holder.getSurface(), null, null);   		
    		}else {    			
    			// 예기치 않게 종료되었을 경우 새롭게 Main을 실행시킨다.
    			Intent intent = new Intent(this, MainActivity.class);
    			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			startActivity(intent);    			
			}
		}else{  	
			
			SurfaceThread st = new SurfaceThread();
			st.run();
				
		}	    	
    }
    
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {		
		if (virtualDisplay != null) {
			 virtualDisplay.release();
		 }				
		
	}	
    
	public String getDeviceName() {
		   String manufacturer = Build.MANUFACTURER;
		   String model = Build.MODEL;
		   if (model.startsWith(manufacturer)) {
		      return capitalize(model);
		   } else {
		      return capitalize(manufacturer) + " " + model;
		   }
		}


		private String capitalize(String s) {
		    if (s == null || s.length() == 0) {
		        return "";
		    }
		    char first = s.charAt(0);
		    if (Character.isUpperCase(first)) {
		        return s;
		    } else {
		        return Character.toUpperCase(first) + s.substring(1);
		    }
		}
		
		
		private void displayMetrics() throws RemoteException
		{
			Display windowDisplay = mWindowManager.getDefaultDisplay();
			DisplayMetrics displayMetrics = new DisplayMetrics();
			windowDisplay.getMetrics(displayMetrics);

			// Device의 Display에서 width와 height 구하기
			deviceWidth = displayMetrics.widthPixels;
			deviceHeight = displayMetrics.heightPixels;
			
			// Device의 Display에서 가운데 위치 구하기
			deviceHorizontalCenter = (deviceWidth - mChatHeadsView.getWidth()) >> 1;
			deviceVerticalCenter = (deviceHeight - mChatHeadsView.getHeight()) >> 1;
			//showToast("deviceWidth : "+ String.valueOf(deviceWidth) +"deviceHeight : "+ String.valueOf(deviceHeight) );
			//showToast("deviceHorizontalCenter : "+ String.valueOf(deviceHorizontalCenter) +"deviceVerticalCenter : "+ String.valueOf(deviceVerticalCenter) );
		}
		
		public static boolean changeMoveToSide()
		{
			if ( moveToSide )
			{
				moveToSide = false;
			}
			else
			{
				moveToSide = true;
			}
			return moveToSide;
		}
		/*public void doubleTouched() throws RemoteException
		{
			boolean mode = changeMoveToSide();
			if ( mode )
				Toast.makeText(context, R.string.quicklic_magnet_mode, Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(context, R.string.quicklic_floating_mode, Toast.LENGTH_SHORT).show();
		}*/
		public void touched() throws RemoteException
		{
			/*Intent intent = new Intent(context, QuicklicMainService.class);
			setFloatingVisibility(false);
			context.startService(intent);*/
		}
		private OnClickListener clickListener = new OnClickListener()
		{
			@SuppressLint("HandlerLeak")
			@Override
			public void onClick( View v )
			{
				try
				{				
					{
						long pressTime = System.currentTimeMillis();

						/* Double Clicked
						 * 현재 누른 시간과 마지막으로 누른 시간을 감산한 결과가
						 * DOUBLE_PRESS_INTERVAL보다 작으면 Double Clicked.
						 */
						if ( (pressTime - lastPressTime) <= DOUBLE_PRESS_INTERVAL )
						{
							//doubleTouched();
							// Double Clicked 인 경우, 핸들러가 실행되도 Single Click 작업 하지 않음.
							isDoubleClicked = true;
						}
						else
						{
							// Single Clicked
							isDoubleClicked = false;

							// Handler 에 Single Click 시 수행할 작업을 등록
							Message message = new Message();
							Handler handler = new Handler()
							{
								public void handleMessage( Message message )
								{
									// Double Clicked 가 아니고 객체의 이동 수행을 하지 않았다면 실행.
									if ( !isDoubleClicked && isMoved == false )
									{
										try
										{
											touched();
										}
										catch (RemoteException e)
										{
											e.printStackTrace();
										}
									}
								}
							};
							// DOUBLE_PRESS_INTERVAL 시간동안 Handler 를 Delay 시킴.
							handler.sendMessageDelayed(message, DOUBLE_PRESS_INTERVAL);
						}
						// 현재 누른 시간을 마지막 누른 시간으로 저장
						lastPressTime = pressTime;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		private OnTouchListener mViewTouchListener1 = new OnTouchListener() {
	           	

	    		private int initialX;
	    		private int initialY;
	    		private float initialTouchX;
	    		private float initialTouchY;
	    		private int moveTouchX;
	    		private int moveTouchY;
	    		
	    		@Override
	    		public boolean onTouch( View v, MotionEvent event )
	    		{
	    		try
	    			{
	    				
	    					switch ( event.getAction() )
	    					{
	    					case MotionEvent.ACTION_DOWN:
	    						isMoved = false;

	    						initialX = mParams.x;
	    						initialY = mParams.y;
	    						initialTouchX = event.getRawX();
	    						initialTouchY = event.getRawY();
	    						//showToast("initialTouchX : " +String.valueOf(initialTouchX)+" / initialTouchY : "+String.valueOf(initialTouchY));
	    						break;

	    					case MotionEvent.ACTION_MOVE:
	    						moveTouchX = (int) (event.getRawX() - initialTouchX);
	    						moveTouchY = (int) (event.getRawY() - initialTouchY);

	    						mParams.x = initialX + moveTouchX;
	    						mParams.y = initialY + moveTouchY;
	    						mWindowManager.updateViewLayout(mChatHeadsView, mParams);   

	    						// 터치 감지 : X와 Y좌표가 10이하인 경우에는 움직임이 없다고 판단하고 single touch 이벤트 발생.
	    						isMoved = true;
	    						//showToast("moveTouchX : " +String.valueOf(moveTouchX)+" / moveTouchY : "+String.valueOf(moveTouchY));
	    						if (Math.abs(moveTouchX) < 10 && Math.abs(moveTouchY) < 10) {
									isMoved = false;
								}    						
	    						break;

	    					case MotionEvent.ACTION_UP:
	    						if (!isMoved )
	    						{
	    							mChatViewClicked();
	    							timer.schedule(new TimerTask()
	    							{
	    								@Override
	    								public void run()
	    								{
	    									// DOUBLE_PRESS_INTERVAL+100 milliseconds 가 지나가면, 다시 클릭 가능해짐. 
	    									
	    									
	    									isMoved = false;
	    								}
	    							}, DOUBLE_PRESS_INTERVAL + 100);
	    							    							
	    						
	    						}
	    						
	    						if ( isMoved )
	    						{
	    							moveToSide();
	    						}
	    						break;
	    					}	    				
	    			}
	    			catch (Exception e)
	    			{
	    				e.printStackTrace();
	    			}
	    			return false;
	    		}

	    		
	    		private void moveToSide()
	    		{
	    			initialX = mParams.x;
	    			initialY = mParams.y;

	    			int toX = mParams.x;
	    			int toY = mParams.y;

	    			if ( mParams.x > deviceHorizontalCenter )
	    			{
	    				if ( mParams.y > deviceVerticalCenter )
	    				{
	    					if ( deviceWidth - (mParams.x + mChatHeadsView.getWidth()) > deviceHeight - (mParams.y + mChatHeadsView.getHeight()) )
	    					{
	    						toY = deviceHeight - mChatHeadsView.getHeight();
	    					}
	    					else
	    					{
	    						toX = deviceWidth - mChatHeadsView.getWidth();
	    					}
	    				}
	    				else
	    				{
	    					if ( deviceWidth - (mParams.x + mChatHeadsView.getWidth()) > mParams.y )
	    					{
	    						toY = 0;
	    					}
	    					else
	    					{
	    						toX = deviceWidth - mChatHeadsView.getWidth();
	    					}
	    				}
	    			}
	    			else
	    			{
	    				if ( mParams.y > deviceVerticalCenter )
	    				{
	    					if ( mParams.x > deviceHeight - (mParams.y + mChatHeadsView.getHeight()) )
	    					{
	    						toY = deviceHeight - mChatHeadsView.getHeight();
	    					}
	    					else
	    					{
	    						toX = 0;
	    					}
	    				}
	    				else
	    				{
	    					if ( mParams.x > mParams.y )
	    					{
	    						toY = 0;
	    					}
	    					else
	    					{
	    						toX = 0;
	    					}
	    				}
	    			}

	    			mParams.x = toX;
	    			mParams.y = toY;

	    			animateFromTo(mChatHeadsView, initialX, initialY, toX, toY);
	    		}       	
	        	
	        }   ; 	   	
		
	
	public void animateFromTo( View v, int fromX, int fromY, final int toX, final int toY )
	{
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowManager.updateViewLayout(mChatHeadsView, mParams);   

		/**
		 * object animator
		 */
		Property<View, Float> xProp = View.X;
		Property<View, Float> yProp = View.Y;

		float[] xFloat = new float[2];
		xFloat[0] = fromX;
		xFloat[1] = toX;

		float[] yFloat = new float[2];
		yFloat[0] = fromY;
		yFloat[1] = toY;

		ObjectAnimator xAnimator = ObjectAnimator.ofFloat(v, xProp, xFloat);
		ObjectAnimator yAnimator = ObjectAnimator.ofFloat(v, yProp, yFloat);

		final AnimatorSet localAnimatorSet = new AnimatorSet();
		localAnimatorSet.setInterpolator(new LinearInterpolator());
		localAnimatorSet.setDuration(ANIMATION_DURATION);

		localAnimatorSet.addListener(new AnimatorListener()
		{
			@Override
			public void onAnimationStart( Animator animation )
			{
				mChatHeadsView.setEnabled(false);
			}

			@Override
			public void onAnimationRepeat( Animator animation )
			{

			}

			@Override
			public void onAnimationEnd( Animator animation )
			{
				mChatHeadsView.setEnabled(true);

				mParams.width = mChatHeadsView.getWidth();
				mParams.height = mChatHeadsView.getHeight();

				mParams.x = toX;
				mParams.y = toY;
				mWindowManager.updateViewLayout(mChatHeadsView, mParams);   

				mChatHeadsView.setX(0);
				mChatHeadsView.setY(0);
			}

			@Override
			public void onAnimationCancel( Animator animation )
			{

			}
		});

		localAnimatorSet.playTogether(xAnimator, yAnimator);
		localAnimatorSet.start();
	}
		
		
}
