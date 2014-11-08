package com.example.muhlenbergdiningx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * forces square images
 * @author jmankhan
 *
 */
public class DiningImageView extends ImageView
{
	private String text;
	
	public DiningImageView(Context context) {
		super(context);
	}
	
	public DiningImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	public DiningImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
	
	public DiningImageView(Context context, int res)
	{
		super(context);
		setImageResource(res);
	}
	
	public DiningImageView(Context context, int res, String text)
	{
		super(context);
		setImageResource(res);
		this.text=text;
	}
	
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); 
    }
	
	public String getText()
	{
		return text;
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		if(getTag() == null)
			return;

		text = (String) getTag();
		
		Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
		paint.setColor(Color.BLACK);
		paint.setTextSize(24.0f);
		
		Rect bounds = new Rect();
		paint.getTextBounds(text.toUpperCase(), 0, text.length(), bounds);
		canvas.drawText(text.toUpperCase(), getWidth()/2-bounds.width()/2, getHeight(), paint);
	}
}
