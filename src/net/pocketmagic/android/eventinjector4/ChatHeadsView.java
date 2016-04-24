package net.pocketmagic.android.eventinjector4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class ChatHeadsView extends View{	
    	
	private String text = null;
    private int backgroundColor = Color.BLACK;
    private String tempText;

	public ChatHeadsView(Context context) {
		super(context);				
		
	}	
	    @Override
	    protected void onFinishInflate() {
	        setClickable(true);
	        Log.w("LOG","onFinishInflate()");
	    }
	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {	        
	      
	        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	        int heightSize = 0;
	        switch(heightMode) {
	        case MeasureSpec.UNSPECIFIED:   
	            heightSize = heightMeasureSpec;
	            break;
	        case MeasureSpec.AT_MOST:      
	            heightSize = 60;
	            break;
	        case MeasureSpec.EXACTLY:      
	            heightSize = MeasureSpec.getSize(heightMeasureSpec);
	            break;
	        }
	        
	        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	        int widthSize = 0;
	        switch(widthMode) {
	        case MeasureSpec.UNSPECIFIED:   
	            widthSize = widthMeasureSpec;
	            break;
	        case MeasureSpec.AT_MOST:      
	            widthSize = 60;
	            break;
	        case MeasureSpec.EXACTLY:      
	            widthSize = MeasureSpec.getSize(widthMeasureSpec);
	            break;
	        }
	        	       	        
	        setMeasuredDimension(getContext().getApplicationContext().getResources().getDisplayMetrics().heightPixels/15
	        								,getContext().getApplicationContext().getResources().getDisplayMetrics().heightPixels/15);
	    
	        
	    
	    
	    }
	   
	    @Override
	    protected void onDraw(Canvas canvas) {    
	        
	    	
	    	
	    	
	    	
	    }
	  
	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	     
	        return super.onKeyDown(keyCode, event); 
	    }	    
	  
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	       Log.w("LOG","onTouchEvent("+event+")");
	        switch(event.getAction()) {
	        case MotionEvent.ACTION_UP:
	            backgroundColor = Color.BLACK;
	            text = tempText;
	            break;
	        case MotionEvent.ACTION_DOWN:
	            backgroundColor = Color.BLACK;
	            tempText = text;
	            text = "Clicked!";
	            break;
	        case MotionEvent.ACTION_MOVE:
	            backgroundColor = Color.BLUE;
	            text = "Moved!";
	            break;
	        }
	        invalidate();
	        return super.onTouchEvent(event);
	    }	    
	    
	    public String getText() {
	        return text;
	    }
	    public void setText(String text) {
	        this.text = text;
	    }
	}

