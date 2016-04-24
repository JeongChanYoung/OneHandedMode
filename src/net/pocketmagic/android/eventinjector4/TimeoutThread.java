package net.pocketmagic.android.eventinjector4;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.A1w0n.androidcommonutils.CMDUtils;
import com.A1w0n.androidcommonutils.debugutils.Logger;
import com.a1w0n.standard.Jni.Exec;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;

public class TimeoutThread  implements Runnable {
		
	
	@Override
	public void run() {						

			float startTimer = System.nanoTime();
			float endTimer = 0;	
			
			while(true){
				
				endTimer = System.nanoTime();
				//Log.d("IntervalTimer", String.valueOf((endTimer - startTimer) / 1000000));
				if (((endTimer - startTimer) / 1000000) >= 3000) {
					
					ChatHeadsService.hidden_flag = true;
					break;
				}				
				
				
			}	
		}
				
	}	
	