package net.pocketmagic.android.eventinjector4;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;

import com.a1w0n.standard.Jni.Exec;

public class SurfaceThread extends Activity implements Runnable {

	//private Bitmap bmp;
		
	
	@Override
	public void run() {
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;	
    	if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
    		if (MainActivity.projection != null ) {
    			ChatHeadsService.virtualDisplay = MainActivity.projection.createVirtualDisplay("test"
    					, MainActivity.width
    					, MainActivity.height
    					, DisplayMetrics.DENSITY_DEFAULT, 0, ChatHeadsService.mAssistive_holder.getSurface(), null, null);   		
    		}else {       			
    			// 예기치 않게 종료되었을 경우 새롭게 Main을 실행시킨다.
    			Intent intent = new Intent(this, MainActivity.class);
    			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			startActivity(intent);    			
			}
		}else{		
							
			byte[] aa = new byte[1280 * 720 * 4];	
			int bb = Exec.test(aa);	
			System.out.println(bb);
	        if (aa != null && bb != -1) {				
				ChatHeadsService.bmp = BitmapFactory.decodeByteArray(aa, 0, bb);					
				Log.d("<<bmp.getWidth>>", String.valueOf(ChatHeadsService.bmp.getWidth()));
				Log.d("<<bmp.getHeight>>", String.valueOf(ChatHeadsService.bmp.getHeight()));
				 Bitmap checkbmp = Bitmap.createBitmap(ChatHeadsService.bmp, ChatHeadsService.bmp.getWidth()/3, ChatHeadsService.bmp.getHeight()/3, ChatHeadsService.bmp.getWidth()/3+10, ChatHeadsService.bmp.getHeight()/3+10);
//			        int R1,G1,B1, countthecolor =0 , countthecolor2 = 0;
//			        for (int i = 0; i < 5; i++) {
//						for (int j = 0; j < 5; j++) {
//							int pixel =  checkbmp.getPixel(i,j);
//					        R1 = Color.red(pixel);
//					        G1 = Color.green(pixel);
//					        B1 = Color.blue(pixel);
//
//					        if((R1 == 0) && (G1 == 0) && (B1 == 0)) {
//					            countthecolor = countthecolor + 1;
//					        }
//					        if((R1 == 255) && (G1 == 255) && (B1 == 255)) {
//					            countthecolor2 = countthecolor2 + 1;
//					        }
//					        
//					        
//						}
//					}			      		        
//			        Log.d("countthecolor", String.valueOf(countthecolor));
//			        Log.d("countthecolor2", String.valueOf(countthecolor2));
			        // 버퍼프레임의 가로폭이 세로폭보다 큰 특이한 케이스도 screencap 으로 전환 
//			        if (countthecolor >=40 ||countthecolor2 > 40 ) {
//			        }
//			        else{
						ChatHeadsService.bmp = changeColor(ChatHeadsService.bmp,0);
						ChatHeadsService.bmp = ScaledBitmap(ChatHeadsService.bmp);
						//ChatHeadsService.surfaceview.setBackground(new BitmapDrawable(ChatHeadsService.bmp));
						ChatHeadsService.surfaceview.setBackgroundDrawable(new BitmapDrawable(ChatHeadsService.bmp));
//					}
	        }else{
	        	
	        	
				
	        	try {			
	    				
	        		ChatHeadsService.sh = Runtime.getRuntime().exec("su", null,null);
	    			OutputStream  os = ChatHeadsService.sh.getOutputStream();
	    			DataOutputStream dos = new DataOutputStream(os);
	    			dos.write(("/system/bin/screencap -p " + "/sdcard/img.png").getBytes("ASCII"));
	    			dos.flush();
	    			dos.close();		
	    			
	    			ChatHeadsService.sh.waitFor();	    	
	    			
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}   	
	    		    	    	    
	    		String imageInSD = Environment.getExternalStorageDirectory().toString() + "/img.png";
	    		ChatHeadsService.bmp = BitmapFactory.decodeFile(imageInSD);
	    		//ChatHeadsService.bmp = Bitmap.createScaledBitmap(ChatHeadsService.bmp,ChatHeadsService.surfaceview.getWidth()/2, ChatHeadsService.surfaceview.getHeight()/2,true);
	    		ChatHeadsService.bmp = Bitmap.createBitmap(ChatHeadsService.bmp, 0, 0, ChatHeadsService.surfaceview.getWidth(), ChatHeadsService.surfaceview.getHeight());
	    		
	    		/*ChatHeadsService.layoutlp.width = 300;
	    		ChatHeadsService.mWindowManager.updateViewLayout(ChatHeadsService.mAssistiveView, ChatHeadsService.mParams2);*/
	    		//ChatHeadsService.surfaceview.setLayoutParams(ChatHeadsService.mParams2);
	    					    		
	    		//ChatHeadsService.surfaceview.setBackground(new BitmapDrawable(ChatHeadsService.bmp));
	    		ChatHeadsService.surfaceview.setBackgroundDrawable(new BitmapDrawable(ChatHeadsService.bmp));
	    		
	        	
	        	
	        }
		}
 }			       	
	//}	
	
	public Bitmap ScaledBitmap(Bitmap bmp){
		ChatHeadsService.rotation_s = ChatHeadsService.mWindowManager.getDefaultDisplay().getRotation();
		Log.d("bmp.getHeight", String.valueOf(bmp.getHeight()));
		Log.d("ChatHeadsService.surfaceview.getHeight()", String.valueOf(ChatHeadsService.surfaceview.getHeight()));
		
    	bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), ChatHeadsService.surfaceview.getHeight());				
        if (ChatHeadsService.rotation_s == Surface.ROTATION_90) {			
        	Matrix matrix = new Matrix();        	
        	matrix.postRotate(270);			
        	Bitmap sbmp = Bitmap.createScaledBitmap(bmp,ChatHeadsService.surfaceview.getWidth()/2, ChatHeadsService.surfaceview.getHeight()/2,true);
        	bmp = Bitmap.createBitmap(bmp , 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);        	        	
		}else if (ChatHeadsService.rotation_s == Surface.ROTATION_270) {
			Matrix matrix = new Matrix();			
			matrix.postRotate(90);			
			Bitmap sbmp = Bitmap.createScaledBitmap(bmp,ChatHeadsService.surfaceview.getWidth()/2, ChatHeadsService.surfaceview.getHeight()/2,true);
			bmp = Bitmap.createBitmap(bmp , 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);    
		}
        
        return bmp; 	
    }
	// Bitmap 색상값 변경하기
    public static Bitmap changeColor(Bitmap bmpOriginal, float degree) {
	    Bitmap bmp = Bitmap.createBitmap(bmpOriginal.getWidth(),
	            bmpOriginal.getHeight(), Config.ARGB_8888);
	  
	    Canvas c = new Canvas(bmp);
	    Paint paint = new Paint();	    
	    
	    float[] colorTransform = {
	            0, 0, 1f, 0, 0, 
	            0, 1f, 0, 0, 0,
	            1f, 0, 0, 0, 0, 
	            0, 0, 0, 1f, 0};

	    ColorMatrix colorMatrix = new ColorMatrix();
	    colorMatrix.setSaturation(0f); //Remove Colour 
	    colorMatrix.set(colorTransform); //Apply the Red

	    ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
	    
	    paint.setColorFilter(colorFilter);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmp;
	}
}
