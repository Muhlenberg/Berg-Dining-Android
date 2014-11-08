package com.muhlenberg.dining;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Disable swiping on viewpager
 * @author jmankhan
 *
 */
public class DiningViewPager extends ViewPager
{

	public DiningViewPager(Context context)
	{
		super(context);
	}
	
	public DiningViewPager(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event)
	{

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return false;
    }

}
